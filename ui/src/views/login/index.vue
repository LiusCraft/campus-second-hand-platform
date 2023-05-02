<template>
  <v-container
      class="fill-height"
      fluid
      style="background-image: url('https://picsum.photos/1600/900?random=1')"
  >
    <v-row
        align="center"
        justify="center"
    >
      <v-col
          cols="12"
          sm="8"
          md="6"
          lg="4"
      >
        <v-card class="elevation-12" v-if="isLogin">
          <v-toolbar
              color="primary"
              dark
              flat
          >
            <v-toolbar-title>登录平台</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-btn
                    icon
                    large
                    v-on="on"
                    to="/"
                >
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </template>
              <span>取消登录</span>
            </v-tooltip>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field
                  label="邮箱"
                  name="email"
                  v-model="loginData.email"
                  prepend-icon="mdi-account"
                  type="text"
              ></v-text-field>

              <v-text-field
                  id="password"
                  v-model="loginData.password"
                  label="密码"
                  name="password"
                  prepend-icon="mdi-lock"
                  type="password"
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn plain color="error" @click="isLogin=false">注册账号</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="login(loginData)">登录</v-btn>
          </v-card-actions>
        </v-card>
        <v-card class="elevation-12" v-else>
          <v-toolbar
              color="primary"
              dark
              flat
          >
            <v-toolbar-title>注册平台</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-tooltip bottom>
              <template v-slot:activator="{ on }">
                <v-btn
                    icon
                    large
                    v-on="on"
                    to="/"
                >
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </template>
              <span>取消注册</span>
            </v-tooltip>
          </v-toolbar>
          <v-card-text>
              <v-text-field
                  label="邮箱"
                  name="email"
                  v-model="loginData.email"
                  prepend-icon="mdi-email"
                  type="text"
                  hint="用于登录账号"
                  persistent-hint
                  :readonly="verifyCodeExpire>0"
              >
                <template v-slot:append-outer>
                  <v-btn :disabled="verifyCodeExpire>0" text outlined small @click="getEmailCode()">{{getCodeBtnTitle}}</v-btn>
                </template>
              </v-text-field>

              <v-text-field
                  label="邮箱验证码"
                  name="email"
                  v-model="loginData.code"
                  prepend-icon="mdi-web-check"
                  type="text"
                  hint="用于验证邮箱真实性"
                  persistent-hint
              ></v-text-field>

              <v-text-field
              label="昵称"
              name="nickname"
              v-model="loginData.nickname"
              prepend-icon="mdi-account"
              hint="网上称呼"
              persistent-hint
              />
              <v-text-field
                  id="password"
                  v-model="loginData.password"
                  label="密码"
                  name="password"
                  prepend-icon="mdi-lock"
                  type="password"
                  hint="请为自己账号创建大于6位数账号"
                  persistent-hint
              ></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn plain color="error" @click="isLogin=true">登录账号</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="register()">注册账号</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import {API_GET_USER_VERIFY, API_USER_REGISTER} from "@/apis/user";
import {mapActions} from "vuex";
var verifyExpireTimer = null;
export default {
  data() {
    return {
      isLogin: true,
      loginData: {
        email: "",
        password: "",
        nickname: "",
        code: ""
      },
      verifyCodeExpire: 0
    }
  },
  computed: {
    getCodeBtnTitle() {
      return this.verifyCodeExpire>0?`${this.verifyCodeExpire}秒后重试`:"获取验证码"
    }
  },
  methods: {
    ...mapActions({
      actionLogin: "login"
    }),
    getEmailCode() {
      API_GET_USER_VERIFY(this.loginData.email).then(({data})=>{
        this.$vuetifyFunc.snackbar.show(data.msg, data.state?"success":"error");
        if(data.code===404) {
          return;
        }
        this.verifyCodeExpire = Math.floor((data.data.expire - data.data.current)/1000)
        if (data.state && verifyExpireTimer) {
          clearInterval(verifyExpireTimer);
          verifyExpireTimer = null;
        }
        if(verifyExpireTimer == null) {
          verifyExpireTimer = setInterval(()=>{
            if (this.verifyCodeExpire>0)
              this.verifyCodeExpire--;
            else
              clearInterval(verifyExpireTimer);
          }, 1000);
        }
      })
    },
    login(loginData){
      this.actionLogin(loginData).then(ok=>{
        if (ok){
          this.$router.push({name: "Home"})
        }
      })
    },
    register() {
      API_USER_REGISTER(this.loginData.email, this.loginData.password, this.loginData.nickname, this.loginData.code).then(({data}) => {
        if (!data.state) {
          this.$vuetifyFunc.snackbar.error(data.msg)
          return;
        }
        this.login(this.loginData);
      })
    }
  }
}
</script>