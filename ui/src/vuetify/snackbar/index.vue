<template>
  <div id="snackbarList" class="component-snackbar" style="position: absolute; bottom: 0px;">
    <v-snackbar dark style=" display: flex;flex-direction: row; height: inherit"  v-for="(message,index) in messageQueue" v-model="message.open"
                :color="message.color"
                :timeout="message.timeout"
                @input="()=>finishMessage++"
                :key="'snack-'+index"
                bottom>
      {{ message.msg }}
      <template v-slot:action>
        <v-btn text @click="message.open=false">
          关闭
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  name: "Snackbar",
  props: {},
  components: {},
  data() {
    return {
      messageQueue: [],
      finishMessage: 0,
    };
  },
  created() {
  },
  methods: {
    info(mes, timeout) {
      this.messageQueue.push({
        color: "primary",
        msg: mes,
        timeout: timeout,
        open: true
      })
    },
    error(mes, timeout) {
      this.messageQueue.push({
        color: "error",
        msg: mes,
        timeout: timeout,
        open: true
      })
    },
    warning(mes, timeout) {
      this.messageQueue.push({
        color: "warning",
        msg: mes,
        timeout: timeout,
        open: true
      })
    },
    success(mes, timeout) {
      this.messageQueue.push({
        color: "success",
        msg: mes,
        timeout: timeout,
        open: true
      })
    },
    show(mes, timeout, color) {
      this.messageQueue.push({
        color: color,
        msg: mes,
        timeout: timeout,
        open: true
      })
    }
  },
  watch: {
    finishMessage(e){
      if (e>= this.messageQueue.length) {
        this.finishMessage = 0;
        this.messageQueue = [];
      }
    }
  }
};
</script>

<style lang="scss">
</style>