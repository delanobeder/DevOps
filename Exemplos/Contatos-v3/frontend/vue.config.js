const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        pathRewrite: { '^/api': '' },
        secure: false,
        changeOrigin: true,
      },
    },
  }
})