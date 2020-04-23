<template>
  <div class="sign">
    <transition-router>
    <router-view class="router"></router-view>
    </transition-router>
  </div>
</template>
<style lang="less" scoped>
  .sign{
    width: 100%;
    min-height: 100vh;
    background-image: url('../assets/img/b_sign.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    overflow: auto;
    .router{
      width: 600px;
      margin: 20vh auto 0;
    }
  }
</style>
