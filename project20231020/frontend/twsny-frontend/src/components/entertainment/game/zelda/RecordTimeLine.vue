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
      'color': 'orange',
      'label': "start version 1.0 (Breath of the wilderness) 2023-05-01"
    },
    {
      'color': 'green',
      'label': "created role 2023-05-01"
    },
    {
      'color': 'green',
      'label': "kill first enemy 2023-05-03"
    },
    {
      'color': 'green',
      'label': "beat the game 2023-08-09"
    },
    {
      'color': 'orange',
      'label': "start the version 2.0 (Tears of Kingdom) 2023-08-09"
    })
</script>

