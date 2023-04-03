import request from '@/utils/request';
let rootApi = '/admin/goods';
export function API_GOOD_GET_LIST(page=1, limit=10, category="all") {
    return request({
        url: rootApi+ "/category/" + category,
        method: "get",
        params: {
            page: page,
            limit: limit
        }
    })
}

export function API_ADMIN_GOOD_SAVE(good=null){
    if (good == null) return new Promise(resolve => resolve({data:{state: false, msg: "发生错误！请刷新页面重试"}}));
    let methodName = "post";
    if (good.id>0) methodName = "put"
    return request({
        url: rootApi + (methodName === "put"?"/"+good.id:""),
        method: methodName,
        hasFile: true,
        data: good,

    });
}

export async function API_GOOD_GET_INFO(goodId) {
    if (!(goodId>0)) {
        return ({
            data: {
                state: false,
                msg: "查询失败， 请刷新页面重试！"
            }
        })
    }
    return await request({
        url: rootApi + "/"+goodId,
        method: "get"
    });
}

export function API_ADMIN_GOOD_DELETE(id){
    return request({
        method: "delete",
        url: rootApi+"/"+id
    })
}