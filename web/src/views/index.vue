<template>
  <div>
    <nav class="nav">
      <div>
        <router-link v-for="nav in navList" :key="nav.name" :to="nav.url">{{nav.name}}</router-link>
      </div>
      <div>
        <div class="input">
          <button @click="switchSearch"><i class="fa fa-search"/></button>
          <transition-grow width="150px">
            <input v-if="showSearch" v-model="searchKey" placeholder="搜索..."/>
          </transition-grow>
        </div>
        <template  v-if="isLogin">
          <span>{{userInfo.userName}}</span>
          <img :src="head" alt="头像">
          <img src="../assets/img/i_logout.svg" alt="登出" @click="logout">
        </template>
        <template v-else>
          <router-link type="text" to="/sign/up">注册</router-link>
          <router-link type="text" to="/sign/in">登录</router-link>
        </template>
      </div>
    </nav>
    <div class="home">
      <transition-router>
        <router-view />
      </transition-router>
    </div>
    <Footer/>
  </div>
</template>

<script>
import Footer from '../components/footer'
import md5 from 'md5'
export default {
  name: 'app',
  components: {
    Footer
  },
  data () {
    return {
      searchKey: '',
      showSearch: false,
      userInfo: this.$decDes(localStorage.getItem('userInfo'))
    }
  },
  computed: {
    isLogin () {
      return !!this.userInfo.sessionId
    },
    head () {
      return `https://www.gravatar.com/avatar/${md5(this.userInfo.email)}`
    },
    navList () {
      let list = [{ name: '首页', url: '/' }]
      if (this.isLogin) {
        list.push({
          name: '新增文章',
          url: '/writer'
        })
      }
      return list
    }
  },
  created () {

  },
  methods: {
    logout () {
      localStorage.setItem('userInfo', '')
      this.userInfo = {}
    },
    switchSearch () {
      console.log(this.showSearch)
      this.showSearch = !this.showSearch
    }
  }
}
</script>
<style lang="less" scoped>
nav {
  width: 100%;
  height: 72px;
  background-color: rgba(52, 50, 50, 0.97);
  position: absolute;
  top: 0;
  left: 0;
  justify-content: center;
  align-content: center;
  display: grid;
  grid-template-columns: 750px 450px;
  grid-template-rows: 1fr;
  box-shadow: 0 0.3125rem 0.3125rem -0.3125rem rgba(0,0,0,0.117);
  &>div {
    display: flex;
    flex-direction: row;
    align-items: center;
    &:first-of-type {
      justify-content: flex-start;
      &>* {
        margin-right: 35px;
      }
    }
    &:last-of-type {
      justify-content: flex-end;
      & > *{
        margin-right: 20px;
      }
      img{
        width: auto;
        height: 48px;
        cursor: pointer;
      }
      .input{
        background: #2c2a2a;
        border-radius: 40px;
        transition: all .5s;
        button{
          height: 40px;
          width: 40px;
          border-radius: 100%;
          background: #2c2a2a;
          color: #ccc;
          line-height: 40px;
          text-align: center;
          outline: none;
          border: none;
          font-size: 15px;
          cursor: pointer;
          &:hover{
            background: #fff;
            color: #333;
          }
        }
        input{
          width: 150px;
          line-height: 1.5;
          outline: none;
          border: none;
          background: inherit;
          color: #ccc;
          padding: 0 15px 0 5px;
          border-radius: 40px;
        }
      }
    }
  }
}
.home {
  background-color: #2c2a2a;
  width: 100%;
  min-height: 900px;
  padding-top: 72px;
}
</style>

<style>
</style>
