<script setup lang="ts">
import { fetchItem } from '@/apis/item/itemApi'
import type { Item } from '@/types/item.D'
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const item = ref<Item | null>({ id: 1, name: 'Item A', description: 'First Item', createdAt: '2025-10-10' })

onMounted(() => {
  fetchItem(Number(route.params.id)).then(res => {
     item.value = res
  })
})
</script>

<template>
  <div v-if="item">
    <h2>{{ item.name }}</h2>
    <p>描述：{{ item.description }}</p>
    <p>创建时间：{{ item.createdAt }}</p>
  </div>
</template>
