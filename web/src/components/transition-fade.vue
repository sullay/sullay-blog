<template>
  <transition name="fade" mode="out-in">
    <slot></slot>
  </transition>
</template>

<script>
export default {
  name: 'transition-fade'
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: all .5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
  transform: translateX(100px);
}
</style>
