<template>
  <v-dialog :value="value" @input="changeDialog" fullscreen persistent>
    <v-card>
      <v-toolbar dense>
        <v-toolbar-title>商品: {{goodTitle}}</v-toolbar-title>
        <v-spacer/>
        <v-btn icon @click="changeDialog(false)">
          <v-icon>
            mdi-close
          </v-icon>
        </v-btn>
      </v-toolbar>
      <div class="mt-4"></div>
      <template v-if="!goodData">
        <v-card-text class="text-center">
          <v-skeleton-loader type="article"></v-skeleton-loader>
          正在加载, 喝杯coffee等待一下吧~
        </v-card-text>
      </template>
      <template v-else>
        <v-card-text class="mb-12">
          <v-img height="150px" src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"/>
          <v-list dense class="pa-0">
            <v-list-item>
              商品名: {{goodData.name}}
            </v-list-item>
            <v-divider/>
            <v-list-item>
              剩余数量: {{goodData.count}}
            </v-list-item>
            <v-divider/>
            <v-list-item>
              商品卖家: {{goodData.user.nickname}}
            </v-list-item>
            <v-divider/>
            <v-list-item>
              商品分类: {{goodData.category.name}}
            </v-list-item>
            <v-list-item>
              上架时间: {{goodData.gmtCreate}}
            </v-list-item>
          </v-list>
          <v-divider/>
          <p class="text-subtitle-1 mt-2 ml-2 mb-0">商品描述</p>
          <div class="ma-2">
            {{goodData.description}}
          </div>
        </v-card-text>
        <v-card-actions class="elevation-2" style="position: absolute; bottom: 0; width: 100%">
          <v-btn dar color="error" disabled block elevation="0" v-if="selfGood">无法购买, 您是卖家</v-btn>
          <v-row v-else>
            <v-col>
              <v-text-field v-model="buyCount" dense outlined label="购买数量" type="number" value="1" hide-details/>
            </v-col>
            <v-col>
              <v-btn color="primary" :disabled="goodData.count<1" block elevation="0"  @click="buyGood()">{{goodData.count>0?"购买此商品":"商品已售空"}}</v-btn>
            </v-col>
          </v-row>
        </v-card-actions>
      </template>
    </v-card>
  </v-dialog>
</template>
<script>
import {API_GOOD_BUY, API_GOOD_GET_INFO} from "@/apis/good";
import {mapGetters} from "vuex";

export default {
  props: {
    goodId: {
      type: Number,
    },
    value: {
      type: Boolean,
      default: false
    },
    restItems: {
      type: Function,
      default: ()=>{}
    }
  },
  data() {
    return{
      goodData: null,
      buyCount: 1,
    }
  },
  computed: {
    ...mapGetters([
        "userInfo"
    ]),
    goodTitle() {
      if (this.goodData) return this.goodData.name;
      return "正在加载...";
    },
    selfGood() {
      if (!this.goodData) return false;
      return !!(this.userInfo && this.userInfo.data.id === this.goodData.user.id);

    }
  },
  methods: {
    changeDialog(e) {
      this.$emit("input", e);
    },
    buyGood() {
      if (!(this.buyCount>0)) {
        this.$vuetifyFunc.snackbar.error("请输入购买数量");
        return;
      }
      API_GOOD_BUY(this.goodData.id, this.buyCount).then(({data})=>{
        this.$vuetifyFunc.snackbar
            .show(data.msg, data.state?"success":"error");
        if (!data.state) {
          return;
        }
        this.restItems();
        this.getGoodInfo();
      })
    },
    getGoodInfo() {
      this.buyCount = 1;
      if (this.goodId == null) return;
      this.loading = true;
      API_GOOD_GET_INFO(this.goodId).then(({data})=>{
        if (!data.state){
          this.goodData = null;
          return;
        }
        this.goodData = data.data.data;
      }).finally(()=>{
        this.loading = false;
      })
    }
  },
  watch:{
    goodId(e) {
      console.log(e);
      this.getGoodInfo();
    }
  }
}
</script>