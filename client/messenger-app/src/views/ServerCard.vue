<template>
<el-card class="box-card">
  <div slot="header" class="clearfix">
    <span>{{serverName}}</span>
    <el-button v-if="!isMember" style="float: right; padding: 3px 0" type="text" @click="buttonClickHandler">Join</el-button>
    <el-button v-else style="float: right; padding: 3px 0" type="success" disabled>Joined</el-button>
  </div>
  <div class="text item">
    {{ desc }}
  </div>
</el-card>
</template>
<script>
export default {
  name: 'server-card',
  props: {
    img: {
        type: String,
        default: null
    },
    serverName: {
        type: String,
        default: 'null'
    },
    desc: {
      type: String,
      default: "Описание отсутствует!"
    },
    isMember: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    buttonClickHandler() {
        this.$emit('buttonClickHandler')
    }
  }
};
</script>
<style>
  .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
</style>
