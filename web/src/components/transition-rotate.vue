<template>
  <transition name="rotate">
    <slot></slot>
  </transition>
</template>

<script>
export default {
  name: 'transition-rotate'
}
</script>

<style scoped>
.rotate-enter-active, .rotate-leave-active {
  transition: all 1s;
}
.rotate-enter, .rotate-leave-to {
  opacity: 0;
  transform: rotate(-360deg) scale(0.5);
}
</style>
