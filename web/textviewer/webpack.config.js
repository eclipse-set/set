/* eslint-disable @typescript-eslint/no-require-imports */
const path = require('path')
const Copy = require('copy-webpack-plugin')
const dotenv = require('dotenv')
dotenv.config()
module.exports = (env, argv) => ({
  mode: argv.mode,
  entry: './src/index.ts',
  resolve: {
    extensions: ['.ts', '.js']
  },
  output: {
    filename: 'index.js',
    path: argv.mode == 'development' ? `${process.env.ECLIPSE_HOME}/web/textviewer` : path.resolve(__dirname, 'dist')
  },
  externals: {'monaco-editor': 'monaco'},
  module: {
    rules: [
      {
        test: /\.ts?$/,
        use: 'ts-loader',
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader']
      },
      {
        test: /\.ttf$/,
        use: ['file-loader']
      }
    ]
  },
  plugins: [
    new Copy({
      patterns: [
        './src/index.html',
        {from: './node_modules/monaco-editor/min/vs/basic-languages', to: 'monaco/vs/basic-languages'},
        {from: './node_modules/monaco-editor/min/vs/editor', to: 'monaco/vs/editor'},
        {from: './node_modules/monaco-editor/min/vs/assets', to: 'monaco/vs/assets'},
        {from: './node_modules/monaco-editor/min/vs/loader.js', to: 'monaco/vs/loader.js'},
        {
          from: './node_modules/monaco-editor/min/vs/*.js',
          to({context, absoluteFilename}) {
            return 'monaco/vs/[name][ext]'
          }
        }
      ]
    })
  ]
})
