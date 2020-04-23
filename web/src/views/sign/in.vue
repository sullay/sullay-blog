<template>
  <div class="signIn">
    <el-form :model="form" :rules="rules" ref="form" label-width="120px">
       <el-form-item label="用户名/邮箱：" prop="userName">
        <el-input v-model="form.userName" placeholder="UserName/Email"></el-input>
      </el-form-item>
      <el-form-item label="密码：" prop="password">
        <el-input v-model="form.password" type="password" placeholder="Password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="warning" @click="onSubmit">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      form: {},
      rules: {
        userName: [
          { required: true,
            validator: (rule, value, callback) => {
              if (/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/.test(value)) {
                callback()
              } else if (/^[a-zA-Z0-9_-]{4,16}$/.test(value)) {
                callback()
              } else {
                callback(new Error('请输入正确的用户名或邮箱'))
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
    onSubmit () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.signIn{
  transform: translateX(-40px);
  /deep/ .el-form-item__label{
    color: rgba(255, 255, 255, 0.9);
  }
}
</style>
