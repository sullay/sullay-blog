<template>
  <div class="index">
    <div class="banner"></div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {

    }
  },
  created () {
  },
  methods: {
    ...mapActions([])
  }
}
</script>
<style lang="less" scoped>
.index{
  .banner{
    width: 100%;
    height: 494px;
    background-image: url('http://sullay.cn/files/blog/banner.jpg');
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
  }
}
</style>
