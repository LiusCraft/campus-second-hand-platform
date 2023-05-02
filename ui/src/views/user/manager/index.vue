<template>
  <v-container>
    <v-chip-group color="primary" v-model="selectedCategory" mandatory @change="getList">
      <v-btn class="mr-2" color="primary" @click="editGood(null)">发布商品</v-btn>
      <v-chip key="category-all" value="0" :disabled="loading">所有分类</v-chip>
      <v-chip v-for="(categoryItem) in categoryList" :disabled="loading" :value="categoryItem.id"
              :key="'category-'+categoryItem.id">{{ categoryItem.name }}
      </v-chip>
    </v-chip-group>

    <v-divider class="mb-2"/>
    <v-row v-if="goodList.data.length">
      <v-col v-for="(goodItem,goodIndex) in goodList.data" :key="goodItem.id">
        <good-card :index="goodIndex" :good-item="goodItem" :call="editGood"/>
      </v-col>
    </v-row>
    <v-sheet v-else class="pa-10 text-center">
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
        v-model="editGoodDialog.data"
        :dialog.sync="editGoodDialog.open"
        :save-call-back="saveGood"/>
  </v-container>
</template>
<script>
import GoodEdit from "@/components/GoodEdit/index.vue";
import {API_GOOD_GET_LIST} from "@/apis/good";
import {API_CATEGORY_GET_LIST} from "@/apis/category";
import GoodCard from "@/components/GoodCrad/index.vue";

export default {
  components: {
    GoodCard,
    GoodEdit
  },
  computed: {
    maxPage() {
      return Math.ceil(this.goodList.total / 10);
    }
  },
  methods: {
    editGood(item) {
      if (item == null) {
        this.editGoodDialog = {
          data: null,
          open: true
        }
      }else
      this.editGoodDialog = {
        data: Object.assign({}, item),
        open: true
      }
    },
    saveGood(data) {
      if (!data.state) {
        return false;
      }
      this.editGoodDialog.data = null;
      this.getList();
      return true;
    },
    getList() {
      API_GOOD_GET_LIST(this.page, 10, this.selectedCategory, true).then(({data}) => {
        if (!data.state){
          this.goodList = {
            data: [],
            total: 0
          }
          return;
        }
        this.goodList = {
          data: data.data.data,
          total: data.data.count,
        }
      }).finally(() => {
        this.loading = false;
      })
    },
    getCategoryList() {
      API_CATEGORY_GET_LIST().then(({data})=>{
        if (data.state) this.categoryList = data.data.data;
      })
    }
  },
  mounted() {
    this.getCategoryList();
  },
  data() {
    return {
      loading: false,
      page: 1,
      goodList: {
        total: 0,
        data: []
      },
      categoryList: [],
      selectedCategory: 0,
      editGoodDialog: {
        data: null,
        open: false
      }
    }
  }
}
</script>