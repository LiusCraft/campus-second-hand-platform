import request from "@/utils/request";
let rootApi = "/feedback";

export function API_FEEDBACK_POST(context) {
    return request({
        method: "post",
        url: rootApi,
        data: {
            context
        }
    })
}