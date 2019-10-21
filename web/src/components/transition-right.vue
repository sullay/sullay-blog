<template>
  <transition name="right">
    <slot></slot>
  </transition>
</template>

<script>
export default {
  name: 'transition-right'
}
</script>

<style scoped>
.right-enter-active, .right-leave-active {
  transition: all .5s;
}
.right-enter, .right-leave-to {
  opacity: 0;
  transform: translateX(50%);
}
</style>
