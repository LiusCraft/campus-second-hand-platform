
var snackbar;
var Vue;
function install(vue) {
    Vue = vue;
}
const show = (msg, timeout=3000, type)=>{
    snackbar = Vue.child.$children[0].$children[0];
    if (type===1) snackbar.info(msg, timeout);
    else if(type === 2) snackbar.error(msg, timeout);
    else if(type === 3) snackbar.warning(msg, timeout);
    else if(type === 4) snackbar.success(msg, timeout);
    else snackbar.show(msg, timeout, type);
}
export default {
    functions: {
        show: (msg, color, timeout) => {show(msg, timeout, color)},
        info:(msg, timeout)=>{ show(msg, timeout,1)},
        error: (msg, timeout) => show(msg, timeout,2),
        warning: (msg, timeout) => show(msg, timeout,3),
        success: (msg, timeout) => show(msg, timeout,4)
    },
    install: install
}