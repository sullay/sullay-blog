<template>
  <transition name="router" mode="out-in">
    <slot></slot>
  </transition>
</template>

<script>
export default {
  name: 'transition-router'
}
</script>

<style scoped>
.router-enter-active, .router-leave-active {
  transition: all .5s;
}
.router-enter{
  opacity: 0;
  transform: translateX(-100px);
}
.router-leave-to {
  opacity: 0;
  transform: translateX(100px);
}
</style>
