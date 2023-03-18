import Axios from 'axios'
Axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

const instance = Axios.create({
    baseURL: "/api",
    timeout: 3000,
});

instance.interceptors.request.use(config=>{
    let token = localStorage.getItem("token");
    if (token) config.headers["token"] = token;
    if (config.params) {
        let urlSearchParams = new URLSearchParams(config.params);
        config.url +="?"+urlSearchParams.toString();
    }
    if (config.method === 'post' || config.method === 'put')
    {
        config.data = typeof config.data === 'object'? JSON.stringify(config.data):config.data;
    }
    return config;
});

export default instance;