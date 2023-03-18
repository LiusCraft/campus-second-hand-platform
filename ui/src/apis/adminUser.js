import request from "@/utils/request";
let rootApi = "/admin/users";

export function API_USERS_GET(page=1,limit=10, role=2){
    return request({
        method: "get",
        url: rootApi,
        params: {
            page,
            limit,
            role
        }
    })
}

export function API_USERS_PUT(userData) {
    return request({
        method: "put",
        url: rootApi+`/${userData.id}`,
        data: userData
    })
}