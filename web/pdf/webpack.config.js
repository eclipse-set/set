const path = require("path");
const packageJSON = require("./package.json")
const fs = require('fs').promises
const existsSync = require('fs').existsSync
const CopyWebpackPlugin = require('copy-webpack-plugin')
const TerserPlugin = require("terser-webpack-plugin");
const StreamZip = require('node-stream-zip');
const { Octokit } = require("@octokit/rest");

// The pdfjs-prod package does not contain the viewer component, but the main release on Github does.
// As a result we need to fetch the release. For easier versioning, we use the version defined in package.json
class DownloadPDFJSPlugin {
  async downloadPdfJS(version) {
    if (existsSync(`pdfjs/pdfjs-${version}-dist.zip`))
      return

    try {
      const octokit = new Octokit({ auth: process.env.GITHUB_TOKEN });
      const release = await octokit.rest.repos.getReleaseByTag({
        owner: "mozilla",
        repo: "pdf.js",
        tag: `v${version}`
      })

      const data = await octokit.rest.repos.getReleaseAsset({
        owner: "mozilla",
        repo: "pdf.js",
        asset_id: release.data.assets[0].id,
        headers: {
          Accept: "application/octet-stream"
        },
      })

      await fs.mkdir('pdfjs', { recursive: true })
      await fs.writeFile(`./pdfjs/pdfjs-${version}-dist.zip`, Buffer.from(data.data))
      const zip = new StreamZip.async({ file: `./pdfjs/pdfjs-${version}-dist.zip` });
      await zip.extract(null, './pdfjs')
      await zip.close()
    }
    catch (e) {
      console.log(e)
    }
  }

  apply(compiler) {
    compiler.hooks.beforeCompile.tapPromise('DownloadPDFJSPlugin', () => {
      return this.downloadPdfJS(packageJSON.dependencies["pdfjs-dist"].substring(1))
    })
  }
}

module.exports = {
  mode: 'production',
  context: __dirname,
  // Disable performance hints (this is a locally served web application)
  performance: {
    hints: false,
    maxEntrypointSize: 512000,
    maxAssetSize: 512000
  },
  optimization: {
    minimize: true,
    minimizer: [new TerserPlugin({ extractComments: false })],
  },
  entry: {
    index: "./index.js",
    "pdf.worker": "pdfjs-dist/build/pdf.worker.entry",
  },
  output: {
    path: path.join(__dirname, "dist"),
    filename: "[name].js",
  },
  plugins: [
    new DownloadPDFJSPlugin(),
    new CopyWebpackPlugin({
      patterns: [
        'pdfjs/web/viewer.css',
        'pdfjs/build/pdf.js',
        { from: 'pdfjs/web/viewer.html', transform: content => content.toString().replace("../build/pdf.js", "pdf.js").replace("</head>", '<script src="index.js"></script>') },
        { from: 'pdfjs/web/viewer.js', transform: content => content.toString().replace("../build/pdf.worker.js", "pdf.worker.js") },
        { from: 'pdfjs/web/images', to: "images" },
        { from: 'pdfjs/web/locale', to: "locale" }
      ]
    })
  ],
};
