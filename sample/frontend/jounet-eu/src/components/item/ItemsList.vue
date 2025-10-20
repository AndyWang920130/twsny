<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Button, message, Modal, Table, TableColumn } from 'ant-design-vue'
import ItemFormModal from './ItemFormModal.vue'
import { useRouter } from 'vue-router'
import { deleteItem, fetchItems } from '@/apis/item/itemApi'
import type { Item } from '@/types/item.D'

const items = ref<Item[]>([
  { id: 1, name: 'Item A', description: 'First Item', createdAt: '2025-10-10' },
  { id: 2, name: 'Item B', description: 'Second Item', createdAt: '2025-10-12' }
])
const loading = ref(false)
const router = useRouter()
const showForm = ref(false)

const loadItems = async () => {
  loading.value = true
  try {
    items.value = await fetchItems()
  } catch (error) {
    console.error(error)
    message.error('加载失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// const loadItems = () => {
//   loading.value = true
//   fetchItems().then(res => {
//     items.value = res
//     loading.value = false
//   }).catch(err => {
//     loading.value = false
//   })
// }

const onDelete = (id: number) => {
  Modal.confirm({
    title: '确认删除？',
    onOk: async () => {
      await deleteItem(id)
      message.success('删除成功')
      loadItems()
    }
  })
}

const goDetail = (id: number) => {
  router.push(`/item/${id}`)
}

const openAddModal = () => {
  showForm.value = true
}

onMounted(() => loadItems())
</script>

<template>
  <div>
    <Button type="primary" class="mb-4" @click="openAddModal">新增</Button>
    <Table :dataSource="items" :loading="loading" rowKey="id">
      <TableColumn title="名称" dataIndex="name" />
      <TableColumn title="描述" dataIndex="description" />
      <TableColumn title="创建时间" dataIndex="createdAt" />
      <TableColumn title="操作" v-slot="{ record }">
        <Button type="link" @click="goDetail(record.id)">详情</Button>
        <Button type="link" danger @click="onDelete(record.id)">删除</Button>
      </TableColumn>
    </Table>
    <ItemFormModal v-model:visible="showForm" @success="loadItems" />
  </div>
</template>
