import request from "@/utils/request";
let rootApi = "/admin"


export function API_ADMIN_STATISTICS() {
    return request({
        method: "get",
        url: rootApi+"/statistics"
    })
}