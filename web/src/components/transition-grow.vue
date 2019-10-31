<template>
  <transition name="grow" v-on:before-enter="beforeEnter" v-on:enter="enter" v-on:leave="leave">
    <slot></slot>
  </transition>
</template>

<script>
export default {
  name: 'transition-grow',
  props: {
    width: {
      type: String,
      default: '100px'
    }
  },
  methods: {
    beforeEnter: function (el) {
      el.style.width = '0px'
    },
    enter: function (el, done) {
      this.Velocity(el, { width: this.width }, { duration: 300, complete: done })
    },
    leave: function (el, done) {
      this.Velocity(el, { width: '0px', padding: '0px' }, { duration: 300, complete: done })
    }
  }
}

</script>

<style scoped>

</style>
