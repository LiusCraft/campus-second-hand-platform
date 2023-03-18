import Snackbar from "@/vuetify/snackbar";
export function vuetifyInstall(vue) {
    Snackbar.install(vue);
}
export const vuetifyFunctions = {
    snackbar: Snackbar.functions
}
