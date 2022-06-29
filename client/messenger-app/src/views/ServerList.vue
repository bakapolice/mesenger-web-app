<template>
  <el-container>
    <el-header>Servers</el-header>
    <el-main>    
      <ul style="overflow:auto">
          <li v-for="(server, index) in servers" :key="index">
           <server-card v-if="!isJoined(server)"
            :serverName="server.serverName"
            :desc="server.serverDescription"
            :isMember="false"
            @buttonClickHandler="joinHandler(server.id)"
          />
          <server-card v-else
            :serverName="server.serverName"
            :desc="server.serverDescription"
            :isMember="true"
          />
            </li>
      </ul>
    </el-main>
  </el-container>
</template>
<script>
/* eslint-disable */
import ServerService from '../services/server.service';
import ServerCard from './ServerCard.vue';
export default {
  components: { ServerCard },
  data() {
    return {
    servers: [],
    userServers: []
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  methods: {
    load () {
        this.servers.length;
      },
    isJoined(server){
      let searched = this.userServers.filter(function(elem) {
        if(elem.id === server.id) return elem;
      });
      if(searched.length > 0) return true;
      else return false;
    },
    getAllServers() {
      ServerService.getAllServers().then(
        (response) => {
          this.servers = response.data;
          console.log("ALL S: ");
          console.log(this.servers);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getUserServers(){
      ServerService.getUserServers(this.currentUser.id)
      .then((response) => {
          this.userServers = response.data;
          console.log("USER SRV:");
          console.log(this.userServers);
        },
        (error) => {
          console.log(error);
        });
    },
    joinHandler(serverId){
        ServerService.serverJoin(serverId, this.currentUser.id)
        .then((response) =>{
          this.$router.go();
        },
        (error)=>{
          console.log(error);
        })
    },
  },
  watch: {
watch:{
    $route (to, from){
        this.getAllServers();
    }
} 
  },
  mounted() {
     if (this.$route.name !== 'login' && !this.currentUser) {
      this.$router.push('/login');
    };
    this.getAllServers();
    this.getUserServers();
  },
};
</script>
<style scoped>
.el-card{
  margin: 10px;
}
</style>