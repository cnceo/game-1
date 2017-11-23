<template>
  <div class="select-type">
    <ul>
      <li v-for="(item, index) in types" :key="index" class="row">
        <span class="label" @click="selectType(index)" style="border-color: #f00">
          <img src="../assets/imgs/img_Create_yes.png" alt=""  v-if="item.select" width="100%">
        </span>
        <div class="select-info1" v-if="bigImg">
          <img :src="item.img" v-show="!!item.img" width="100%"/>
          <span v-show="!!item.label" class="text">{{item.label}}</span>
        </div>
         <div class="select-info2" v-else>
          <img :src="item.img" v-show="!!item.img" height="100%"/>
          <span v-show="!!item.label" class="text">{{item.label}}</span>
        </div>
      </li>
      
    </ul>
  </div>
</template>

<script>
export default {
  name: 'app',
  props: {
    types: {
      type: Array,
      default: function () {
        return []
      }
    },
    bigImg: {
      type: Boolean,
      default: true
    }
  },
  watch: {
    types (val) {
    }
  },
  created () {
  },
  data () {
    return {
    }
  },
  methods: {
    selectType (index) {
      let value = ''
      this.types.forEach((item, ids) => {
        if (ids === index) {
          item.select = true
          value = item.value
        } else {
          item.select = false
        }
      })
      this.$emit('on-select', value)
    }
  }
}
</script>

<style scoped lang="less">
.select-type{
  ul {
    display: flex;
    flex-direction: row;
    align-items: center;
    .row{
      display: flex;
      flex-direction: row;
      align-items: center;
      margin-left: 30px;
      .label{
        flex: 0 0 60px;
        width: 60px;
        height: 60px;
        margin-right: 20px;
        background: url('../assets/imgs/img_Create_Marquee.png') 0 0 no-repeat;
        background-size: 100% 100%;
        img{
          position: relative;
          top: 12px;
        }
      }
      .select-info1{
        flex: 1;
      }
      .select-info2{
       height: 50px;
      }
    }
  }
  
}
</style>
