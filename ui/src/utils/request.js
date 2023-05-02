import Axios from 'axios';
Axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

const instance = Axios.create({
    baseURL: "/api",
    timeout: 3000,
});

instance.interceptors.request.use(config => {
    let token = localStorage.getItem("token");
    if (token) config.headers["token"] = token;
    if ((config.method === 'post' || config.method === 'put') && !config.hasFile) {
        config.data = typeof config.data === 'object' ? JSON.stringify(config.data) : config.data;
    } else if (config.hasFile) {
        let formdata = new FormData();
        config.headers["Content-Type"] = "multipart/form-data;boundary="+new Date().getTime()
        Object.keys(config.data).forEach(key => {
            formdata.append(key, config.data[key]);
        })
        config.data = formdata;
    }
    return config;
});

export default instance;