<template>
  <div class="p-4">
    <h2 class="text-xl font-bold mb-4">用户列表</h2>
    <button @click="goCreate" class="bg-blue-500 text-white px-3 py-1 rounded">新增用户</button>

    <table class="w-full mt-4 border">
      <thead>
        <tr class="bg-gray-100">
          <th>ID</th>
          <th>姓名</th>
          <th>邮箱</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id" class="border-t">
          <td>{{ user.id }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>
            <button @click="goDetail(user.id)" class="text-blue-600">详情</button> |
            <button @click="goEdit(user.id)" class="text-green-600">编辑</button> |
            <button @click="onDelete(user.id)" class="text-red-600">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/userStore';
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia';

const router = useRouter();
const store = useUserStore();

onMounted(() => store.fetchUsers());

const users = storeToRefs(store).users;

const goDetail = (id: number) => router.push(`/user/${id}`);
const goEdit = (id: number) => router.push(`/user/${id}/edit`);
const goCreate = () => router.push('/user/create');

const onDelete = async (id: number) => {
  if (confirm('确定删除该用户吗？')) {
    await store.removeUser(id);
  }
};
</script>
