<template>
  <transition-group name="listindex" tag="div">
    <slot></slot>
  </transition-group>
</template>

<script>
export default {
  name: 'transition-list-index'
}
</script>

<style>
  .listindex-enter {
    opacity: 0;
    transform: translateX(-200px);
  }
  .listindex-leave-to {
    opacity: 0;
  }

  .listindex-enter-active,.listindex-leave-active {
    transition: all 0.5s;
  }
</style>
