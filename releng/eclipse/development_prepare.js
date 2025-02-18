const exec = require('child_process').exec
const original_path = __dirname
const https = require('https')
const fs = require('fs')
const pdfjsVersion = require("../../web/pdf/package.json").dependencies["pdfjs-dist"].substring(1)
const eclipseHome = process.env.ECLIPSE_HOME
// Build and copy web package to EclipseDirectory
copyResourceDirectory()
compileWebPackages()

async function copyResourceDirectory() {
  console.log("****Compile Textviewer****")
  const copyFunc = (src, dest) => {
    console.log(`Copy ${src} to ${dest}`)
    fs.cpSync(src, dest, {recursive: true, force: true})
  }
  const featureBundlePath = "../../java/bundles/org.eclipse.set.feature/rootdir"
  copyFunc(featureBundlePath, eclipseHome)
  console.log("****End****")
}

async function compileWebPackages() {
  // News
  console.log("****Compile News****")
  await doCommand(`cd ../../web/news && hugo -d ${eclipseHome}/web/news`)

  // Developerhelp
  console.log("****Compile Developerhelp****")
  await doCommand(`cd ../../web/developerhelp && hugo -d ${eclipseHome}/web/developerhelp`)

  // About
  console.log("****Compile About****")
  await doCommand(`cd ../../web/about && hugo -d ${eclipseHome}/web/about`)
  // Textviewer
  console.log("****Compile Textviewer****")
  await doCommand(`cd ../../web/textviewer && npm run build`)

  // Siteplan
  console.log("****Compile Siteplan****")
  await doCommand(`cd ../../web/siteplan && npm run build`)

  // Pdfjs
  console.log("****Compile Pdfjs****")
  compilePdfJs(
    `https://github.com/mozilla/pdf.js/releases/download/v${pdfjsVersion}/pdfjs-${pdfjsVersion}-dist.zip`, "../../web/pdf/pdfjs.zip")
}

async function compilePdfJs(fileUrl, outputPath) {
  await new Promise(resolve => {
    https.get(fileUrl, response => {
      if (response.statusCode >= 300 && response.statusCode < 400 && response.headers.location) {
        console.log("Redirecting URL nach: " + response.headers.location)
        return compilePdfJs(response.headers.location, outputPath)
      }

      if (response.statusCode !== 200) {
        console.error("Error: HTTP " + response.statusCode)
      }
      const fileStream = fs.createWriteStream(outputPath)
      response.pipe(fileStream)
      fileStream.on("finish", () => {
        console.log("Download complete")
        fileStream.close()
      }).on("error", err => {
        console.log("Download Error: " + err.message)
      })
      resolve(null)
    })
  })
  await doCommand(`cd ../../web/pdf && tar -xf pdfjs.zip && npm run build`)
}

async function doCommand(command) {
  await new Promise(resolve => {
    exec(`${command}`, (error, stderr, stdout) => {
      if (error) {
        console.error(`Error executing command: ${error.message}`);
      } else if (stderr) {
        console.error(`Error output: ${stderr}`);
      } else {
        console.log(`Command Output: ${stdout}`);
      }

      console.log("****End****")
      resolve(null)
    })
  })
}