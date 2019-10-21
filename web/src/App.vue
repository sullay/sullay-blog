<template>
  <div id="app" class="app">
    <div>
      <transition-fade>
        <router-view />
      </transition-fade>
    </div>
  </div>
</template>

<script>
export default {
  name: 'app',
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

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
