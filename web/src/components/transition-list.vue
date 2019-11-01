<template>
  <transition-group :name="name" tag="div">
    <slot></slot>
  </transition-group>
</template>

<script>
export default {
  name: 'transition-list',
  props: {
    name: {
      type: String,
      default: 'list'
    }
  }
}
</script>

<style>
  .list-enter {
    opacity: 0;
    transform: translateY(-100px);
  }
  .list-leave-to{
    opacity: 0;
    animation: bounceOutRight .5s;
  }
  .list-enter-active{
    transition: all .5s;
    transition-delay: .5s;
  }
  .list-leave-active {
    transition: all .5s;
  }

  .listde-enter {
    opacity: 0;
    transform: translateY(-100px);
  }
  .listde-leave-to{
    opacity: 0;
    animation: bounceOutRight .5s;
  }
  .listde-enter-active, .listde-leave-active{
    transition: all .5s;
  }
  @keyframes bounceOutRight {
  0% {
    transform: translateX(0px)
  }
  25% {
    transform: translateX(-50px)
  }
  100% {
    transform: translateX(100%)
  }
}
</style>
