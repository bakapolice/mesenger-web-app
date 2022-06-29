import Vue from 'vue'
import App from './App.vue'
import {router} from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import VeeValidate from 'vee-validate';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
    faHome,
    faUser,
    faUserPlus,
    faSignInAlt,
    faSignOutAlt
  } from '@fortawesome/free-solid-svg-icons';
import setupInterceptors from './services/setupInterceptors';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);
Vue.config.productionTip = false;
Vue.use(VeeValidate);
Vue.use(ElementUI);
Vue.component('font-awesome-icon', FontAwesomeIcon);
setupInterceptors(store);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');