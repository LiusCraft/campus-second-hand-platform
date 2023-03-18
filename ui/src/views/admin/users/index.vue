<template>
  <v-container>
    <v-chip-group mandatory color="primary" v-model="selectRoleId" @change="getList">
      <v-chip v-for="roleItem in userRoles" :value="roleItem.value" :key="'role-'+roleItem.value">{{roleItem.text}}</v-chip>
    </v-chip-group>
    <v-divider/>
    <template v-if="userList.data.length">
      <v-list>
        <template v-for="(userItem,userIndex) in userList.data">
          <v-list-item :key="userIndex">
            <v-list-item-content>
              <v-list-item-title>{{userItem.nickname}}</v-list-item-title>
              <v-list-item-subtitle>
                <v-chip small label class="mr-1">邮箱: {{userItem.email}}</v-chip>
                <v-chip small label>加入时间: {{userItem.gmtCreate}}</v-chip>
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-list-item-action>
              <v-btn elevation="0" color="primary" @click="userEditDialog.data=Object.assign({},userItem)">编辑</v-btn>
            </v-list-item-action>
          </v-list-item>
          <v-divider :key="'divider-'+userIndex"/>
        </template>
      </v-list>
      <div class="text-center mt-5">
        <v-pagination
            v-model="userList.page"
            :length="userMaxPage"
            @input="getList"
            total-visible="10"
        ></v-pagination>
      </div>
    </template>
    <v-sheet v-else class="pa-10 text-center" >
      未查询到相关用户
    </v-sheet>

    <v-dialog :value="userEditDialog.data" max-width="500" @change="(e)=> {if(!e)this.userEditDialog.data=null}">
      <v-card v-if="userEditDialog.data">
        <v-toolbar>
          <v-toolbar-title>用户编辑</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon color="red" @click="userEditDialog.data=null">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <v-card-text class="mt-5">
          <v-text-field outlined label="昵称" v-model="userEditDialog.data.nickname"/>
          <v-text-field outlined label="邮箱" v-model="userEditDialog.data.email"/>
          <v-select :items="userRoles" v-model="userEditDialog.data.roleId"/>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" block elevation="0" @click="saveUser()">保存修改</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<script>
import {API_USERS_GET, API_USERS_PUT} from "@/apis/adminUser";

export default {
  computed: {
    userMaxPage() {
      return Math.ceil(this.userList.total/this.userList.limit);
    }
  },
  data() {
    return{
      userEditDialog: {
        data: null,
      },
      userRoles: [
        {
          text: "普通用户",
          value: 2
        },
        {
          text: "管理员",
          value: 1
        }
      ],
      selectRoleId: 2,
      userList: {
        data: [],
        total: 0,
        page: 1,
        limit: 10
      }
    }
  },
  mounted() {
    this.getList();
  },
  methods: {
    saveUser() {
      API_USERS_PUT(this.userEditDialog.data).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if (!data.state)return;
        this.getList();
        this.userEditDialog.data = null;
      });
    },
    getList() {
      API_USERS_GET(this.userList.page, this.userList.limit, this.selectRoleId).then(({data})=>{
        if (!data.state) {
          this.userList.data = [];
          this.userList.total = 0;
          this.userList.page = 1;
          return;
        }
        this.userList.data = data.data.data;
        this.userList.total = data.data.count;
      })
    }
  }
}
</script>