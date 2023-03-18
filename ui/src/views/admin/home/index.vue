<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card >
          <v-card-title>{{statistics.order}} 下单</v-card-title>
          <v-card-text>当前平台已完成单数</v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <v-card >
          <v-card-title>{{statistics.good}} 商品</v-card-title>
          <v-card-text>当前商品上架数量</v-card-text>
        </v-card>
      </v-col>
      <v-col>
        <v-card>
          <v-card-title>{{statistics.user}} 用户</v-card-title>
          <v-card-text>当前平台注册用户数量</v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import {API_ADMIN_STATISTICS} from "@/apis/admin";

export default {
  data() {
    return{
      statistics: {
        good: 0,
        user: 0,
        order: 0
      }
    }
  },
  mounted() {
    this.getStatistics()
  },
  methods: {
    getStatistics() {
      API_ADMIN_STATISTICS().then(({data})=>{
        if (!data.state) {
          return;
        }
        this.statistics = data.data;
      })
    }
  }
}
</script>