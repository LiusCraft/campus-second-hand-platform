<template>
  <v-container>
    <v-card elevation="0">
      <v-tabs
          center-active
          v-model="currentTab"
      >
        <v-tab key="buyOrders">购买订单</v-tab>
        <v-tab key="sells">卖出订单</v-tab>
      </v-tabs>
      <v-tabs-items v-model="currentTab">
        <v-tab-item key="buyOrders">
          <v-chip-group v-if="currentTab===0" class="ml-1" color="primary" v-model="orderStatus" mandatory @change="getList">
            <v-chip key="category-all" value="-1" :disabled="loading">全部</v-chip>
            <v-chip v-for="(orderStatusItem) in orderStatusBuyList" :disabled="loading" :value="orderStatusItem.value"
                    :key="'category-'+orderStatusItem.value">{{ orderStatusItem.text }}
            </v-chip>
          </v-chip-group>
          <v-divider class="mb-2"/>
          <order-list admin show-buy show-sell v-if="orderList.data.length>0" :rest-items="getList" :order-items="orderList.data"/>
          <v-sheet v-else class="pa-10 text-center" >
            未查询到订单信息
          </v-sheet>
          <div class="text-center mt-5" v-if="maxPage>1">
            <v-pagination
                :disabled="loading"
                v-model="page"
                @input="getList"
                :length="maxPage"
                total-visible="10"
            ></v-pagination>
          </div>
        </v-tab-item>
        <v-tab-item key="sells">
          <v-chip-group v-if="currentTab===1" class="ml-1" color="primary" v-model="orderStatus" mandatory @change="getList">
            <v-chip key="category-all" value="-1" :disabled="loading">全部</v-chip>
            <v-chip v-for="(orderStatusItem) in orderStatusSellList" :disabled="loading" :value="orderStatusItem.value"
                    :key="'category-'+orderStatusItem.value">{{ orderStatusItem.text }}
            </v-chip>
          </v-chip-group>
          <v-divider class="mb-2"/>
          <order-list admin show-buy show-sell :rest-items="getList" v-if="orderList.data.length>0" :order-items="orderList.data" sell/>
          <v-sheet v-else class="pa-10 text-center" >
            未查询到订单信息
          </v-sheet>
          <div class="text-center mt-5" v-if="maxPage>1">
            <v-pagination
                :disabled="loading"
                v-model="page"
                @input="getList"
                :length="maxPage"
                total-visible="10"
            ></v-pagination>
          </div>
        </v-tab-item>
      </v-tabs-items>

    </v-card>
  </v-container>
</template>
<script>
import { API_ORDERS_GET_LIST } from "@/apis/adminOrder";
import OrderList from "@/components/OrderList/index.vue";

export default {
  components: {OrderList},
  data() {
    return{
      currentTab: 0,
      orderStatusBuyList: [
        {
          text: "待确认",
          value: 2
        },
        {
          text: "待发货",
          value: 4
        },
        {
          text: "待收货",
          value: 5
        },
        {
          text: "已收货",
          value: 1
        },
        {
          text: "已取消",
          value: 0
        }
      ],
      orderStatusSellList: [
        {
          text: "待确认",
          value: 2
        },
        {
          text: "待发货",
          value: 4
        },
        {
          text: "已收货",
          value: 1
        },
        {
          text: "已取消",
          value: 0
        }
      ],
      loading: false,
      orderList: {
        data: [],
        total: 0,
      },
      page: 1,
      orderStatus: -1
    }
  },
  computed: {
    maxPage() {
      return Math.ceil(this.orderList.total/10)
    }
  },
  mounted() {
  },
  methods: {
    changeTab() {
      this.orderStatus = -1;
      this.page = 1;
      this.getList();
    },
    getList() {
      API_ORDERS_GET_LIST(this.currentTab, this.page, 10, this.orderStatus).then(({data})=>{
        this.orderList.total = data.data.count || 0;
        this.orderList.data = data.data.data || [];
      });
    },
  },
  watch:{
    currentTab() {
      this.changeTab();
    }
  }
}
</script>