<template>
  <div>
    <v-app-bar dark app color="primary">
      <v-app-bar-title>二手交易平台</v-app-bar-title>
      <v-spacer/>


        <v-toolbar-items>
          <v-btn text to="/">首页</v-btn>
          <template v-if="userInfo">
          <v-btn text to="/manager">
            商品管理
          </v-btn>
          <v-btn text to="/order">订单管理</v-btn>
          <v-btn text to="/admin" v-if="userInfo.data.roleId===1">管理后台</v-btn>
          <v-btn text @click="outLogin()">退出登录</v-btn>
          </template>
        </v-toolbar-items>


      <v-btn icon @click="openUser()" v-if="!userInfo">
        <v-icon>mdi-account</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
  </div>
</template>
<script>
import {mapGetters} from "vuex";

export default {
  computed: {
    ...mapGetters([
        "userInfo"
    ])
  },
  methods: {
    outLogin() {
      localStorage.removeItem("token");
      window.location.href = "/#/login"
      window.location.reload();
    },
    openUser() {
      if(!this.userInfo) {
        this.$router.push({name: "Login"})
        console.log(this.userInfo)
      }
    }
  },
  mounted() {
  },
  watch: {
    userInfo(newV){
      if(newV) {
        this.$router.push({name: "Home"})
      }
    }
  }
}
</script>