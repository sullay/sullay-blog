<template>
  <div class="writer" :class="{ 'writer-subfield':subfield,'writer-not-subfield':!subfield }">
    <div class="anthologyList">
      <el-button type="text" @click="toCreateAnthology" class="create-anthology"><i class="fa fa-plus"/>新建文集</el-button>
      <input
        v-model="anthologyName"
        maxlength="50"
        class="anthologyName"
        placeholder="请输入文集名..."
        @keyup.enter="_createAnthology"
        v-if="isCreateAnthology"
      />
      <div class="anthologyName-operation" v-if="isCreateAnthology">
        <el-button type="text" style="color:#42c02e" @click="_createAnthology">提交</el-button>
        <el-button type="text" style="color:#999" @click="isCreateAnthology=false">取消</el-button>
      </div>
      <div v-for="(anthology,index) in anthologyList"
        @click="selectedAnthology=index"
        :title="anthology.name"
        :key="anthology.id"
        class="anthology"
        :class="{'isSelected':index==selectedAnthology}">
        <span>{{anthology.name}}</span>
        <el-popover
          v-model="anthology.isShowTip"
          placement="bottom-end"
          width="135"
          trigger="click">
          33333333333333
          <i class="fa fa-gear" slot="reference" @click.stop="anthology.isShowTip=true" v-show="index==selectedAnthology"/>
        </el-popover>
      </div>
    </div>
    <div></div>
    <div class="write-mavon">
      <el-input v-model="title" maxlength="50" class="context-title"></el-input>
      <mavon-editor
        class="mavon-editor"
        v-model="context"
        :ishljs="true"
        :subfield="subfield"
        :fullscreen="true"
        :toolbars="toolbars"
        @subfieldToggle="subfieldToggle"
        @save="save"
        ref="md"
        @imgAdd="$imgAdd"
        @imgDel="$imgDel"
      />
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      selectedAnthology: 0,
      anthologyList: [],
      anthologyName: '',
      isCreateAnthology: false,
      title: '',
      img_file: [],
      context: ' ', // 输入的数据
      subfield: false,
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
        // fullscreen: true, // 全屏编辑
        // readmodel: true, // 沉浸式阅读
        /* 1.3.5 */
        undo: true, // 上一步
        trash: true, // 清空
        save: true // 保存（触发events中的save事件）
        /* 1.4.2 */
        // navigation: true // 导航目录
      }
    }
  },
  created () {
    this._getAnthologyList()
  },
  methods: {
    ...mapActions(['uploadFile', 'createAnthology', 'getAnthologyList']),
    toCreateAnthology () {
      this.anthologyName = ''
      this.isCreateAnthology = true
    },
    _createAnthology () {
      if (!this.anthologyName) {
        this.$message.error('请输入文集名...')
        return
      }
      this.createAnthology({ name: this.anthologyName }).then(res => {
        if (res.data.code === 200) {
          this.isCreateAnthology = false
        }
      })
    },
    _getAnthologyList () {
      this.getAnthologyList({}).then(res => {
        if (res.data.code === 200) {
          this.anthologyList = res.data.data.anthologyPage.content
          this._getAnthologyList()
        }
      })
    },
    save () {
      console.log(this.context)
    },
    subfieldToggle (status, value) {
      this.subfield = status
    },
    $imgAdd (pos, $file) {
      console.log(pos)
      console.log($file)
      // 第一步.将图片上传到服务器.
      var formdata = new FormData()
      formdata.append('file', $file)
      this.img_file[pos] = $file
      this.uploadFile(formdata).then(res => {
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
.writer {
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-rows: 100vh;
}
.writer-not-subfield {
  grid-template-columns: 16.66666667% 25% 58.33333333%;
}
.writer-subfield {
  grid-template-columns: 0 0 100%;
}
.anthologyList{
  overflow-y: auto;
  background-color: #404040;
  color: #f2f2f2;
  display: grid;
  grid-template-rows: 80px;
  grid-auto-rows: 40px;
}
.anthologyList .create-anthology{
  align-self: center;
  height: 42px;
  margin: 0 18px;
  color: #ec7259;
  border: 1px solid rgba(236,114,89,.8);
  border-radius: 20px;
}
.anthologyList .anthologyName{
  margin: 0 18px;
  height: 35px;
  color: #ccc;
  background-color: #595959;
  border: 1px solid #333;
  padding: 4px 6px;
  font-size: 14px;
  line-height: 20px;
}
.anthologyList .anthologyName-operation{
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.anthologyList .anthologyName-operation .el-button:first-of-type{
  margin-right: 50px;
}
.anthologyList .anthology{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 16px;
  cursor: pointer;
}
.anthologyList .anthology.isSelected{
  background-color: #666;
}
.anthologyList .anthology:hover,.anthologyList .anthology:active,.anthologyList .anthology:focus{
  background-color: #666;
}
.anthologyList .anthology span{
  max-width: 250px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.anthologyList .fa-gear{
  cursor: pointer;
}
.write-mavon{
  display: flex;
  flex-direction: column;
}
.context-title {
  margin-top: 20px;
  width: 100%;
  padding: 0 80px 10px 40px;
  margin-bottom: 0;
  border: none;
  font-size: 30px;
  font-weight: 400;
  line-height: 30px;
  -webkit-box-shadow: none;
  box-shadow: none;
  color: #595959;
  background-color: transparent;
  outline: none;
  border-radius: 0;
  overflow: hidden;
  -o-text-overflow: ellipsis;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex-shrink: 0;
}
.mavon-editor{
  flex-grow: 1;
}
</style>
