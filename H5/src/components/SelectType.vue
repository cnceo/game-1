<template>
  <div class="select-type">
    <ul>
      <li v-for="(item, index) in types" :key="index" class="row">
        <span class="label" @click="selectType(index)" style="border-color: #f00" v-if="item.select"></span>
        <span class="label" @click="selectType(index)" v-else></span>
        <div class="select-info">
          <img :src="item.img" v-show="!!item.img"/>
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
      this.types.forEach((item, ids) => {
        if (ids === index) {
          item.select = true
        } else {
          item.select = false
        }
      })
      this.$emit('on-select', this.types)
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
      flex: 0 0 auto;
      .label{
        flex: 0 0 50px;
        width: 50px;
        height: 50px;
        border: 3px solid #fff;
      }
      .select-info{
        flex: 1
      }
    }
  }
  
}
</style>
