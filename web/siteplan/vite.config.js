import vue from '@vitejs/plugin-vue'
import path from 'path'
import {defineConfig} from 'vite'

export default defineConfig(({_, mode}) => ({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
      "rbush": __dirname + '/node_modules/rbush/rbush.js',
    },

    // Allow import file from the extension list
    extensions: ['.vue', '.ts', '.js', '.json']
  },
  build: {
    sourcemap: mode === 'development' ? true : 'inline',
    minify: mode === 'development' ? false : 'esbuild',
    outDir: mode === 'development' ? `${process.env.ECLIPSE_ROOT}/web/siteplan` : "dist",
    emptyOutDir: true
  }
}))
