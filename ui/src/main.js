import router from "@/router";
import { vuetifyFunctions, vuetifyInstall } from "@/vuetify";
import Vue from 'vue';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import store from './store';

Vue.prototype.$vuetifyFunc= vuetifyFunctions;
Vue.prototype.$isMobile = () => {
  console.log(navigator)
  let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i);
  return flag?true:false;
}
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
