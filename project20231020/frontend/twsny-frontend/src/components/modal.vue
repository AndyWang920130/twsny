<template>
  <div>
<!--    <a-button type="primary" @click="showModal">Open Modal</a-button>-->
    <a-modal v-model:open="open" title="Basic Modal" @ok="handleOk">
      <p>Some contents...</p>
      <p>Some contents...</p>
      <p>Some contents...</p>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
const emit = defineEmits(['confirm', 'cancel'])
const confirm = () => {
  emit('confirm')
  alert('confirm')
}

const cancel = () => {
  emit('cancel')
  alert('cancel')
}


import { ref } from 'vue';
const open = ref<boolean>(false);

const showModal = () => {
  open.value = true;
};

const handleOk = (e: MouseEvent) => {
  console.log(e);
  open.value = false;
};
</script>

