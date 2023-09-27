module.exports = {
  // Disable linting on save (rather: build),
  // as we're linting in a separate step in production builds
  lintOnSave: import.meta.env.NODE_ENV !== 'production',
  chainWebpack: config => {
    // Disable webpack size warnings,
    // as we're not tranferring files over an internet connection
    config.performance.maxEntrypointSize(100000000).maxAssetSize(100000000)
    // Workaround for https://github.com/vuejs/vue-cli/issues/5399
    config.module.rule('vue').uses.delete('cache-loader')
    config.module.rule('js').uses.delete('cache-loader')
    config.module.rule('ts').uses.delete('cache-loader')
    config.module.rule('tsx').uses.delete('cache-loader')
  }
}
