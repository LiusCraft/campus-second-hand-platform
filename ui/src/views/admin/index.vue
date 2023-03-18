<template>
  <div v-if="userInfo">
    <v-navigation-drawer permanent app>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="title">
           管理员后台
          </v-list-item-title>
          <v-list-item-subtitle>
            您好！{{userInfo.data.nickname}}
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list
          dense
          nav
      >
        <v-list-item
            v-for="item in leftItems"
            :key="item.title"
            link
            :to="item.to"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <template v-slot:append>
        <div class="pa-2">
          <v-btn block color="error" elevation="0" to="/">退出后台</v-btn>
        </div>
      </template>
    </v-navigation-drawer>
    <v-main>
      <router-view/>
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
  data() {
    return {
      leftItems: [
        {
          icon: "mdi-cloud-circle",
          title: "统计",
          to: {
            name: "AdminHome"
          }
        },
        {
          icon: "mdi-account-box",
          title: "用户管理",
          to: {
            name: "AdminUsers"
          }
        },
        {
          icon: "mdi-order-bool-descending-variant",
          title: "订单管理",
          to: {
            name: "AdminOrders"
          }
        },
        {
          icon: "mdi-tag-text-outline",
          title: "分类管理",
          to: {
            name: "AdminCategory"
          }
        },
        {
          icon: "mdi-list-box",
          title: "商品管理",
          to: {
            name: "AdminGoods"
          }
        },
        {
          icon: "mdi-message-alert",
          title: "反馈处理",
          to: {
            name: "AdminFeedBack"
          }
        }
      ]
    }
  },
  mounted() {
    if(this.userInfo.data.roleId!==1){
      this.$vuetifyFunc.snackbar.error("您不是管理员！")
      this.$router.push({path: "/"})
    }

  }
}
</script>