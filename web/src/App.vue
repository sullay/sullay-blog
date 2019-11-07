<template>
  <div id="app" class="app">
    <transition-router>
      <router-view />
    </transition-router>
  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {

    }
  },
  created () {
    this.axios.interceptors.response.use(
      response => {
        if (response.data.code !== 200) {
          this.$message.error(response.data.data.errTip)
        }
        return response
      },
      error => {
        // Do something with response error
        return Promise.reject(error)
      }
    )
  }
}
</script>

<style lang="less">
#app {
  font-family: -apple-system,BlinkMacSystemFont,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Segoe UI","PingFang SC","Hiragino Sans GB","Microsoft YaHei","Helvetica Neue",Helvetica,Arial,sans-serif;
  text-align: center;
  color: rgba(255, 255, 255, 0.9);
  position: relative;
}
</style>
