import request from "@/utils/request";
let rootApi = "/admin/feedbacks";
export function API_FEEDBACKS_GET(page, limit) {
    return request({
        method: "get",
        url: rootApi,
        params: {
            page,
            limit
        }
    })
}