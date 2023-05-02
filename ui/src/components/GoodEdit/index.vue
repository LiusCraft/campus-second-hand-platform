<template>
  <v-dialog :fullscreen="fullscreen" :value="dialog" @input="closeDialog" max-width="500">
    <v-card max-width="500">
      <v-toolbar>
        <v-toolbar-title>{{editName}}</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon color="error" @click="closeDialog()">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-toolbar>
      <template v-if="loading || !editData">
        <v-card-text class="text-center mt-2">
          <v-skeleton-loader type="article"></v-skeleton-loader>
          正在加载, 喝杯coffee等待一下吧~
        </v-card-text>
      </template>
      <template v-else>
        <v-card-text class="mt-4">
          <v-select v-model="editData.categoryId" label="商品分类" placeholder="请选择商品分类" persistent-placeholder outlined flat :items="categoryList" item-text="name" item-value="id" hide-details no-data-text="无更多分类">
            <template v-slot:item="{item}">
              <v-sheet class="pa-1">
                {{item.name}}<br/>
                <span class="grey--text text-subtitle-3">{{item.description}}</span>
              </v-sheet>
            </template>
            <template v-slot:selection="{item}">
              <v-sheet class="" >
                {{item.name}}<br/>
                <span class="grey--text text-subtitle-3">{{item.description}}</span>
              </v-sheet>
            </template>
          </v-select>
          <v-file-input label="封面图" v-model="editData.img"/>
          <v-text-field label="物品名称" v-model="editData.name" hide-details/>
          <v-text-field label="物品数量" type="number" v-model="editData.count" hide-details/>
          <v-textarea label="物品描述" placeholder="请描述您的物品!" v-model="editData.description" rows="3" hide-details/>
        </v-card-text>
        <v-card-actions class="elevation-2">
          <template v-if="admin">
            <v-menu offset-y v-if="!editData.hot">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="success" v-bind="attrs"
                       v-on="on" :disabled="!editData.id">设置推荐</v-btn>
              </template>
              <v-list>
                <v-list-item
                    v-for="(item, index) in hotTimeList"
                    :key="index"
                    @click="setGoodHots(item.time)"
                >
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
            <v-btn @click="setGoodHots(null)" color="seconds" v-else :disabled="!editData.id">取消推荐</v-btn>
          </template>
          <v-subheader v-else>请遵守校规,禁止上架违禁品</v-subheader>
          <v-spacer/>
          <v-btn color="error" @click="deleteGood(editData.id)" :disabled="!editData.id">删除</v-btn>
          <v-btn color="primary" @click="saveGood()">{{saveBtnName}}</v-btn>
        </v-card-actions>
      </template>
    </v-card>
  </v-dialog>
</template>

<script>
import {API_CATEGORY_GET_LIST} from "@/apis/category";
import {API_GOOD_DELETE, API_GOOD_GET_INFO, API_GOOD_SAVE} from "@/apis/good";
import {API_ADMIN_GOOD_SAVE, API_ADMIN_GOOD_DELETE, API_PUT_GOOD_HOTS} from "@/apis/adminGood";

let defaultValue = {
  name: "",
  categoryId: null,
  description: "",
  count: 1,
}
export default {
  props: {
    value: {
      type: Object,
      default: null
    },
    dialog: {
      type: Boolean,
      default: false
    },
    saveCallBack: {
      type: Function,
      default: ()=>{}
    },
    admin:{
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      loading: false,
      fullscreen: false,
      editData: Object.assign({},defaultValue),
      categoryList: [],
      hotTimeList: [
        {
          title: "一天",
          time: 24 * 60 * 60 * 1000
        },
        {
          title: "两天",
          time: 2 * 24 * 60 * 60 * 1000
        },
        {
          title: "三天",
          time: 3 * 24 * 60 * 60 * 1000
        },
        {
          title: "一周",
          time: 7 * 24 * 60 * 60 * 1000
        },
        {
          title: "一个月",
          time: 30 * 7 * 60 * 60 * 1000
        }
      ]
    }
  },
  computed: {
    editName() {
      return this.value?"编辑商品":"新增商品";
    },
    saveBtnName() {
      return this.value?"保存更改":"上架商品";
    }
  },
  mounted() {
    this.getCategoryList();
  },
  methods: {
    setGoodHots(hotTime) {
      API_PUT_GOOD_HOTS(this.editData.id, hotTime).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if (data.state) {
          this.closeDialog();
        }
      })
    },
    deleteGood(id) {
      let apiFun = API_GOOD_DELETE
      if(this.admin)
        apiFun = API_ADMIN_GOOD_DELETE
      apiFun(id).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if (data.state) {
          this.saveCallBack(data);
          this.closeDialog()
        }
      })
    },
    closeDialog(e) {
      if (e === this.dialog) return;
      this.$emit("update:dialog", false);
    },
    getCategoryList() {
      API_CATEGORY_GET_LIST().then(({data})=>{
        if (!data.state) return;
        this.categoryList = data.data.data;
      })
    },
    saveGood() {
      let apiFun = API_GOOD_SAVE;
      if (this.admin) apiFun = API_ADMIN_GOOD_SAVE
      apiFun(this.editData).then(({data})=>{
        this.saveCallBack(data);
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if(!data.state) {
          return;
        }
        if (!this.value) {
          this.editData = Object.assign({}, defaultValue)
        }
        this.closeDialog();
      })
    },
    getGoodInfo() {
      if (this.value == null) return;
      this.loading = true;
      API_GOOD_GET_INFO(this.value.id).then(({data})=>{
        if (!data.state){
          this.editData = null;
          return;
        }
        let newV = data.data.data;
        this.editData = {
          id: newV.id,
          name: newV.name,
          categoryId: newV.category.id,
          description: newV.description,
          count: newV.count,
          hot: newV.hot
        }
      }).finally(()=>{
        this.loading = false;
      })
    }
  },
  watch: {
    dialog(newV) {
      if(newV) {
        this.fullscreen = window.innerWidth<510
      }
    },
    value(newV) {
      this.fullscreen = window.innerWidth<510
      if (newV == null) {
        this.editData = Object.assign({}, defaultValue)
      }else {
        this.getGoodInfo();
      }
    }
  }

}
</script>