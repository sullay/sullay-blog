<template>
  <transition name="rotate" v-on:before-enter="beforeEnter" v-on:enter="enter" v-on:leave="leave">
    <slot></slot>
  </transition>
</template>

<script>
export default {
  name: 'transition-rotate',
  methods: {
    beforeEnter: function (el) {
    },
    enter: function (el, done) {
      let fa = el.querySelector('.fa-gear')
      this.Velocity(fa, { color: ['#f2f2f2', '#FF0000'] }, { duration: 1000 })
    },
    leave: function (el, done) {
      let fa = el.querySelector('.fa-gear')
      this.Velocity(fa, { color: ['#FF0000', '#f2f2f2'] }, { duration: 1000 })
    }
  }
}
</script>

<style scoped>
.rotate-enter-active,
.rotate-leave-active {
  transition: all 1s;
}
.rotate-enter,
.rotate-leave-to {
  opacity: 0;
  transform: rotate(-360deg) scale(0.5);
}
</style>
