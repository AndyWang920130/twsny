<template>
  <div class="p-4">
    <h2 class="text-xl font-bold mb-4">{{ isEdit ? '编辑用户' : '新增用户' }}</h2>

    <form @submit.prevent="onSubmit">
      <div class="mb-3">
        <label>姓名：</label>
        <input v-model="form.name" class="border p-1 w-full" />
      </div>
      <div class="mb-3">
        <label>邮箱：</label>
        <input v-model="form.email" class="border p-1 w-full" />
      </div>

      <button class="bg-blue-500 text-white px-3 py-1 rounded" type="submit">
        {{ isEdit ? '保存修改' : '添加用户' }}
      </button>
      <button @click="router.back()" type="button" class="ml-2 bg-gray-300 px-3 py-1 rounded">取消</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/store/userStore';

const route = useRoute();
const router = useRouter();
const store = useUserStore();

const isEdit = ref(false);
const form = ref({ name: '', email: '' });

onMounted(async () => {
  if (route.params.id) {
    isEdit.value = true;
    await store.fetchUser(Number(route.params.id));
    form.value = { ...store.currentUser };
  }
});

const onSubmit = async () => {
  if (isEdit.value) {
    await store.editUser(Number(route.params.id), form.value);
  } else {
    await store.addUser(form.value);
  }
  router.push('/users');
};
</script>
