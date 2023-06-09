import request from "@/utils/request";
let rootApi = '/user'

export function API_USER_LOGIN(email, password) {
    return request({
        method: "post",
        url: rootApi + "/login",
        data: {
            email: email,
            password: password
        }
    })
}

export function API_USER_REGISTER(email, password, nickname, code) {
    return request({
        method: "post",
        url: rootApi + "/register",
        data: {
            email: email,
            password: password,
            nickname: nickname,
            code: code
        }
    })
}

export function API_USER_INFO() {
    return request({
        method: "get",
        url: rootApi
    })
}

export function API_GET_USER_VERIFY(email) {
    return request({
        method: "post",
        url: rootApi + "/getCode",
        params: {
            email: email
        }
    })
}