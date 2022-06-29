<template>
  <el-container>
    <el-header>
    <i class="el-icon-sunrise-1"></i> Friends </el-header>
    <el-main>
      <ul>
        <li v-for="(user, index) in friends" :key="index">
          <friend-row
            :username="user.login"
            @openDialog="openDialog(user.id)"
          />
        </li>
      </ul>
    </el-main>
  </el-container>
</template>
<script>
/* eslint-disable */
import UserService from '../services/user.service';
import ChatService from '../services/chat.service';
import FriendRow from './FriendRow.vue';
export default {
  components: { FriendRow },
  name: 'friend-list',
  data() {
    return {
      friends: [],
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    openDialog(id) {
      //alert("Send message to user id: " + id + " from " + this.currentUser.username);
      //const chatId = this.getChatId(this.currentUser.id, id);
      this.getChatId(this.currentUser.id, id);
    },
    getChatId(senderId, recipientId) {
      ChatService.getChatId(senderId, recipientId)
        .then((response) => {
          let chatId = response.data;
          if (chatId == '') chatId = senderId + '_' + recipientId;
          this.$router.push({
            name: 'dialog',
            params: {
              id: chatId,
              sel: recipientId,
            },
          });
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getAllUsers() {
      UserService.getUserFriends()
        .then((response) => {
          this.friends = response.data;
          console.log(this.friends);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    refreshList() {
      this.getAllUsers();
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
    this.getAllUsers();
  },
};
</script>
<style>
li {
  list-style-type: none; /* Убираем маркеры */
}
ul {
  padding-left: 0; /* Сдвигаем список влево */
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: '';
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}
</style>

