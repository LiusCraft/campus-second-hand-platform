<template>
  <v-app id="app">
    <snackbar ref="rootSnackbarRef"></snackbar>
    <router-view/>
  </v-app>
</template>

<script>
import {mapActions} from "vuex";
import Snackbar from "@/vuetify/snackbar/index.vue";
export default {
  name: 'App',
  components: {Snackbar},
  data: () => ({
    //
  }),
  mounted() {
    this.getInfo().then(ok=>{
      if (ok) {
        this.$vuetifyFunc.snackbar.success("欢迎回来~");
      }else {
        this.$vuetifyFunc.snackbar.error("同学，赶紧登录享受更多服务吧~");
      }
    });
  },
  methods: {
    ...mapActions([
        "login",
        "getInfo"
    ])
  }
};
</script>
