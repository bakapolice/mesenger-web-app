<template>
  <div id="app">
    <LayoutLogin v-if="$route.meta.layout === 'login'">
      <router-view />
    </LayoutLogin>
    <LayoutDefault v-else>
      <router-view />
    </LayoutDefault>
  </div>
</template>
<script>
/* eslint-disable */
import EventBus from './common/EventBus';
import LayoutDefault from './layouts/LayoutDefault.vue';
import LayoutLogin from './layouts/LayoutLogin.vue';
export default {
  components: {
    LayoutDefault,
    LayoutLogin,
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
  },
  mounted() {
    EventBus.on('logout', () => {
      this.logOut();
    });
  },
  beforeDestroy() {
    EventBus.remove('logout');
  },
};
</script>