const path = require('path')
const Copy = require('copy-webpack-plugin')

module.exports = {
  mode: 'development',
  entry: './src/index.ts',
  resolve: {
    extensions: ['.ts', '.js']
  },
  output: {
    filename: 'index.js',
    path: path.resolve(__dirname, 'dist')
  },
  externals: { 'monaco-editor': 'monaco' },
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
        { from: './node_modules/monaco-editor/min/vs/basic-languages/xml', to: 'monaco/vs/basic-languages/xml' },
        { from: './node_modules/monaco-editor/min/vs/editor', to: 'monaco/vs/editor' },
        { from: './node_modules/monaco-editor/min/vs/base', to: 'monaco/vs/base' },
        {
          from: './node_modules/monaco-editor/min/vs/*.js',
          to ({ context, absoluteFilename }) {
            return 'monaco/vs/[name][ext]'
          }
        }
      ]
    })
  ]
}
