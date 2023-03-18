module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8001',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },
  transpileDependencies: [
    'vuetify'
  ]
}
