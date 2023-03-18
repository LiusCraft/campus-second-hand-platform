<template>
  <v-container>
    <v-toolbar dense flat>
      <v-toolbar-title>分类管理</v-toolbar-title>
      <v-spacer/>
      <v-btn elevation="0" color="primary" @click="newCategory()">新增分类</v-btn>
    </v-toolbar>
    <v-divider/>
    <v-sheet class="pa-2">
      <template v-if="categoryList.data.length">
        <v-list>
          <template v-for="(categoryItem,categoryIndex) in categoryList.data">
            <v-list-item :key="categoryIndex">
              <v-list-item-content>
                <v-list-item-title>{{categoryItem.name}}</v-list-item-title>
                <v-list-item-subtitle>
                  {{categoryItem.description}}
                </v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-action>
                <v-btn elevation="0" color="primary" @click="categoryEditDialog.data=Object.assign({},categoryItem)">编辑</v-btn>
              </v-list-item-action>
            </v-list-item>
            <v-divider :key="'divider-'+categoryIndex"/>
          </template>
        </v-list>
        <div class="text-center mt-5">
          <v-pagination
              v-model="categoryList.page"
              :length="categoryMaxPage"
              @input="getList"
              total-visible="10"
          ></v-pagination>
        </div>
      </template>
      <v-sheet v-else class="pa-10 text-center" >
        未查询到分类
      </v-sheet>
    </v-sheet>
    <v-dialog :value="categoryEditDialog.data" max-width="500" @change="(e)=> {if(!e)this.categoryEditDialog.data=null}">
      <v-card v-if="categoryEditDialog.data">
        <v-toolbar>
          <v-toolbar-title v-if="categoryEditDialog.data.id">分类编辑</v-toolbar-title>
          <v-toolbar-title v-else>新增分类</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon color="red" @click="categoryEditDialog.data=null">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <v-card-text class="mt-5">
          <v-text-field outlined label="名称" v-model="categoryEditDialog.data.name"/>
          <v-text-field outlined label="描述" v-model="categoryEditDialog.data.description"/>
          <v-row>
            <v-col>
              <v-btn :disabled="!categoryEditDialog.data.id" elevation="0" color="error" block @click="deleteCategory(categoryEditDialog.data.id)">删除</v-btn>
            </v-col>
            <v-col>
              <v-btn color="primary" block elevation="0" @click="saveCategory()">保存修改</v-btn>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>
import {API_CATEGORY_GET_LIST} from "@/apis/category";
import {API_CATEGORY_DELETE, API_CATEGORY_SAVE} from "@/apis/adminCategory";
export default {
  computed: {
    categoryMaxPage() {
      return Math.ceil(this.categoryList.total/this.categoryList.limit);
    }
  },
  data() {
    return{
      categoryEditDialog: {
        data: null
      },
      categoryList: {
        data: [],
        page: 1,
        limit: 10,
        total: 0,
      }
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    deleteCategory(id) {
      API_CATEGORY_DELETE(id).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if(data.state) {
          this.getList();
          this.categoryEditDialog.data = null;
        }
      })
    },
    saveCategory() {
      API_CATEGORY_SAVE(this.categoryEditDialog.data).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if (!data.state){
          return;
        }
        this.categoryEditDialog.data = null;
        this.getList();
      })
    },
    newCategory() {
      this.categoryEditDialog.data = {
        name: "",
        description: ""
      }
    },
    getList() {
      API_CATEGORY_GET_LIST(this.categoryList.page, this.categoryList.limit).then(({data})=>{
        if (!data.state) {
          this.categoryList = {
            data: [],
            page: 1,
            limit: 10,
            total: 0
          }
          return;
        }
        this.categoryList.data = data.data.data;
        this.categoryList.total = data.data.count;
      })
    }
  }
}
</script>