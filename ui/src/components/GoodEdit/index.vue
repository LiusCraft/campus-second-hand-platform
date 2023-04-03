<template>
  <v-dialog :value="dialog" @input="closeDialog" max-width="500">
    <v-card max-width="500">
      <v-toolbar>
        <v-toolbar-title>{{editName}}</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon color="error" @click="closeDialog()">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-toolbar>
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
        <v-subheader>请遵守校规,禁止上架违禁品</v-subheader>
        <v-spacer/>
        <v-btn color="error" @click="deleteGood(editData.id)" :disabled="!editData.id">删除</v-btn>
        <v-btn color="primary" @click="saveGood()">{{saveBtnName}}</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import {API_CATEGORY_GET_LIST} from "@/apis/category";
import {API_GOOD_DELETE, API_GOOD_SAVE} from "@/apis/good";
import {API_ADMIN_GOOD_SAVE, API_ADMIN_GOOD_DELETE} from "@/apis/adminGood";

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
      editData: defaultValue,
      categoryList: []
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
        this.closeDialog();
      })
    }
  },
  watch: {
    value(newV) {
      if (newV == null) {
        this.editData = Object.assign({}, defaultValue)
      }else {
        this.editData = {
          id: newV.id,
          name: newV.name,
          categoryId: newV.category.id,
          description: newV.description,
          count: newV.count
        }
      }
    }
  }

}
</script>