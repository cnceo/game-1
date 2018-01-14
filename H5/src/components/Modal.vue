<template>
  <div class="game-modal" v-show="show">
    <div class="modal-content" :class="{'s-right': sites}">
      
      <div class="modal-bg" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: -1">
        <slot name="modal-bg" height="100%">
        </slot>
      </div>
      
      <div class="game-title">
        <slot name="title"></slot>
      </div>  
      <div class="game-body">
        <slot name="body"></slot>
      </div>
      <div class="game-foot">
        <slot name="foot"></slot>
      </div>
      <span class="close" @touchstart="closeModal" v-show="showX">
        <img src="../assets/imgs/img_Daikai_cuohao.png" alt="" width="100%">
      </span>
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
    },
    showClose: {
      type: Boolean,
      default: true
    },
    site: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    showModal (val) {
      this.show = val
    },
    showClose (val) {
      this.showX = val
    },
    // 弹窗位置（中间或右侧）
    site (val) {
      this.sites = val
    }
  },
  data () {
    return {
      show: false,
      showX: true,
      sites: false
    }
  },
  created () {
    this.show = this.showModal
    this.showX = this.showClose
    this.sites = this.site
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
    right: 50%;
    width: 60%;
    height: 68%;
    transform: translate(50%, -50%);
   // background: rgba(0, 0, 0, .8);
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
      top: -20px;
      right: -20px;
      width: 80px;
      color: #fff;
    }
    .game-body{
      padding: 20px 0 30px 0;
    }
  }
  .modal-content.s-right{
    right: 30px;
    transform: translate(0, -50%);
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
