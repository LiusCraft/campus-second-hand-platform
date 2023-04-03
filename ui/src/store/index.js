import { API_USER_INFO, API_USER_LOGIN } from "@/apis/user";
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: {
      token: localStorage.getItem("token"),
      data: null,
    }
  },
  getters:{
    userInfo({userInfo}){
      if(!userInfo || !userInfo.token) return false;
      if (!userInfo.data) {
        let localData = localStorage.getItem("userData");
        if(!localData){
          return false;
        }
        return userInfo.data = JSON.parse(localData);
      }
      return userInfo;
    }
  },
  mutations: {
    userInfo(state, data) {
      state.userInfo = Object.assign(state.userInfo, data);
      localStorage.setItem("userData", JSON.stringify(data));
    }
  },
  actions: {
    async getInfo(context) {
      return await API_USER_INFO().then(({data})=>{
        if (!data.state) {
          localStorage.removeItem("token");
          context.commit("userInfo", {data: null, token: null})
          return false;
        }
        context.commit("userInfo", {data: data.data.data})
        return true;
      })
    },
    async login(context, loginData) {
      return await API_USER_LOGIN(loginData.email, loginData.password).then(({data})=> {
        Vue.prototype.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if (!data.state) {
          context.commit("userInfo", {data: null})
          return false;
        }
        localStorage.setItem("token", data.data.token);
        context.commit("userInfo", {token: data.data.token});
        context.commit("userInfo", {data: data.data.data})
        return true;
      })
    }
  },
  modules: {
  }
})
