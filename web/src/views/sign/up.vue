<template>
  <div class="signUp">
    <el-form :model="form" :rules="rules" ref="form" label-width="80px">
       <el-form-item label="邮箱：" prop="email">
        <el-input v-model="form.email" placeholder="Email"></el-input>
      </el-form-item>
       <el-form-item label="用户名：" prop="userName">
        <el-input v-model="form.userName" placeholder="UserName"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input v-model="form.password" type="password" placeholder="Password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="warning" @click="onSubmit">注册账号</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      form: {},
      rules: {
        email: [
          { required: true,
            validator: (rule, value, callback) => {
              if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value)) {
                callback()
              } else {
                callback(new Error('请输入正确的邮箱'))
              }
            },
            trigger: 'blur'
          }
        ],
        userName: [
          { required: true,
            validator: (rule, value, callback) => {
              if (/^[a-zA-Z0-9_-]{4,16}$/.test(value)) {
                callback()
              } else {
                callback(new Error('请输入4-16位字母、数字、下划线组成的用户名'))
              }
            },
            trigger: 'blur'
          }
        ],
        password: [
          { required: true,
            validator: (rule, value, callback) => {
              if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test(value)) {
                callback()
              } else {
                callback(new Error('请输入6-20位数字或字母的密码'))
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    ...mapActions(['register']),
    onSubmit () {
      // 注册
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.register(this.form).then(res => {
            if (res.data.code === 200) {
              this.$router.push('/sign/in')
            }
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.signUp{
  /deep/ .el-form-item__label{
    color: rgba(255, 255, 255, 0.9);
  }
}
</style>
