<template>
  <div class="game-modal" v-show="show">
    <div class="modal-content">
      <div class="game-title">
        <slot name="title"></slot>
      </div>  
      <div class="game-body">
        <slot name="body"></slot>
      </div>
      <span class="close" @click="closeModal">X</span>
    </div>
    <div class="modal-mask"></div>
  </div>
</template>

<script>
export default {
  name: 'app',
  props: {
    showModal: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    showModal (val) {
      this.show = val
    }
  },
  data () {
    return {
      show: false
    }
  },
  methods: {
    closeModal () {
      this.show = false
      this.$emit('on-close')
    }
  }
}
</script>

<style scoped lang="less">
.game-modal{
  
  .modal-content{
    position: fixed;
    top: 50%;
    left: 50%;
    width: 60%;
    height: 60%;
    transform: translate(-50%, -50%);
    background: rgba(0, 0, 0, .8);
    z-index: 1000;
    .game-title{
      position: absolute;
      top: -10px;
      left: 50%;
      width: 100%;
      transform: translateX(-50%);
    }
    .close{
      position: absolute;
      top: 0;
      right: 0;
      color: #fff;
    }
    .game-body{
      padding: 20px 0 30px 0;
    }
  }
  .modal-mask{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: transparent;
    z-index: 100;
  }
}
</style>
