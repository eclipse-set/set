const path = require('path');
const packageJSON = require('./package.json')
const fs = require('fs').promises
const existsSync = require('fs').existsSync
const CopyWebpackPlugin = require('copy-webpack-plugin')
const TerserPlugin = require('terser-webpack-plugin');
const StreamZip = require('node-stream-zip');

class ExtractPDFJSPlugin {
  async extractPdfJS() {
    if (!existsSync('pdfjs.zip'))
      return

    await fs.mkdir('pdfjs', { recursive: true })
    const zip = new StreamZip.async({ file: './pdfjs.zip' });
    await zip.extract(null, './pdfjs')
    await zip.close()
  }

  apply(compiler) {
    compiler.hooks.beforeCompile.tapPromise('ExtractPDFJSPlugin', () => {
      return this.extractPdfJS()
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
    index: './index.js',
    'pdf.worker': 'pdfjs-dist/build/pdf.worker.entry',
  },
  output: {
    path: path.join(__dirname, 'dist'),
    filename: '[name].js',
  },
  plugins: [
    new ExtractPDFJSPlugin(),
    new CopyWebpackPlugin({
      patterns: [
        'pdfjs/web/viewer.css',
        'pdfjs/build/pdf.js',
        { from: 'pdfjs/web/viewer.html', transform: content => content.toString().replace('../build/pdf.js', 'pdf.js').replace('</head>', '<script src="index.js"></script></head>') },
        { from: 'pdfjs/web/viewer.js', transform: content => content.toString().replace('../build/pdf.worker.js', 'pdf.worker.js') },
        { from: 'pdfjs/web/images', to: 'images' },
        { from: 'pdfjs/web/locale', to: 'locale' }
      ]
    })
  ],
};
