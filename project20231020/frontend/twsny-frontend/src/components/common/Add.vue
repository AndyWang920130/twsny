<template>
  <a-form
      ref="formRef"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
  >
    <slot v-for="item in props.items">
      <template v-if="item.type === 'input'">
        <a-form-item :ref="item.label" :label="item.label" :name="item.label">
          <a-input v-model:value="item.value" :placeholder="item.placeholder"/>
        </a-form-item>
      </template>

      <template v-if="item.type === 'option'">
        <a-form-item :label="item.label" :name="item.label" >
          <a-select v-model:value="item.value" :placeholder="item.placeholder" :options="item.options" />
        </a-form-item>
      </template>

      <template v-if="item.type === 'radio'">
        <a-form-item :label="item.label" :name="item.label">
          <a-radio-group v-model:value="item.value" :options="item.options"/>
        </a-form-item>
      </template>

      <template v-if="item.type === 'checkBox'">
        <a-form-item :label="item.label" :name="item.label">
            <a-checkbox-group v-model:value="item.value" :options="item.options"/>
        </a-form-item>
      </template>

      <template v-if="item.type === 'datetime'">
        <a-form-item :label="item.label" :name="item.label" >
          <a-date-picker v-model:value="item.value"  show-time picker="date" :format="dateFormat" :placeholder="item.placeholder" style="width: 100%" />
        </a-form-item>
      </template>

      <template v-if="item.type === 'fileUpload'">
        <a-form-item :label="item.label" :name="item.label" >
          <FileUpload :fileList="item.value"></FileUpload>
        </a-form-item>
      </template>
    </slot>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }" v-show="props.showConfirmBtn">
      <a-button type="primary" @click="confirm">Confirm</a-button>
      <a-button style="margin-left: 10px" @click="cancel">Cancel</a-button>
    </a-form-item>
  </a-form>
</template>

<script lang="ts" setup>
import dayjs, { Dayjs } from 'dayjs';
import { reactive, ref, toRaw } from 'vue';
import type { UnwrapRef } from 'vue';
import type { Rule } from 'ant-design-vue/es/form';
import FileUpload from "./FileUpload.vue";
import FileUploadTest from "../FileUploadTest.vue";
import {Item, FileItem, ItemType} from "../../definition/FormData"

interface AddProp {
  prop1: string;
  items: Array<Item>;
  showConfirmBtn: boolean
}

const props = defineProps<AddProp>()


const emit = defineEmits<{
  confirm: [e: Event]
  cancel: [e: Event]
}>()

const confirm = (e) => {
  emit('confirm', e)
}

const cancel = (e) => {
  emit('cancel', e)
}


const formRef = ref();
const labelCol = { span: 5 };
const wrapperCol = { span: 13 };
const rules: Record<string, Rule[]> = {
  name: [
    { required: true, message: 'Please input Activity name', trigger: 'change' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  region: [{ required: true, message: 'Please select Activity zone', trigger: 'change' }],
  date1: [{ required: true, message: 'Please pick a date', trigger: 'change', type: 'object' }],
  type: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one activity type',
      trigger: 'change',
    },
  ],
  resource: [{ required: true, message: 'Please select activity resource', trigger: 'change' }],
  desc: [{ required: true, message: 'Please input activity form', trigger: 'blur' }],
};

const resetForm = () => {
  formRef.value.resetFields();
};

const dateFormat = 'YYYY/MM/DD hh:mm:ss';
</script>

