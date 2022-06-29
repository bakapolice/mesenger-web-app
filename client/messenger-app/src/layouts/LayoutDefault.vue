<template>
  <el-container class="content">

    <el-aside width="70px" class="aside-menu">
      <el-menu class="el-menu-vertical-servers" :collapse="true" background-color="#545c64">
        <div class="nav-link">
          <el-button
              icon="el-icon-s-home"
              circle
              @click="(activeServer = null), activeServerChangeHandler()"
          ></el-button>
        </div>


        <el-menu-item
          v-for="(server, index) in servers"
          :key="index"
          :index="index.toString()"
          @click="(activeServer = server.id), activeServerChangeHandler()"
        >
          <i class="el-icon-menu"></i>
          <span slot="title">{{ server.serverName }}</span>
        </el-menu-item>

        <div class="nav-link">
          <el-button icon="el-icon-plus" circle @click="createServer"></el-button>
        </div>

        <router-link to="/server_list" class="nav-link">
        <el-button icon="el-icon-search" circle>
        </el-button>

        </router-link>
      </el-menu>
    </el-aside>

    <el-aside width="200px">
      <el-menu
        default-active="1"
        class="el-menu-vertical-dialogs"
        :collapse="false"
      >
        <template v-if="activeServer === null">
          <el-button class="h-full-width">
            <router-link v-if="currentUser" to="/friends" class="nav-link"
              >Friend List</router-link
            >
          </el-button>
          <br> <br>
          Список диалогов
          <el-menu-item
            v-for="(dialog, index) in dialogs"
            :key="index"
            :index="index.toString()"
            :style="{ 'padding-left': '0'}"
            @click="dialogHandler(dialog.chatId, dialog.recipientId)"
          >
            <i class="el-icon-menu"></i>
            <span slot="title"
              >{{ dialog.recipientFirstName }}
              {{ dialog.recipientLastName }}</span
            >
          </el-menu-item>
        </template>

        <template v-else>
          <el-button class="h-full-width" type="info" plain @click="createChat"
            >Create chat</el-button
          >
          <el-menu-item
            v-for="(chat, index) in activeServerChats"
            :key="index"
            :index="index.toString()"
            @click="openChat(chat.id)"
          >
            <i class="el-icon-menu"></i>
            <span slot="title">{{ chat.chatName }}</span>
          </el-menu-item>
        </template>
      </el-menu>

      <div v-if="currentUser" class="navbar-nav">
        <li class="nav-item">
          <router-link to="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" />LogOut
          </a>
        </li>
      </div>
    </el-aside>

    <el-container>
      <el-main class="scrollable"><slot /></el-main>
    </el-container>

    <el-aside width="200px">
      <el-menu
        default-active="2"
        class="el-menu-vertical-members"
        :collapse="false"
      >
          Members
          <el-menu-item
            v-for="(member, index) in members"
            :key="index"
            :index="index.toString()"
          >
            <i class="el-icon-menu"></i>
            <span slot="title"
              >{{ member.firstName }}
              {{ member.lastName }}</span
            >
          </el-menu-item>
      </el-menu>
    </el-aside>

  </el-container>
</template>

<script>
/* eslint-disable */
import EventBus from '../common/EventBus';
import ServerService from '../services/server.service';
import ChatService from '../services/chat.service';
import AuthService from '../services/auth.service';
export default {
  data() {
    return {
      servers: [],
      dialogs: [],
      members: [],
      activeServerChats: [],
      activeServer: null,
      activeChat: null,
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    userDialogs() {},
    serverChats() {},
  },
  methods: {
    getMembers(){
      ServerService.getServerMembers(this.activeServer)
      .then((response)=>{
        this.members = response.data;
      },
      (error)=>{
        console.log(error);
      });
    },
    openChat(chatId) {
      this.$router.push({
        name: 'chat-room',
        params: {
          id: chatId,
        },
      });
    },
    dialogHandler(chatId, recipientId) {
      this.$router.push({
        name: 'dialog',
        params: {
          id: chatId,
          sel: recipientId
        },
      });
    },
    activeServerChangeHandler() {
      console.log('ACTIVE SERVER ID ' + this.activeServer);
      if (this.activeServer == null) {
        this.members= [];
        this.getDialogs();
      }
      if (this.activeServer != null) {
        this.getServerChats();
        this.getMembers();
      }
    },
    createServer() {
      this.$prompt('Please input server name', 'Create server', {
        confirmButtonText: 'Create server',
        cancelButtonText: 'Cancel',
      })
        .then(({ value }) => {
          ServerService.createServer(value, this.currentUser.id).then(
            (response) => {
              this.$message({
                type: 'success',
                message: response.data + '. Server created with name:' + value,
              });
              this.refreshServerList();
              this.$router.go();
            },
            (error) => {
              this.$message({
                type: 'failed',
                message: 'Server create fail. Error: ' + error,
              });
            }
          );
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Input canceled',
          });
        });
    },
    createChat() {
      this.$prompt('Please input chat name', 'Create chat', {
        confirmButtonText: 'Create chat',
        cancelButtonText: 'Cancel',
      })
        .then(({ value }) => {
          ChatService.createChat(
            value,
            this.activeServer,
            this.currentUser.id
          ).then(
            (response) => {
              this.$message({
                type: 'success',
                message: response.data + '. Chat created with name:' + value,
              });
              this.refreshChatList();
            },
            (error) => {
              this.$message({
                type: 'failed',
                message: 'Server create fail. Error: ' + error,
              });
            }
          );
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: 'Input canceled',
          });
        });
    },
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
    getAllServers() {
      ServerService.getUserServers(this.currentUser.id).then(
        (response) => {
          this.servers = response.data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getServerChats() {
      ServerService.getServerChats(this.activeServer).then(
        (response) => {
          this.activeServerChats = response.data;
          this.$router.push('/room/'+this.activeServerChats[0].id);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    refreshServerList() {
      this.getAllServers();
    },
    refreshChatList() {
      this.getServerChats();
    },
    getDialogs() {
      ChatService.getDialogs(this.currentUser.id)
        .then((response) => {
          this.dialogs = response.data;
          console.log(this.dialogs);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  mounted() {
    AuthService.getValidate()
    .then((response) => {}),
    (error) => {
      this.content =
          (error.response && error.response.data && error.response.data.message) ||
          error.message ||
          error.toString();
        if (error.response && error.response.status === 403) {
          EventBus.dispatch("logout");
          }
    }
    this.getAllServers();
    this.activeServer == null;
  },
  beforeDestroy() {
    EventBus.remove('logout');
  },
};
</script>

<style>
.el-menu-vertical-servers:not(.el-menu--collapse) {
  min-height: 100vh;
}
.el-menu-vertical-dialogs:not(.el-menu--collapse) {
  width: 100%;
}

.el-menu:not(.el-menu--collapse) {
  padding: 10px;
  border-right: none;
}

.el-aside {
  border-right: solid 1px #e6e6e6;
}

.aside-menu {
  margin-right: -7px;
  border-right: none;
}

.el-menu--collapse{
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  padding: 8px 0;
}

.el-menu .el-menu-item {
  padding: 0 44px;
}

.el-tooltip{
  padding: 0;

}

.navbar-nav{
  padding-left: 40px;
}

.aside-menu {
  min-height: 100vh;

}

.el-aside {
  display: flex;
  flex-direction: column;
  width: 200px;
  min-height: 100vh;
  justify-content: space-between;
  align-items: flex-start;
}

.h-full-width {
  width: 100%;
}

.scrollable{
    max-height: calc(100vh - 20px);
    overflow: auto;
}

</style>
