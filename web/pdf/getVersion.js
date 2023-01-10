const packageJSON = require("./package.json")
console.log(packageJSON.dependencies["pdfjs-dist"].substring(1))