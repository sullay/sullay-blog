<template>
  <div class="article">
    <div class="title">
      <h1>{{article.name}}</h1>
      <p>{{article.createTime|filterTime}}</p>
    </div>
    <mavon-editor
      :ishljs="true"
      codeStyle="vs2015"
      :value="article.context"
      :subfield="prop.subfield"
      :defaultOpen="prop.defaultOpen"
      :toolbarsFlag="prop.toolbarsFlag"
      :editable="prop.editable"
      :scrollStyle="prop.scrollStyle"
      :boxShadow="prop.boxShadow"
      previewBackground="#2c2a2a"
    ></mavon-editor>
    <div id="vcomments" ref="vcomments"></div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      prop: {
        subfield: false, // 单双栏模式
        defaultOpen: 'preview', // edit： 默认展示编辑区域 ， preview： 默认展示预览区域
        editable: false,
        toolbarsFlag: false,
        scrollStyle: true,
        boxShadow: false
      },
      article: {}
    }
  },
  created () {
    if (this.$route.params.id) {
      this._getArticleById(this.$route.params.id)
    }
  },
  mounted () {
    // eslint-disable-next-line no-undef
    let valine = new Valine()
    valine.init({
      el: '#vcomments',
      appId: 'wp6uBUe91YrXbA7UKyDBnLGe-gzGzoHsz',
      appKey: 'NR1GUnjpWTjeI4z4D6JOqoUv',
      path: '/' + this.$route.params.id,
      placeholder: '请留言...',
      meta: ['nick', 'mail']
    })
    let nick = this.$refs.vcomments.querySelector('[name=nick]')
    let mail = this.$refs.vcomments.querySelector('[name=mail]')
    nick.setAttribute('disabled', true)
    mail.setAttribute('disabled', true)
    nick.value = '杨金伟'
    mail.value = '1181518458@qq.com'
    localStorage.setItem('ValineCache', JSON.stringify({ 'nick': nick.value, 'mail': mail.value, 'link': '' }))
  },
  methods: {
    ...mapActions(['getArticleById']),
    _getArticleById (id) {
      this.getArticleById({ id: id }).then(res => {
        if (res.data.code === 200) {
          console.log(res)
          this.article = res.data.data
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.article{
  padding-bottom: 50px;
  .title{
    width: 100%;
    height: 400px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    background-color:rgba(52, 50, 50, 0.97);
    h1{
      margin-bottom: 13px;
    }
  }
}
</style>
<style lang="less">
.article{
  .markdown-body{
    color: rgb(188, 188, 188);
    width: 800px;
    margin: 30px auto 0;
    pre{
     background-color: inherit;
     padding:0px;
    }
    img{
      margin: 10px auto 0;
      display: block;
    }
    &.v-note-wrapper{
      border: none;
    }
  }
  #vcomments{
    width: 1200px;
    margin: 0 auto;
  }
  .v {
    .vsys{
      color: #b3b1b1;
    }
    p{
      color: rgba(255, 255, 255, 0.9);
    }
  }
}
</style>
