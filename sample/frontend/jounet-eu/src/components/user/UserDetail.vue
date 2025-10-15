<template>
  <div class="p-4">
    <h2 class="text-xl font-bold mb-4">用户详情</h2>
    <div v-if="user">
      <p><b>ID：</b>{{ user.id }}</p>
      <p><b>姓名：</b>{{ user.name }}</p>
      <p><b>邮箱：</b>{{ user.email }}</p>
      <button @click="router.back()" class="mt-3 bg-gray-300 px-3 py-1 rounded">返回</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/store/userStore';
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';

const route = useRoute();
const router = useRouter();
const store = useUserStore();

onMounted(() => store.fetchUser(Number(route.params.id)));

const user = storeToRefs(store).currentUser;
</script>
