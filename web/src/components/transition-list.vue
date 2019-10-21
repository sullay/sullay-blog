<template>
  <transition-group name="list" tag="div">
    <slot></slot>
  </transition-group>
</template>

<script>
export default {
  name: 'transition-list'
}
</script>

<style>
  .list-enter {
    opacity: 0;
    transform: translateY(-100px);
  }
  .list-leave-to{
    opacity: 0;
    animation: bounceOutRight 1s;
  }
  .list-enter-active,.list-leave-active {
    transition: all 1s;
  }
  @keyframes bounceOutRight {
  0% {
    transform: translateX(0px)
  }
  50% {
    transform: translateX(-50px)
  }
  100% {
    transform: translateX(100%)
  }
}
</style>
