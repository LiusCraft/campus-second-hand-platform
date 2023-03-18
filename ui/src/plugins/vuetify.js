import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes:{
            light: {
                primary: '#4ea5ff',
                secondary: '#424242',
                accent: '#82B1FF',
                error: '#FF5252',
                info: '#4ea5ff',
                success: '#4CAF50',
                warning: '#ffbe10',
            }
        }
    }
});
