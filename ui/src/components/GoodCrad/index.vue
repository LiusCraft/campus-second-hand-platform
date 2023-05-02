<template>
  <v-card
    class="mx-auto"
    max-width="350"
    v-if="goodItem"
    @click="call(goodItem, index)"
    outlined
  >
    <v-img height="150px" :src="`/api/goods/img/${goodItem.id}`">
      <v-overlay absolute class="white--text align-end">
        <v-card-title class="pa-2">{{ goodItem.name }}</v-card-title>
      </v-overlay>
    </v-img>

    <v-card-subtitle class="pb-0">{{ goodItem.gmtCreate }}</v-card-subtitle>

    <v-card-text class="pb-2">
      <v-chip-group>
        <v-chip small label color="orange" dark>{{goodItem.user.nickname}}</v-chip>
        <v-chip small label>数量: {{goodItem.count}}</v-chip>
        <v-chip small label v-if="goodItem.hot" color="error">平台推荐</v-chip>
      </v-chip-group>
      <!--            <div>商家 <span>{{ goodItem.user.nickname }}</span></div>-->

      <!--            <div>数量 <span>{{ goodItem.count }}</span></div>-->
    </v-card-text>
  </v-card>
</template>
<script>
export default {
  name: "GoodCard",
  props: {
    index: {
      type: Number,
      default: null
    },
    goodItem: {
      type: Object,
      default: null
    },
    call: {
      type: Function,
      default: () => {}
    }
  }
};
</script>