const exec = require('child_process').exec
const https = require('https')
const fs = require('fs')
const projectRoot = `${__dirname}/../..`

let eclipseHome = process.env.ECLIPSE_HOME
const envFilePath = `${__dirname}/.env`
if (eclipseHome) {
  fs.writeFileSync(envFilePath, `ECLIPSE_HOME=${eclipseHome}`)
} else if (fs.existsSync(envFilePath)) {
  const envFileContent = fs.readFileSync(envFilePath, 'utf-8')
  const matchResult = envFileContent.match(/^ECLIPSE_HOME=(.*)$/)
  if (matchResult) {
    eclipseHome = matchResult[1]
  }
}
if (!eclipseHome) {
  console.error('Neither eclipse home set nor .env file existing. Can not proceed')
  return
}
console.log('Preparing development environment with eclipse home', eclipseHome)

// Build and copy web package to EclipseDirectory
copyResourceDirectory()
compileWebPackages()

async function copyResourceDirectory() {
  console.log("****Copy resource directory****")
  const copyFunc = (src, dest) => {
    console.log(`Copy ${src} to ${dest}`)
    fs.cpSync(src, dest, { recursive: true, force: true })
  }
  const featureBundlePath = `${projectRoot}/java/bundles/org.eclipse.set.feature/rootdir`
  copyFunc(featureBundlePath, eclipseHome)
  console.log("****End****")
}

async function compileWebPackages() {
  // News
  console.log("****Compile News****")
  await doCommand(`cd web/news && hugo -d ${eclipseHome}/web/news`)

  // Developerhelp
  console.log("****Compile Developerhelp****")
  await doCommand(`cd web/developerhelp && hugo -d ${eclipseHome}/web/developerhelp`)

  // About
  console.log("****Compile About****")
  await doCommand(`cd web/about && hugo -d ${eclipseHome}/web/about`)
  // Textviewer
  console.log("****Compile Textviewer****")
  createEnvSymlink('web/textviewer')
  await doCommand(`cd web/textviewer && npm ci && npm run build`)

  // Siteplan
  console.log("****Compile Siteplan****")
  createEnvSymlink('web/siteplan')
  await doCommand(`cd web/siteplan && npm ci && npm run build`)

  // Pdfjs
  console.log("****Compile Pdfjs****")
  const pdfjsVersion = require(`${projectRoot}/web/pdf/package.json`).dependencies["pdfjs-dist"].substring(1)
  compilePdfJs(
    `https://github.com/mozilla/pdf.js/releases/download/v${pdfjsVersion}/pdfjs-${pdfjsVersion}-dist.zip`, `${projectRoot}/web/pdf/pdfjs.zip`)
}

async function compilePdfJs(fileUrl, outputPath) {
  const executeCommand = await new Promise((resolve, reject) => {
    https.get(fileUrl, async response => {
      if (response.statusCode >= 300 && response.statusCode < 400 && response.headers.location) {
        console.log("Redirecting URL nach: " + response.headers.location)
        await compilePdfJs(response.headers.location, outputPath)
        resolve(false)
        return
      }

      if (response.statusCode !== 200) {
        console.error("Error: HTTP " + response.statusCode)
        reject()
      }
      const fileStream = fs.createWriteStream(outputPath)
      response.pipe(fileStream)
      fileStream.on("finish", () => {
        console.log("Download complete")
        fileStream.close()
        resolve(true)
      }).on("error", err => {
        console.log("Download Error: " + err.message)
        reject()
      })

    })
  })
  if (executeCommand) {
    createEnvSymlink('web/pdf')
    await doCommand(`cd web/pdf && npm ci && npm run build`)
  }
}

function createEnvSymlink(path) {
  fs.copyFileSync(`${projectRoot}/releng/eclipse/env-symlink`, `${projectRoot}/${path}/.env`)
}

async function doCommand(command) {
  await new Promise(resolve => {
    exec(`cd ${projectRoot} && ${command}`, (error, stderr, stdout) => {
      if (error) {
        console.error(`Error executing command: ${error.message}`)
      } else if (stderr) {
        console.error(`Error output: ${stderr}`)
      } else {
        console.log(`Command Output: ${stdout}`)
      }

      console.log("****End****")
      resolve(null)
    })
  })
}