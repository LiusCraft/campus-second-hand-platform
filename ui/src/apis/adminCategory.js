import request from "@/utils/request";
let rootApi = "/admin/category"
export function API_CATEGORY_SAVE(data) {
    if (data.id)
        return API_CATEGORY_PUT(data);
    return request({
        method: "post",
        url: rootApi,
        data: data
    })
}

export function API_CATEGORY_PUT(data) {
    return request({
        method: "put",
        url: rootApi+"/"+data.id,
        data: data
    })
}

export function API_CATEGORY_DELETE(id){
    return request({
        method: "delete",
        url: rootApi+"/"+id
    })
}