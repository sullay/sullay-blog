<template>
  <transition name="left">
    <slot></slot>
  </transition>
</template>

<script>
export default {
  name: 'transition-left'
}
</script>

<style scoped>
.left-enter-active, .left-leave-active {
  transition: all .5s;
}
.left-enter, .left-leave-to {
  opacity: 0;
  transform: translateX(-50%);
}
</style>
