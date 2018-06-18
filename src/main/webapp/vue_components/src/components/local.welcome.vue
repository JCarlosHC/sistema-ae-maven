<template>
    <div class="jumbotron">
      <h1>{{ project }}</h1>
      <p class="lead">We seek to be <b>the best online teaching platform.</b>
        <b>Register now</b> and acquire <b>100 USD</b> to take any of our courses.</p>
      <p><a :href="link" class="btn btn-primary btn-lg">Register now! &raquo;</a></p>
    </div>
</template>

<script>
export default {
  name: 'welcome',
  props: {
    project: {
      type: String,
      required: true
    },
    link: {
      type: String,
      requide: true
    }
  },
  mounted() {
    this.$parent.$on('welcomeSelectedID', x => {
      this.project = x;
    })
  }
}
</script>