const path = require('path')

// eslint-disable-next-line no-unused-vars
function resolve (dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  chainWebpack: (config) => {
  },
  devServer: {
    host: process.env.VUE_APP_DEV_SERVER_HOST,
    port: process.env.VUE_APP_DEV_SERVER_PORT,
    proxy: {
      '/': {
        target: process.env.VUE_APP_API_SERVER_URL
      }
    }
  }
}
