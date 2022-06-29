import Vue from 'vue';
import Router from 'vue-router';
import Login from './views/Login.vue';
import Register from './views/Register.vue';
import FriendList from './views/FriendList.vue';
import ConversationList from './views/ConversationList.vue';

Vue.use(Router);
export const router = new Router({
    mode: 'history',
    routes: [

        {
            path: '/im/:id/:sel',
            name: 'dialog',
            component: () => import('./views/Dialog.vue')
        },
        {
            path: '/room/:id',
            name: 'chat-room',
            component: () => import('./views/ChatRoom.vue')
        },
        {
            path: '/servers',
            name: 'server-list',
            component: () => import('./views/ServerList.vue')
        },
        {
            path: '/im',
            name: 'conversations',
            component: ConversationList
        },
        {
            path: '/friends',
            name: 'friends',
            component: FriendList
        },
        {
            path: '/',
            name: 'profile',
            component: () => import('./views/Profile.vue')

        },
        {
            path: '/profile',
            component: () => import('./views/Profile.vue')
        },
        {
            path: '/server_list',
            name: "server-search",
            component: () => import('./views/ServerList.vue')
        }, 
        {
            path: '/login',
            name: 'login',
            meta: { layout: 'login'},
            component: Login
        },
        {
            path: '/register',
            name: 'register',
            meta: { layout: 'login'},
            component: Register
        }
    ]
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');
    if (authRequired && !loggedIn) {
        next('/login');
    }
    else {
        next();
    }
});