<template>
  <Modal :open="clothesAddProps.open" @handle-cancel="handleCancel">
    <Add @confirm="confirm" @cancel="handleCancel" :items="items"></Add>
  </Modal>
</template>
<script lang="ts" setup>
import dayjs, { Dayjs } from 'dayjs';
import {reactive, Ref, ref, toRaw} from 'vue';
import type { UnwrapRef } from 'vue';
import type { Rule } from 'ant-design-vue/es/form';
import Modal from "../../../common/Modal.vue";
import Add from "../../../common/Add.vue";
import {Item, FileItem, SelectOption} from "../../../../definition/FormData";
import {getBrandList} from "../../../../service/brand";
import {addClothes} from "../../../../service/clothes";
import { clothesTypes } from "../../../../utils/clothes.ts";
import {ClothesAddVM} from "../../../../definition/clothes/Clothes";
import {message} from "ant-design-vue";

interface ClothesProp {
  open: boolean;
}

const clothesAddProps = defineProps<ClothesProp>()

const brandOptions : Ref<SelectOption[]> = ref([])
getBrandList().then(response => {
  let brandList = response.data.content;
  brandList.forEach(item => {
    const data = {
      label : item.name,
      value: item.id
    }
    brandOptions.value.push(data)
  })
})


const clothesTypeOptions : Ref<SelectOption[]> = ref([])
clothesTypes.value.forEach(item => {
  const data = {
    label : item,
    value: item
  }
  clothesTypeOptions.value.push(data)
})

const dateFormat = 'YYYY/MM/DD hh:mm:ss';

const fileList : Ref<FileItem[]> = ref([])

const items : Ref<Item[]> = ref([
  {
    label : 'name',
    type: 'input',
    placeholder: '请输入名称',
  },
  {
    label : 'brand',
    type: 'option',
    placeholder: '请选择品牌',
    options: brandOptions.value,
  },
  {
    label : 'type',
    type: 'option',
    placeholder: '请选择类型',
    options: clothesTypeOptions.value,
  },
  {
    label : 'price',
    type: 'input',
    placeholder: '请输入价格',
  },
  {
    label : 'purchase date',
    type: 'datetime',
    placeholder: '请选择购买日期',
    value: dayjs(),
  },
  {
    label : 'upload',
    type: 'fileUpload',
    placeholder: '文件上传',
    value: fileList,
  },
])


const emit = defineEmits<{
  dialogCancel: [e: Event]
  dialogConfirm: [e: Event]
}>()

const handleCancel = (e) => {
  emit('dialogCancel', e)
}

const confirm = (e) => {
  const data : Ref<ClothesAddVM> = ref({
      name: items.value.filter(item => item.label === 'name')[0].value,
      brandId: items.value.filter(item => item.label === 'brand')[0].value,
      price: items.value.filter(item => item.label === 'price')[0].value,
      clothesType: items.value.filter(item => item.label === 'type')[0].value,
      purchaseDate: items.value.filter(item => item.label === 'purchase date')[0].value,
      imagePaths: fileList.value.map(item => item.url.substring(item.url.indexOf("/resources/") + 11))
  })

  addClothes(data.value).then(() => {
    message.success("addClothes success")
    emit('dialogConfirm', e)
  })
}
</script>

