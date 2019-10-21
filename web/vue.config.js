module.exports = {
  devServer: {
    // proxy: {
    //   // proxy all requests starting with /api to jsonplaceholder
    //   '/': {
    //     target: 'http://192.168.0.101:8080/haagendazs', // 代理接口
    //     changeOrigin: true,
    //     pathRewrite: {
    //       '^/': '/' // 代理的路径
    //     }
    //   }
    // }
  },
  publicPath: process.env.NODE_ENV === 'production'
    ? './'
    : '/',
  configureWebpack: config => {
    if (process.env.NODE_ENV === 'production') {
      config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
      config.optimization.minimizer[0].options.terserOptions.compress.drop_debugger = true
      config.optimization.minimizer[0].options.terserOptions.compress.pure_funcs = ['console.log']
    }
  }
}
