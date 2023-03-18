<template>
  <v-list two-line subheader v-if="orderItems.length" >
    <template v-for="item in orderItems">
      <v-list-item
          :key="'item-'+item.id"
          dense
      >
        <v-list-item-content>
          <v-list-item-title >
            <strong class="grey--text mr-1">单号:{{item.id}} </strong>
            <span>{{item.goodVo.name}}</span>
          </v-list-item-title>
          <v-list-item-subtitle>
            <v-chip-group>
              <v-chip small label>数量:{{item.count}}</v-chip>
              <v-chip small label>购买时间:{{item.gmtCreate}}</v-chip>
              <v-chip small label v-if="showSell">卖家: {{item.sellUserVo.nickname}}</v-chip>
              <v-chip small label v-if="showBuy">买家: {{item.buyUserVo.nickname}}</v-chip>
              <v-chip small label>状态: {{item.orderStatus.msg}}</v-chip>
            </v-chip-group>
          </v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action v-if="btnInfo(item.orderStatus.code)">
          <v-btn elevation="0" color="primary" small @click="changeOrderStatus(item.id,btnInfo(item.orderStatus.code).value)">
            {{btnInfo(item.orderStatus.code).text}}
          </v-btn>
          <v-btn dark v-if="!sell && item.orderStatus.code!== 0 && item.orderStatus.code!==1" elevation="0" color="red" x-small @click="changeOrderStatus(item.id, 0)">
            取消订单
          </v-btn>
        </v-list-item-action>
      </v-list-item>
      <v-divider :key="'divider-'+item.id"/>
    </template>
  </v-list>
</template>
<script>
import {API_ORDERS_PUT_STATUS} from "@/apis/order";
import {API_ADMIN_ORDERS_PUT_STATUS} from "@/apis/adminOrder";

export default {
  name: "OrderList",
  props: {
    orderItems: {
      type: Array,
      default: ()=>[]
    },
    restItems: {
      type: Function,
      default: ()=>{}
    },
    sell: {
      type: Boolean,
      default: false
    },
    showSell: {
      type: Boolean,
      default: false,
    },
    showBuy: {
      type: Boolean,
      default: false
    },
    admin: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    btnInfo(status) {
      if (this.sell)
        switch (status) {
          case 4:
            return {
              value: 5,
              text: "发货"
            }
          default:
            return false;
        }
      else
      switch (status){
        case 2:
          return {
            value: 4,
            text: "确认订单"
          }
        case 5:
          return {
            value: 1,
            text: "确认收货"
          }
        default:
          return false;
      }
    },
    changeOrderStatus(id, value) {
      let apiFun = API_ORDERS_PUT_STATUS
      if (this.admin)
        apiFun = API_ADMIN_ORDERS_PUT_STATUS
      apiFun(id, value).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error")
        if (data.state){
          this.restItems();
        }
      })
    }
  }
}
</script>