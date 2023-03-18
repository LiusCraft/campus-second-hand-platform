<template>
<v-container>
  <v-list v-if="feedbackList.data.length">
    <template v-for="feedbackItem in feedbackList.data">
      <v-list-item two-line :key="'item-'+feedbackItem.id">
        <v-list-item-content>
          <v-list-item-title>反馈人: {{feedbackItem.userVo.nickname}}</v-list-item-title>
          <v-list-item-subtitle>内容: {{feedbackItem.context}}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <v-divider :key="'divider-'+feedbackItem.id"/>
    </template>
    <v-pagination
    v-model="feedbackList.page"
    :length="feedbackMaxPage"
    @change="getList"
    :total-visible="10"/>
  </v-list>
  <v-sheet v-else class="pa-10 text-center" >
    未查询到反馈信息
  </v-sheet>
</v-container>
</template>
<script>
import {API_FEEDBACKS_GET} from "@/apis/adminFeedBack";

export default {
  computed: {
    feedbackMaxPage() {
      return Math.ceil(this.feedbackList.total/this.feedbackList.limit)
    }
  },
  data() {
    return{
      feedbackList: {
        data: [],
        total: 0,
        page: 1,
        limit: 10
      }
    }
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList(){
      API_FEEDBACKS_GET(this.feedbackList.page, this.feedbackList.limit).then(({data})=>{
        if (!data.state) {
          this.feedbackList.page = 1;
          this.feedbackList.total = 0;
          this.feedbackList.data = [];
          return;
        }
        this.feedbackList.data = data.data.data;
        this.feedbackList.total = data.data.count;
      })
    }
  }
}
</script>