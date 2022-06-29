<template>
  <el-container>
    <el-header>Dialogs</el-header>
    <el-main>
      <ul>
        <li v-for="(dialog, index) in dialogs" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>
                {{ dialog.chatId }}. {{ dialog.recipientFirstName }} {{ dialog.recipientLastName }}</span
              >
              <el-button
                style="float: right; padding: 3px 0"
                type="text"
                @click="chatHandler(dialog.chatId)"
                >Send message</el-button
              >
            </div>
          </el-card>
        </li>
      </ul>
    </el-main>
  </el-container>
</template>
<script>
/* eslint-disable */
import chatService from '../services/chat.service';
export default {
  data() {
    return {
      dialogs: [],
    };
  },
  methods: {
    getDialogs() {
      chatService
        .getDialogs(this.currentUser.id)
        .then((response) => {
          this.dialogs = response.data;
          console.log(this.dialogs);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },

  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  mounted() {
        if (!this.currentUser) {
      this.$router.push('/login');
    }
    this.getDialogs();
  }
};
</script>
