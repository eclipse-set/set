const execFile = require('child_process').execFile
const https = require('https')
const fs = require('fs')
const path = require('path')
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
console.log('Preparing development environment with eclipse home', eclipseHome, 'and project root', projectRoot)

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
  await doCommand({ cmd: 'hugo', args: ['-d', `${eclipseHome}/web/news`], cwd: `${projectRoot}/web/news`})
  console.log("****End****")

  // Developerhelp
  console.log("****Compile Developerhelp****")
  await doCommand({ cmd: 'hugo', args: ['-d', `${eclipseHome}/web/developerhelp`], cwd: `${projectRoot}/web/developerhelp`})
  console.log("****End****")

  // About
  console.log("****Compile About****")
  await doCommand({ cmd: 'hugo', args: ['-d', `${eclipseHome}/web/about`], cwd: `${projectRoot}/web/about`})
  console.log("****End****")

  // Textviewer
  console.log("****Compile Textviewer****")
  createEnvSymlink('web/textviewer')
  await doCommand({ cmd: 'npm', args: ['ci'], cwd: `${projectRoot}/web/textviewer` })
  await doCommand({ cmd: 'npm', args: ['run', 'build'], cwd: `${projectRoot}/web/textviewer` })
  console.log("****End****")

  // Siteplan
  console.log("****Compile Siteplan****")
  createEnvSymlink('web/siteplan')
  await doCommand({ cmd: 'npm', args: ['ci'], cwd: `${projectRoot}/web/siteplan` })
  await doCommand({ cmd: 'npm', args: ['run', 'build'], cwd: `${projectRoot}/web/siteplan` })
  console.log("****End****")

  // Pdfjs
  console.log("****Compile Pdfjs****")
  const pdfjsVersion = require(`${projectRoot}/web/pdf/package.json`).dependencies["pdfjs-dist"].substring(1)
  compilePdfJs(
    `https://github.com/mozilla/pdf.js/releases/download/v${pdfjsVersion}/pdfjs-${pdfjsVersion}-dist.zip`, `${projectRoot}/web/pdf/pdfjs.zip`)
  console.log("****End****")
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
    await doCommand({ cmd: 'npm', args: ['ci'], cwd: `${projectRoot}/web/pdf` })
    await doCommand({ cmd: 'npm', args: ['run', 'build'], cwd: `${projectRoot}/web/pdf` })
  }
}

function createEnvSymlink(path) {
  fs.copyFileSync(`${projectRoot}/releng/eclipse/.env`, `${projectRoot}/${path}/.env`)
}

// On Windows npm is a .cmd script, which can't be executed without a shell.
// Therefore run npm's JavaScript entry point directly with the current node binary.
function findNpmCli() {
  const nodeDir = path.dirname(process.execPath)
  const candidates = [
    process.env.npm_execpath,
    path.join(nodeDir, 'node_modules', 'npm', 'bin', 'npm-cli.js'),
    path.join(nodeDir, '..', 'lib', 'node_modules', 'npm', 'bin', 'npm-cli.js')
  ]
  return candidates.find(candidate => candidate && candidate.endsWith('npm-cli.js') && fs.existsSync(candidate))
}

async function doCommand({ cmd, args = [], cwd = projectRoot }) {
  await new Promise(resolve => {
    console.log(`Executing: ${cmd} ${args.join(' ')}`)
    if (cmd === 'npm') {
      const npmCli = findNpmCli()
      if (npmCli) {
        cmd = process.execPath
        args = [npmCli, ...args]
      }
    }
    const child = execFile(cmd, args, { cwd }, (error, stdout, stderr) => {
      if (error) {
        console.error(`Error executing command: ${error.message}`)
      }
      resolve(null)
    })
    child.stdout.pipe(process.stdout)
    child.stderr.pipe(process.stderr)
  })
}