import request from "@/utils/request";
let rootApi = 'categorys'
export function API_CATEGORY_GET_LIST(){
    return request({
        method: "get",
        url: rootApi
    });
}