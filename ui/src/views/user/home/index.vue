<template>
  <v-container>
    <v-banner elevation="0  " :app="showBar" color="white">
      <v-avatar
          color="primary accent-4"
          size="40"
      >
        <v-icon
            icon="mdi-lock"
            color="white"
        >
          mdi-cart
        </v-icon>
      </v-avatar>

      您好同学！这里是校园二手交易市场，您可以在这里购买到其它同学出售的物品。当然您如果自己有想出售的物品可上架到该平台，让其它同学看见并购买它！

      <template v-slot:actions v-if="userInfo">
        <v-btn text color="warning" @click="feedbackDialog.open=true">反馈</v-btn>
        <v-btn text color="deep-purple accent-4" to="/manager">管理商品</v-btn>
        <v-btn text color="deep-purple accent-4" @click="editGoodDialog=true">发布商品</v-btn>
      </template>
      <template v-slot:actions v-else>
        <v-btn text color="deep-purple accent-4" to="/login">前去登录</v-btn>
      </template>
    </v-banner>

    <v-carousel
        height="300"
        hide-delimiter-background
        show-arrows-on-hover
        class="mt-4 mb-3 rounded"
    >
      <v-carousel-item
          v-for="(goodItem, i) in hotList"
          :key="i"

          @click="openInfo(goodItem)"
      >
        <v-img  height="100%" :src="`/api/goods/img/${goodItem.id||0}`">
          <v-overlay absolute class="white--text fill-height pb-10">
            <v-card-title class="pa-2 align-end">{{ goodItem.name }}</v-card-title>
<!--            <v-card-subtitle v-if="goodItem.hot"><v-chip color="error">平台推荐</v-chip></v-card-subtitle>-->
          </v-overlay>
        </v-img>
      </v-carousel-item>
    </v-carousel>
    <v-chip-group color="primary" v-model="selectedCategory" mandatory @change="getList">
      <v-chip key="category-all" value="0" :disabled="loading">所有分类</v-chip>
      <v-chip v-for="(categoryItem) in categoryList" :disabled="loading" :value="categoryItem.id" :key="'category-'+categoryItem.id">{{categoryItem.name}}</v-chip>
    </v-chip-group>
    <v-divider class="mb-2"/>
    <v-row v-if="goodList.data.length">
      <v-col v-for="goodItem in goodList.data" :key="goodItem.id">
        <good-card :good-item="goodItem" :call="openInfo"/>
      </v-col>
    </v-row>
    <v-sheet v-else class="pa-10 text-center" >
      未查询到相关商品
    </v-sheet>
    <v-divider class="mt-4"/>
    <div class="text-center mt-5">
      <v-pagination
          :disabled="loading"
          v-model="page"
          :length="maxPage"
          @input="getList"
          total-visible="10"
      ></v-pagination>
    </div>
    <good-edit
        v-model="editGood"
        :dialog.sync="editGoodDialog"
        :save-call-back="saveGood"/>
    <good-info v-model="goodInfoDialog.open" :good-id="goodInfoDialog.id" :rest-items="getList"/>
    <v-dialog v-model="feedbackDialog.open" max-width="500px">
      <v-card>
        <v-toolbar>
          <v-toolbar-title>反馈</v-toolbar-title>
          <v-spacer/>
          <v-btn icon color="red" @click="feedbackDialog.open = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <v-card-text class="mt-3 pb-0">
          <v-textarea placeholder="请描述您要反馈的问题..." dense hide-details label="反馈" v-model="feedbackDialog.data" filled/>
        </v-card-text>
        <v-card-actions>
          <v-btn block color="primary" @click="saveFeedback()">反馈</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>
import {API_GET_GOOD_HOTS, API_GOOD_GET_LIST} from "@/apis/good"
import {API_CATEGORY_GET_LIST} from "@/apis/category";
import GoodEdit from "@/components/GoodEdit/index.vue";
import GoodInfo from "@/components/GoodInfo/index.vue";
import goodInfo from "@/components/GoodInfo/index.vue";
import {mapGetters} from "vuex";
import GoodCard from "@/components/GoodCrad/index.vue";
import feedback from "@/views/admin/feedback/index.vue";
import {API_FEEDBACK_POST} from "@/apis/feedback";



export default {
  components: {
    GoodCard,
    GoodEdit,
    GoodInfo
  },
  computed: {
    feedback() {
      return feedback
    },
    ...mapGetters([
        "userInfo"
    ]),
    goodInfo() {
      return goodInfo
    },
    maxPage() {
      return Math.ceil(this.goodList.total/10)
    }
  },
  data() {
    return {
      colors: [
        'indigo',
        'warning',
        'pink darken-2',
        'red lighten-1',
        'deep-purple accent-4',
      ],
      hotList: [
        'First',
        'Second',
        'Third',
        'Fourth',
        'Fifth',
      ],
      editGood: null,
      editGoodDialog: false,
      categoryList: [],
      loading: false,
      goodList: {
        data: [],
        total: 0,
      },
      page: 1,
      selectedCategory: 0,
      goodInfoDialog: {
        open: false,
        id: null
      },
      feedbackDialog:{
        open: false,
        data: ""
      },
      showBar: true
    }
  },
  mounted() {
    window.addEventListener('scroll', this.onScroll);
    this.getCategoryList();
    this.getHotList()

  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.onScroll)
  },
  methods: {
    onScroll () {
      if(window.scrollY>200 && this.showBar) {
        console.log("tawdad")
        this.showBar = false;
      }else if(window.scrollY<10 && !this.showBar)this.showBar = true;
    },
    getHotList() {
      API_GET_GOOD_HOTS().then(({data})=>{
        if(!data.state) {
          return;
        }
        this.hotList = data.data.data;
      })
    },
    saveFeedback() {
      API_FEEDBACK_POST(this.feedbackDialog.data).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state? "success": "error")
        if (!data.state) {
          return;
        }
        this.feedbackDialog.open = false;
        this.feedbackDialog.data = "";
      })
    },
    openInfo(item) {
      this.goodInfoDialog = {
        open: true,
        id: item.id
      }
    },
    saveGood(data) {
      if (!data.state){
        return false;
      }
      this.getList();
      return true;
    },
    getList() {
      API_GOOD_GET_LIST(this.page, 10, this.selectedCategory).then(({data}) => {
        if (!data.state)
        this.$vuetifyFunc.snackbar.error(data.msg, 3000);
        this.goodList = {
          data: [],
          total: 0
        }
        if (!data.state) return;
        this.goodList = {
          data: data.data.data,
          total: data.data.count,
        }
      }).finally(()=>{
        this.loading = false;
      })
    },
    getCategoryList() {
      API_CATEGORY_GET_LIST().then(({data})=>{
        if (data.state) this.categoryList = data.data.data;
      })
    }
  }
}
</script>