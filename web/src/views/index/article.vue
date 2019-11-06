<template>
  <div>
    <mavon-editor
      class="md"
      :ishljs="true"
      :value="this.$store.state.text.textInfo"
      :subfield="prop.subfield"
      :defaultOpen="prop.defaultOpen"
      :toolbarsFlag="prop.toolbarsFlag"
      :editable="prop.editable"
      :scrollStyle="prop.scrollStyle"
    ></mavon-editor>
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
        scrollStyle: true
      }
    }
  },
  created () {
  },
  methods: {
    ...mapActions(['file_uploadFile'])
  }
}
</script>
<style scoped>
</style>
