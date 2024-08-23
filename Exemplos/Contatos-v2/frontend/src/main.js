import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';
import { createRouter, createWebHistory } from 'vue-router';
import 'bootstrap/dist/css/bootstrap.css';
import VueTheMask from 'vue-the-mask'

import ContatoList from './components/contato/ContatoList';
import ContatoCreate from './components/contato/ContatoCreate';
import ContatoEdit from './components/contato/ContatoEdit';
import ContatoShow from './components/contato/ContatoShow';

  
axios.defaults.baseURL = process.env.VUE_APP_API_URL
axios.interceptors.request.use(function (config) {
  config.headers['X-Binarybox-Api-Key'] = process.env.VUE_APP_API_KEY;
  return config;
});
  
const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: ContatoList },
    { path: '/contatos', component: ContatoList },
    { path: '/contatos/create', component: ContatoCreate },
    { path: '/contatos/edit/:id', component: ContatoEdit },
    { path: '/contatos/show/:id', component: ContatoShow },

  ],
});
  
createApp(App).use(router).use(VueTheMask).mount('#app');
