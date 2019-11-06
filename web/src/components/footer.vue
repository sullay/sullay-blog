<template>
  <div class="footer">
    Copyright 2019 - {{year}} Powered by sullay.cn | All Rights Reserved | 粤ICP备19130979号
  </div>
</template>
<script>
import moment from 'moment'
export default {
  data () {
    return {
      year: moment().format('YYYY')
    }
  }
}
</script>
<style lang="less" scoped>
  .footer{
    width: 100%;
    height: 41px;
    line-height: 41px;
    text-align: center;
    background-color: rgba(52, 50, 50, 0.97);
  }
</style>
