<template>
  <div>
    <div id="main-content" class="container">
      <div class="row">
        <div class="col-md-12">
          <table id="conversation" class="table table-striped">
            <thead>
              <tr>
                <th>Greetings</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in received_messages" :key="index">
                <td>{{ item.senderFirstName }} : {{ item.content }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <form class="form-inline" >
        <div class="form-group">
          <label for="name"></label>
          <input
            type="text"
            id="name"
            class="form-control"
            v-model="send_message"
            placeholder="Enter the message..."
            @keypress.enter.prevent="send"
          />
        </div>
        <el-button
          type="primary"
          id="send"
          class="btn btn-default"
          @click.prevent="send"
        >
          Отправить
        </el-button>
      </form>
    </div>
  </div>
</template>
<script>
/* eslint-disable */
import SockJs from 'sockjs-client';
import Stomp from 'webstomp-client';
import ChatService from '../services/chat.service';
import UserService from '../services/user.service';

export default {
  name: 'conversation',
  data() {
    return {
      recipient: null,
      send_message: null,
      received_messages: [],
      connected: false,
    };
  },
  methods: {
    send() {
      if (this.stompClient && this.stompClient.connected) {
        let msg = '';
        if (this.send_message.trim() !== '') {
          msg = {
            senderId: this.currentUser.id,
            recipientId: this.recipient.id,
            senderFirstName: this.currentUser.firstName,
            senderLastName: this.currentUser.lastName,
            recipientFirstName: this.recipient.firstName,
            recipientLastName: this.recipient.lastName,
            content: this.send_message,
            timestamp: this.currentDateTime(),
          };
        }
        if (this.recipient.id != this.currentUser.id) {
          this.received_messages.push(msg);
          }
        this.stompClient.send('/app/chat', JSON.stringify(msg), {});
        this.send_message = "";
      }
    },
    connect() {
      this.socket = new SockJs('http://localhost:8082/ws');
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          this.stompClient.subscribe(
            '/user/' + this.currentUser.id + '/queue/messages',
            (tick) => {
              this.received_messages.push(JSON.parse(tick.body));
            }
          );
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    getChatMessages() {
      ChatService.getChatMessagesByChatId(this.chatId)
        .then((response) => {
          this.received_messages = response.data;
        })
        .catch((e) => {
          console.error(e);
        });
    },
    getRecipientUser() {
      UserService.getUser(this.recipientId)
        .then((response) => {
          this.recipient = response.data;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    currentDateTime() {
      //yyyy-MM-dd'T'HH:mm:ss.SSSX
      const current = new Date();
      const date =
        current.getFullYear() +
        '-' +
        (current.getMonth() + 1) +
        '-' +
        current.getDate();
      const time =
        current.getHours() +
        ':' +
        current.getMinutes() +
        ':' +
        current.getSeconds();

      const dateTime = date + ' ' + time;

      return dateTime;
    },
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    chatId() {
      return this.$route.params.id;
    },
    recipientId() {
      return this.$route.params.sel;
    },
  },
  watch: {
    chatId: {
      handler(val, oldVal) {
        this.disconnect();
        this.getRecipientUser();
        this.getChatMessages();
        this.connect();
      },
      immediate: true,
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  },
};
</script>
<style scoped>
#main-content{
    min-height: calc(100vh - 60px);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.form-inline{
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.form-group{
  flex-grow: 1;
}
.form-control{
  width: 100%;
}
</style>