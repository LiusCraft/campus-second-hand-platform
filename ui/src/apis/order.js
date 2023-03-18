import request from "@/utils/request";
let rootApi = "/orders"
export function API_ORDERS_GET_LIST(type, page=1,limit=10, orderStatus) {
    return request({
        method: "get",
        url: rootApi+(!type?"/buys":"/sells"),
        params: {
            page,
            limit,
            orderStatus
        }
    })
}

export function API_ORDERS_PUT_STATUS(orderId, orderStatus){
    return request({
        method: "put",
        url: rootApi + "/" + orderId,
        params: {
            orderStatus
        }
    })
}