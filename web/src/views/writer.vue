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
          popper-class="writer-tip-popover"
          placement="bottom-end"
          width="135"
          trigger="click">
          <div class="popover-tip">
            <div @click="editAnthologyName(anthology)"><i class="fa fa-pencil-square-o"></i>修改文集</div>
            <div @click="_deleteAnthology(anthology)"><i class="fa fa-trash-o"></i>删除文集</div>
          </div>
          <i class="fa fa-gear" slot="reference" v-show="index==selectedAnthology"/>
        </el-popover>
      </div>
    </div>
    <div class="articleList">
      <div class="create-article">
        <i class="fa fa-plus-circle"/>
        <span>新建文章</span>
      </div>
    </div>
    <div class="write-mavon">
      <el-input v-model="articleTitle" maxlength="50" class="context-title" placeholder="文章标题"></el-input>
      <mavon-editor
        class="mavon-editor"
        v-model="articleContext"
        :ishljs="true"
        codeStyle="vs2015"
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
      // 选择文集索引
      selectedAnthology: 0,
      // 文集列表
      anthologyList: [],
      // 文集名称（新增表单）
      anthologyName: '',
      // 是否显示创建文集表单
      isCreateAnthology: false,
      // 文章列表
      articleList: [],
      // 文章标题
      articleTitle: '',
      // 文章图片
      img_file: [],
      // 文章内容
      articleContext: ' ', // 输入的数据
      // 编辑器是否分栏
      subfield: false,
      // 编辑器按钮配置
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
  watch: {
    selectedAnthology (val) {

    }
  },
  created () {
    this._getAnthologyList()
  },
  mounted () {
  },
  methods: {
    ...mapActions(['uploadFile', 'createAnthology', 'getAnthologyList', 'deleteAnthology']),
    toCreateAnthology () {
      // 显示创建文集表单
      this.anthologyName = ''
      this.isCreateAnthology = true
    },
    _createAnthology () {
      // 创建文集
      if (!this.anthologyName) {
        this.$message.error('请输入文集名...')
        return
      }
      this.createAnthology({ name: this.anthologyName }).then(res => {
        if (res.data.code === 200) {
          this.isCreateAnthology = false
          this._getAnthologyList()
        }
      })
    },
    _getAnthologyList (isChange = true) {
      // 获取文集列表
      this.getAnthologyList({}).then(res => {
        if (res.data.code === 200) {
          if (isChange) {
            this.selectedAnthology = 0
          }
          this.anthologyList = res.data.data.anthologyPage.content
        }
      })
    },
    editAnthologyName (anthology) {
      // 修改文集名称
      this.$prompt('请输入文集名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^.{1,50}$/,
        inputErrorMessage: '请输入正确文集名称'
      }).then(({ value }) => {
        anthology.name = value
        this.createAnthology(anthology).then(res => {
          if (res.data.code === 200) {
            this._getAnthologyList(false)
          }
        })
      }).catch(() => {
      })
    },
    _deleteAnthology (anthology) {
      // 删除文集
      this.$confirm(`确认删除文集《${anthology.name}》？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteAnthology(anthology).then(res => {
          if (res.data.code === 200) {
            this._getAnthologyList()
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }
        })
      }).catch(() => {
      })
    },
    save () {
      // 保存文章
      console.log(this.articleContext)
      localStorage.setItem('text', this.articleContext)
    },
    subfieldToggle (status, value) {
      // 修改分栏状态
      this.subfield = status
    },
    $imgAdd (pos, $file) {
      // 上传图片
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
      // 删除图片
      delete this.img_file[pos]
    }
  }
}
</script>
<style scoped lang="less">
.writer {
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-rows: 100vh;
  &.writer-not-subfield {
    grid-template-columns: 16.66666667% 25% 58.33333333%;
  }
  &.writer-subfield {
    grid-template-columns: 0 0 100%;
  }
  .anthologyList{
    overflow-y: auto;
    background-color: #404040;
    color: #f2f2f2;
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 80px;
    grid-auto-rows: 40px;
    .create-anthology{
      align-self: center;
      height: 42px;
      margin: 0 18px;
      color: #ec7259;
      border: 1px solid rgba(236,114,89,.8);
      border-radius: 20px;
    }
    .anthologyName{
      margin: 0 18px;
      height: 35px;
      color: #ccc;
      background-color: #595959;
      border: 1px solid #333;
      padding: 4px 6px;
      font-size: 14px;
      line-height: 20px;
    }
    .anthologyName-operation{
      display: flex;
      flex-direction: row;
      justify-content: center;
      .el-button:first-of-type{
        margin-right: 50px;
      }
    }
    .anthology{
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 16px;
      cursor: pointer;
      & > span{
        max-width: 250px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      & > span:last-of-type{
        flex-shrink: 0;
      }
      .fa-gear{
        flex-shrink: 0;
        cursor: pointer;
      }
      &.isSelected{
        background-color: #666;
      }
      &:hover,&:active,&:focus{
        background-color: #666;
      }
    }
  }
  .articleList{
    overflow-y: auto;
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 60px;
    grid-auto-rows: 90px;
    color: #595959;
    .create-article{
      padding-left: 25px;
      text-align: left;
      line-height: 60px;
      border-bottom: 1px solid #d9d9d9;
      cursor: pointer;
      &:hover,&:active,&:focus{
        color: #333;
      }
      &>.fa{
        margin-right: 10px;
      }
    }
  }
  .write-mavon{
    display: flex;
    flex-direction: column;
    border-left: 1px solid #d9d9d9;
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
  }
}
.popover-tip{
  font-size: 14px;
  box-shadow: 0 5px 10px rgba(0,0,0,.2);
  list-style: none;
  background-color: #fff;
  color: #595959;
  border-radius: 6px;
  .fa{
    margin-right: 10px;
  }
  & > div{
    padding: 10px 20px;
    line-height: 20px;
    white-space: nowrap;
    text-align: left;
    position: relative;
    border-bottom: 1px solid #d9d9d9;
    cursor: pointer;
    &:focus,&:active,&:hover{
      background-color: #666;
      color: #fff;
    }
    &:first-of-type{
      border-radius: 4px 4px 0 0;
    }
    &:last-of-type{
      border-bottom:none;
      border-radius: 0 0 4px 4px;
    }
  }
}
</style>
<style lang="less">
  .writer{
    .context-title{
      .el-input__inner{
        border: none;
      }
    }
  }
  .writer-tip-popover{
    padding: 0;
    border:none;
    border-radius: 4px;
  }
</style>
