<template>
  need to completed!!!
  <Add :items="items" :showConfirmBtn="showBtn"></Add>
</template>
<script lang="ts" setup>
import {FileItem, Item, ItemType, SelectOption} from "../../../definition/FormData";
import {Ref, ref} from "vue";
import dayjs from "dayjs";

import Add from "../../common/Add.vue";

const items: Ref<Item[]> = ref([
  {
    label: '网站类型',
    type: 'input',
    placeholder: '请输入网站类型',
  }
])

const showBtn = ref(false)

</script>