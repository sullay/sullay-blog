<template>
  <div class="index">
    <div class="banner"></div>
    <transition-list-index tag="div" class="articleList">
      <div v-for="article in articleList" :key="article.id" class="article"
      @click="toArticle(article.id)"
      :style="'background:linear-gradient(135deg,hsl('+article.id*60+',50%,50%),hsl('+article.id*50+',50%,50%));'">
        <h1>{{article.name}}</h1>
        <p>{{article.createTime|filterTime}}</p>
      </div>
    </transition-list-index>
    <button v-if="showMoreBtn" @click="more" class="more">加载更多</button>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      articleList: [],
      pageSize: 5,
      showMoreBtn: false
    }
  },
  computed: {
    lastId () {
      return this.articleList[this.articleList.length - 1].id || 0
    }
  },
  created () {
    this._getArticleListAll(0)
  },
  methods: {
    ...mapActions(['getArticleListAll']),
    _getArticleListAll (id) {
      this.getArticleListAll({ pageSize: this.pageSize, id: id }).then(res => {
        if (res.data.code === 200) {
          if (res.data.data.length < this.pageSize) {
            this.showMoreBtn = false
          } else {
            this.showMoreBtn = true
          }
          this.articleList = this.articleList.concat(res.data.data)
        }
      })
    },
    more () {
      this._getArticleListAll(this.lastId - 1)
    },
    toArticle (id) {
      this.$router.push({
        name: 'article',
        params: {
          id: id
        }
      })
      // window.location.href = `/article/${id}`
    }
  }
}
</script>
<style lang="less" scoped>
.index{
  padding-bottom: 120px;
  .banner{
    width: 100%;
    height: 494px;
    background-image: url('http://sullay.cn/files/blog/banner.jpg');
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
  }
  .articleList{
    margin-top: 80px;
    display: grid;
    justify-content: center;
    grid-template-columns: 928px;
    grid-auto-rows: 248px;
    row-gap: 50px;
    .article{
      border-radius: 5px;
      cursor: pointer;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      &:hover{
        transition: all 0.5s;
        transform: scale(1.05);
      }
    }
  }
  .more{
    margin: 80px auto 0;
    width: 134px;
    height: 44px;
    border-radius: 55px;
    background: inherit;
    border: 1px solid #fff;
    outline:none;
    color: #fff;
    cursor: pointer;
    &:hover{
      color: #333;
      background: #fff;
    }
  }
}
</style>
