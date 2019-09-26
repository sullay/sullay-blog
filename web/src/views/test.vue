<template>
  <div>
    <mavon-editor v-model="context" :ishljs = "true" :toolbars="toolbars" @subfieldToggle="subfieldToggle" @save="save" ref=md @imgAdd="$imgAdd" @imgDel="$imgDel"/>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      img_file: [],
      context: ' ', // 输入的数据
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        mark: true, // 标记
        superscript: true, // 上角标
        quote: true, // 引用
        ul: true, // 无序列表
        ol: true, // 有序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        help: true, // 帮助
        code: true, // code
        subfield: true, // 是否需要分栏
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        /* 1.3.5 */
        undo: true, // 上一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true // 导航目录
      }
    }
  },
  created () {

  },
  methods: {
    ...mapActions(['updateTextInfo', 'uploadFile', 'test']),
    save () {
      console.log(this.context)
      this.updateTextInfo(this.context)
    },
    subfieldToggle (status, value) {
      console.log(status, value)
    },
    $imgAdd (pos, $file) {
      console.log(pos)
      console.log($file)
      // 第一步.将图片上传到服务器.
      var formdata = new FormData()
      formdata.append('file', $file)
      this.img_file[pos] = $file
      this.uploadFile(formdata).then((res) => {
        console.log(res)
        let _res = res.data.data
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        this.$refs.md.$img2Url(pos, this.fileUrl + _res.value)
      })
    },
    $imgDel (pos) {
      delete this.img_file[pos]
    }
  }
}
</script>
<style scoped>
</style>
