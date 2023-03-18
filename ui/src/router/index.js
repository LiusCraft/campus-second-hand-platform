import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/views/login/index.vue";
import UserIndex from "@/views/user/index.vue";
import AdminIndex from "@/views/admin/index.vue";
import AdminRoutes from "@/router/admin";
import UserRoutes from "@/router/user";
Vue.use(Router)


const router = new Router({
    routes: [
        {
            name: "UserIndex",
            path: "/",
            component: UserIndex,
            meta: {
                login: false,
                title: "首页"
            },
            children: UserRoutes
        },
        {
            name: "Login",
            path: "/login",
            component: Login,
            meta: {
                login: false,
                title: "登录"
            }
        },

        {
            path: "/admin",
            name: "AdminIndex",
            component: AdminIndex,
            meta: {
                login: true,
                title: "管理后台"
            },
            children: AdminRoutes
        }
    ]
});

router.beforeEach((to, from, next)=>{
    if (to.matched.some(res=>res.meta.login)){
        if(!localStorage.getItem("token")){
            next({
                path: "/login"
            })
            return;
        }
    }
    if (to.name === "AdminIndex"){
        next({
            name: "AdminHome"
        })
        return;
    }
    next();
    document.title = to.meta.title;
});

export default router;