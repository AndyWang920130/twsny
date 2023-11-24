<template>
  <a-timeline mode="alternate">
    <a-timeline-item v-for="(item, index) in recordItems" :color="item.color">{{item.label}}</a-timeline-item>
  </a-timeline>
</template>
<script lang="ts" setup>
import { ref } from 'vue';

import { ClockCircleOutlined } from '@ant-design/icons-vue';

const recordItems = ref([])
recordItems.value.push(
    {
      'color': 'red',
      'label': "创建角色 2023-05-01"
    },
    {
      'color': 'green',
      'label': "kill first enemy 2023-05-03"
    })
</script>

