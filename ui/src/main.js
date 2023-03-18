import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from "@/router";
import store from './store'
import {vuetifyInstall, vuetifyFunctions} from "@/vuetify";

Vue.prototype.$vuetifyFunc= vuetifyFunctions;
Vue.config.productionTip = false
new Vue({
  vuetify,
  router,
  store,
  render: h => {
    let dom = h(App)
    vuetifyInstall(dom);
    return dom;
  },
}).$mount('#app');
