import type { RouteRecordRaw } from 'vue-router';
import MainLayout from 'layouts/NavigationLayout.vue';
import ArticleDetailPage from 'pages/article_management/ArticleDetailPage.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('layouts/LoginLayout.vue'),
    name: 'login',
    meta: { requireAuthenticated: false }
  },

  {
    path: '/mainlayout',
    name: 'mainlayout',
    component: MainLayout,
    redirect: '/home',
    children: [
      {
        path: '/home',
        component: () => import('pages/navigation/NavigationPage.vue')
      }
    ]
  },

  {
    path: '/admin',
    component: () => import('layouts/AdminCenter.vue'),
    meta: { requireAuthenticated: true },
    children: [
      {
        path: '/accountmanagement',
        component: () => import('pages/account_management/AccountManagement.vue')
      }

    ]
  },

  {
    path: '/discuss',
    name: 'content',
    component: () => import('layouts/ZeroStart.vue'),
    meta: { requireAuthenticated: false },
    redirect: '/discuss/home',
    children: [
      {
        path: 'home',
        meta: { requireAuthenticated: false },
        component: () => import('pages/ZeroHome.vue')
      },
      {
        path: 'article/:article_id',
        name: 'ArticleDetail',
        component: ArticleDetailPage
      },
      {
        path: 'code',
        name: 'CodeRunner',
        component: () => import('pages/CodeRunner.vue')
      },
      {
        path: 'article',
        name: 'ArticleManagement',
        meta: { requireAuthenticated: false },
        component: () =>
          import('pages/article_management/ArticleManagement.vue')
      },
      {
        path: 'user',
        component: () => import('pages/user_setting/UserManagement.vue')
      },

    ]
  },
  {
    path: '/shop',
    name: 'shop',
    component: () => import('layouts/ShopLayout.vue'),
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('pages/ShopBuy.vue')
      },
      {
        path: 'mine',
        name: 'mine',
        component: () => import('pages/user_setting/UserManagement.vue')
      }
    ]
  },
  {
    path: '/building',
    name: 'building',
    component: () => import('layouts/BuildingLayout.vue'),

    children: [
      {
        path: '/examplepage',
        component: () => import('pages/BuildingExample.vue')
      }
    ]
  },


  {
    path: '/Oauth2WaitingPage',
    component: () => import('pages/sundry/Oauth2WaitingPage.vue')
  },

  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/error/ErrorNotFound.vue')
  }
];

export default routes;
