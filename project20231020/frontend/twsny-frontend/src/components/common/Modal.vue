<template>
  <div>
<!--    <a-button type="primary" @click="showModal">Open Modal</a-button>-->
    <a-modal v-model:open="props.open" title="Basic Modal" @ok="handleOk" @cancel="handleCancel" :footer="null">
      <slot></slot>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import {Item} from "../../definition/FormData";
interface ModalProp {
  open: boolean;
}
const props = defineProps<ModalProp>()

const emit = defineEmits<{
  // handleOk: [e: Event, n: number]
  handleOk: [e: Event]
  handleCancel: [e: Event]
}>()
// const emit = defineEmits(['handleOk'])

const handleCancel = (e) => {
  emit('handleCancel', e)
}

const handleOk = (e) => {
  // emit('handleOk', e, 1)
  emit('handleOk', e)
}
</script>