<template>
  <el-row :gutter="20" class="friend-row">
    <el-col :span="1">
      <div class="grid-content bg-purple">
            <div>
                <el-avatar icon="el-icon-user-solid"></el-avatar>
            </div>
      </div>
    </el-col>
    <el-col :span="16">
        <div class="grid-content bg-purple">
                <el-descriptions :title="username">
                    <el-descriptions-item label="Status">{{status}}</el-descriptions-item>
                </el-descriptions>
        </div>
    </el-col>
    <el-col :span="4">
      <div class="grid-content bg-purple">
        <el-button icon="el-icon-message" circle @click="openDialog"></el-button>
        <el-button icon="el-icon-delete" circle></el-button>
      </div>
    </el-col>
  </el-row>
</template>
<script>
export default {
  name: 'FriendRow',
  props: {
    username: {
        type: String,
        default: 'test-username'
    },
    status: {
        type: String,
        default: 'test-status'
    }
  },
  methods: {
    openDialog() {
        this.$emit('openDialog')
    }
  }
};
</script>
<style scoped>
.el-descriptions__header{
  margin: 0;
}
</style>