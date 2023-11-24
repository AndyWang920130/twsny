<template>
  <a-tabs v-model:activeKey="activeKey" centered>
    <a-tab-pane key="timeline" tab="时间轴"><RecordTimeLine></RecordTimeLine></a-tab-pane>
    <a-tab-pane key="introduction" tab="攻略" force-render><Introduction></Introduction></a-tab-pane>
  </a-tabs>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import RecordTimeLine from "./RecordTimeLine.vue";
import Introduction from "./Introduction.vue";
const activeKey = ref('timeline');
</script>