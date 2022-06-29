<template>
  <div class="container">
    <header class="jumbotrom">
      <h3>
        <strong>{{ currentUser.username }}</strong> Profile
      </h3>
    </header>
    <p>
      <strong>ID:</strong>
      {{ currentUser.id }}
    </p>
    <p>
      <strong>Username:</strong>
      {{ currentUser.username }}
    </p>
    <p>
      <strong>FirstName:</strong>
      {{ currentUser.firstName }}
    </p>
    <p>
      <strong>LastName:</strong>
      {{ currentUser.lastName }}
    </p>
    <p>
      <strong>Email:</strong>
      {{ currentUser.email }}
    </p>
  </div>
</template>
<script>
export default {
  name: 'Profile',
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  },
};
</script>
