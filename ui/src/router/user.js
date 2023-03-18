import Home from "@/views/user/home/index.vue";
import Manager from "@/views/user/manager/index.vue";
import Order from "@/views/user/order/index.vue";

export default [
    {
        name: "Home",
        path: "",
        component: Home,
        meta: {
            title: "商品列表"
        }
    },
    {
        name: "Manager",
        path: "manager",
        component: Manager,
        meta: {
            login: true,
            title: "商品管理"
        }
    },
    {
        name: "Order",
        path: "order",
        component: Order,
        meta: {
            login: true,
            title: "订单管理"
        }
    }
]