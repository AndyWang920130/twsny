<script setup lang="ts">
import { ref } from 'vue'
import { Form, FormItem, Input, message, Modal, Textarea } from 'ant-design-vue'
import { createItem } from '@/apis/item/itemApi';

const props = defineProps<{ visible: boolean }>()
const emit = defineEmits(['update:visible', 'success'])

const form = ref({ name: '', description: '' })

const onSubmit = async () => {
  await createItem({
    name: form.value.name,
    description: form.value.description,
    createdAt: new Date().toISOString().split('T')[0]
  })
  message.success('新增成功')
  emit('success')
  emit('update:visible', false)
}
</script>

<template>
  <Modal :visible="visible" title="新增" @ok="onSubmit" @cancel="$emit('update:visible', false)">
    <Form layout="vertical">
      <FormItem label="名称" required>
        <Input v-model:value="form.name" />
      </FormItem>
      <FormItem label="描述">
        <Textarea v-model:value="form.description" />
      </FormItem>
    </Form>
  </Modal>
</template>
