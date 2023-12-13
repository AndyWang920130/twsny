<!--
跟 Vue 说 Hello World！
-->
<script lang="ts" setup>
import {reactive, Ref, ref, UnwrapRef} from 'vue'
import Add from './components/common/Add.vue'
import dayjs, {Dayjs} from "dayjs";
import {Item, FileItem} from "./definition/FormData"

const dateFormat = 'YYYY/MM/DD hh:mm:ss';

const fileList : Ref<FileItem[]> = ref([
  {
    id: -1,
    name: 'image.png',
    status: 'done',
    url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
  }
])

const items : Ref<Item[]> = ref([
  {
    label : 'name',
    type: 'input',
    placeholder: '请输入名称',
    value: '234',
  },
  {
    label : 'brand',
    type: 'option',
    placeholder: '请选择品牌',
    options: [ {label: 'Lee', value: 'Lee'}, {label: 'Belle', value: 'Belle'}],
    value: 'belle'
  },
  {
    label : 'gender',
    type: 'radio',
    placeholder: '请选择性别',
    options: [ {label: 'male', value: 'male'}, {label: 'female', value: 'female'}],
    value: 'female'
  },
  {
    label : 'color',
    type: 'checkBox',
    placeholder: '请选择颜色',
    options: [ {label: 'black', value: 'black'}, {label: 'white', value: 'white'},  {label: 'green', value: 'green'}, {label: 'blue', value: 'blue'}],
    value: ['green', 'blue']
  },
  {
    label : 'datetime',
    type: 'datetime',
    placeholder: '请选择日期',
    value: dayjs('2015/01/01 09:00:00', dateFormat),
  },
  {
    label : 'upload',
    type: 'fileUpload',
    placeholder: '文件上传',
    value: fileList,
  },
])

const props1 = ref('props1Value')


// “ref”是用来存储值的响应式数据源。
// 理论上我们在展示该字符串的时候不需要将其包装在 ref() 中，
// 但是在下一个示例中更改这个值的时候，我们就需要它了。
const message = ref('Hello World!')
// const message = 'Hello World!';



const confirm = (e) => {
  alert("Add组件confirm透传")
}

const cancel = (e) => {
  alert("Add组件cancel透传")
}


</script>

<template>
    <h1>{{ message }}</h1>
<!--    <Add @confirm="confirm" @cancel="cancel" :items="items"></Add>-->
  <Add @confirm="confirm" @cancel="cancel" :prop1="props1" :items="items"></Add>
</template>