<template>
      <el-container>
        <el-header class="p-0"><nav class="navbar navbar-expand navbar-dark bg-dark">
            <a href class="navbar-brand" @click.prevent>Messenger</a>
            <div v-if="!currentUser" class="navbar-nav ml-auto">
              <li class="nav-item">
                <router-link to="/register" class="nav-link">
                  <font-awesome-icon icon="user-plus" />Sign Up
                </router-link>
              </li>
              <li class="nav-item">
                <router-link to="/login" class="nav-link">
                  <font-awesome-icon icon="sign-in-alt" />Login
                </router-link>
              </li>
            </div>
          </nav>
        </el-header>
        <el-main><slot /></el-main>
      </el-container>
</template>

<script>
/* eslint-disable */
export default {
  data() {
    return {};
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
  },
};
</script>