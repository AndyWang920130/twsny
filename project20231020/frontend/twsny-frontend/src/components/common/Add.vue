<!--<template>-->
<!--  <a-form-->
<!--      ref="formRef"-->
<!--      :model="items"-->
<!--      :rules="rules"-->
<!--      :label-col="labelCol"-->
<!--      :wrapper-col="wrapperCol"-->
<!--  >-->
<!--    <a-form-item ref="name" label="Activity name" name="name">-->
<!--      <a-input v-model:value="items.prop2.name" />-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity zone" name="region">-->
<!--      <a-select v-model:value="items.prop2.region" placeholder="please select your zone">-->
<!--        <a-select-option value="shanghai">Zone one</a-select-option>-->
<!--        <a-select-option value="beijing">Zone two</a-select-option>-->
<!--      </a-select>-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity time" required name="date1">-->
<!--      <a-date-picker-->
<!--          v-model:value="items.prop2.date1"-->
<!--          show-time-->
<!--          type="date"-->
<!--          placeholder="Pick a date"-->
<!--          style="width: 100%"-->
<!--      />-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Instant delivery" name="delivery">-->
<!--      <a-switch v-model:checked="items.prop2.delivery" />-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity type" name="type">-->
<!--      <a-checkbox-group v-model:value="items.prop2.type">-->
<!--        <a-checkbox value="1" name="type">Online</a-checkbox>-->
<!--        <a-checkbox value="2" name="type">Promotion</a-checkbox>-->
<!--        <a-checkbox value="3" name="type">Offline</a-checkbox>-->
<!--      </a-checkbox-group>-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Resources" name="resource">-->
<!--      <a-radio-group v-model:value="items.prop2.resource">-->
<!--        <a-radio value="1">Sponsor</a-radio>-->
<!--        <a-radio value="2">Venue</a-radio>-->
<!--      </a-radio-group>-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity form" name="desc">-->
<!--      <a-textarea v-model:value="items.prop2.desc" />-->
<!--    </a-form-item>-->
<!--    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">-->
<!--      <a-button type="primary" @click="confirm">Confirm</a-button>-->
<!--      <a-button style="margin-left: 10px" @click="cancel">Cancel</a-button>-->
<!--    </a-form-item>-->
<!--  </a-form>-->
<!--</template>-->

<template>
  <a-form
      ref="formRef"
      :rules="rules"
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
        <a-form-item :label="item.label" required :name="item.label" >
          <a-date-picker v-model:value="item.value"  show-time picker="date" :format="dateFormat" :placeholder="item.placeholder" style="width: 100%" />
        </a-form-item>
      </template>

      <template v-if="item.type === 'fileUpload'">
        <a-form-item :label="item.label" required :name="item.label" >
          <FileUpload :fileList="item.value"></FileUpload>
        </a-form-item>
      </template>
    </slot>
<!--    <a-form-item ref="name" label="Activity name" name="name">-->
<!--      <a-input v-model:value="items.prop2.name" />-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity zone" name="region">-->
<!--      <a-select v-model:value="items.prop2.region" placeholder="please select your zone">-->
<!--        <a-select-option value="shanghai">Zone one</a-select-option>-->
<!--        <a-select-option value="beijing">Zone two</a-select-option>-->
<!--      </a-select>-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity time" required name="date1">-->
<!--      <a-date-picker-->
<!--          v-model:value="items.prop2.date1"-->
<!--          show-time-->
<!--          type="date"-->
<!--          placeholder="Pick a date"-->
<!--          style="width: 100%"-->
<!--      />-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Instant delivery" name="delivery">-->
<!--      <a-switch v-model:checked="items.prop2.delivery" />-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity type" name="type">-->
<!--      <a-checkbox-group v-model:value="items.prop2.type">-->
<!--        <a-checkbox value="1" name="type">Online</a-checkbox>-->
<!--        <a-checkbox value="2" name="type">Promotion</a-checkbox>-->
<!--        <a-checkbox value="3" name="type">Offline</a-checkbox>-->
<!--      </a-checkbox-group>-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Resources" name="resource">-->
<!--      <a-radio-group v-model:value="items.prop2.resource">-->
<!--        <a-radio value="1">Sponsor</a-radio>-->
<!--        <a-radio value="2">Venue</a-radio>-->
<!--      </a-radio-group>-->
<!--    </a-form-item>-->
<!--    <a-form-item label="Activity form" name="desc">-->
<!--      <a-textarea v-model:value="items.prop2.desc" />-->
<!--    </a-form-item>-->
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
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
interface AddProp {
  prop1: string;
  items: Array<Item>;
}

interface FileItem {
  id: number,
  name: string,
  url: string,
  status: string
}

interface Item {
  label: string;
  type: string;
  placeholder: string;
  options?: Array<SelectOption>; //?代表可选属性
  value: string | dayjs | Array<String> | Array<FileItem>;
}

interface SelectOption {
  label: string;
  value: string;
}


// interface Item {
//   name: string;
//   region: string | undefined;
//   date1: Dayjs | undefined;
//   delivery: boolean;
//   type: string[];
//   resource: string;
//   desc: string;
// }

const props = defineProps<AddProp>()

const emit = defineEmits(['confirm', 'cancel'])
const confirm = () => {
  emit('confirm')
}

const cancel = () => {
  emit('cancel')
}

interface FormState {
  name: string;
  region: string | undefined;
  date1: Dayjs | undefined;
  delivery: boolean;
  type: string[];
  resource: string;
  desc: string;
}
const formRef = ref();
const labelCol = { span: 5 };
const wrapperCol = { span: 13 };
const formState: UnwrapRef<FormState> = reactive({
  name: '',
  region: undefined,
  date1: undefined,
  delivery: false,
  type: [],
  resource: '',
  desc: '',
});
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
const onSubmit = () => {
  formRef.value
      .validate()
      .then(() => {
        console.log('values', formState, toRaw(formState));
      })
      .catch(error => {
        console.log('error', error);
      });
};
const resetForm = () => {
  formRef.value.resetFields();
};

const dateFormat = 'YYYY/MM/DD hh:mm:ss';
</script>

