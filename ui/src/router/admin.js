import Home from "@/views/admin/home/index.vue";
import Users from "@/views/admin/users/index.vue";
import Goods from "@/views/admin/goods/index.vue";
import Orders from "@/views/admin/orders/index.vue";
import FeedBack from "@/views/admin/feedback/index.vue";
import Categorys from "@/views/admin/categorys/index.vue";
export default [
    { // 管理员首页
        path: "home",
        name: "AdminHome",
        meta: {
            title: "统计"
        },
        component: Home
    },
    { // 用户管理
        path: "users",
        name: "AdminUsers",
        component: Users,
        meta: {
            title: "用户管理"
        }
    },
    { // 商品管理
        path: "goods",
        name: "AdminGoods",
        component: Goods,
        meta: {
            title: "商品管理"
        }
    },
    {
        path: "orders",
        name: "AdminOrders",
        component: Orders,
        meta: {
            title: "订单管理"
        }
    },
    {
        path: "feedback",
        name: "AdminFeedBack",
        component: FeedBack,
        meta: {
            title: "用户反馈"
        }
    },
    {
        path: "category",
        name: "AdminCategory",
        component: Categorys,
        meta: {
            title: "分类管理"
        }
    }
]