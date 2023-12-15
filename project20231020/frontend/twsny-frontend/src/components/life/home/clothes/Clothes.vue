<template>
  <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">Add</a-button>
  <a-table bordered :data-source="dataSource" :columns="columns">
    <template #bodyCell="{ column, text, record }">
      <template v-if="['url', 'directory', 'description'].includes(column.dataIndex)">
        <div>
          <a-input
              v-if="editableData[record.key]"
              v-model:value="editableData[record.key][column.dataIndex]"
              style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>

      <template v-if="column.dataIndex === 'imagePaths'">
        <a-image-preview-group>
          <template v-for="imagePath in record.imagePaths">
            <a-image :width="10" src="\mnt\usr\local\twsny\upload\1702546577130_code.txt" />
          </template>
        </a-image-preview-group>
      </template>

      <template v-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations">
          <span v-if="editableData[record.key]">
            <a-typography-link @click="save(record.key)">Save</a-typography-link>
            <a-popconfirm title="Sure to cancel?" @confirm="cancel(record.key)">
              <a>Cancel</a>
            </a-popconfirm>
          </span>
          <span v-else>
            <a @click="edit(record.key)">Edit</a>
          </span>
          <a-popconfirm v-if="dataSource.length" title="Sure to delete?"
                        @confirm="onDelete(record.key)">
            <a>Delete</a>
          </a-popconfirm>
        </div>
      </template>
    </template>
  </a-table>
  <ClothesAdd :open="clotheAddModalOpen" @dialog-cancel="dialogCancel" @cancel="dialogCancel"></ClothesAdd>
</template>
<script lang="ts" setup>
import { computed, reactive, ref } from 'vue';
import type { Ref, UnwrapRef } from 'vue';
import { CheckOutlined, EditOutlined } from '@ant-design/icons-vue';
import { cloneDeep } from 'lodash-es';
import {getClothesList, getClothes, addClothes, updateClothes, deleteClothes} from "../../../../service/clothes";
import ClothesAdd from "./ClothesAdd.vue";
import {message} from "ant-design-vue";

const clotheAddModalOpen = ref<boolean>(false);
interface DataItem {
  key: number,
  id: number,
  name: string,
  brand: string,
  type: string,
  price: number,
  purchaseDate: string,
  imagePaths: Array<string>
}

const columns = [
  {
    title: 'name',
    dataIndex: 'name',
  },
  {
    title: 'brand',
    dataIndex: 'brand',
  },
  {
    title: 'type',
    dataIndex: 'type',
  },
  {
    title: 'price',
    dataIndex: 'price',
  },
  {
    title: 'purchaseDate',
    dataIndex: 'purchaseDate',
  },
  {
    title: 'images',
    dataIndex: 'imagePaths',
  },
  {
    title: 'operation',
    dataIndex: 'operation',
  },
];

// const websitesDataSource : Ref<WebsitesItem[]> = ref([])
const dataSource: Ref<DataItem[]> = ref([])
let dataIndex = 0;
getClothesList().then(response => {
  let resultData = response.data.content;
  resultData.forEach(item => {
    const data = {
      key: dataIndex++,
      id: item.id,
      name: item.name,
      brand: item.brand ? item.brand.name : null,
      type: item.clothesTypeEnum,
      price: item.price,
      purchaseDate: item.purchaseDate,
      imagePaths: item.imagePaths ? item.imagePaths.split(";") : []
    };
    dataSource.value.push(data)
  })
})
console.log("dataSource:" + dataSource.value)
const count = computed(() => dataSource.value.length + 1);
const editableData: UnwrapRef<Record<string, DataItem>> = reactive({});

const edit = (key: number) => {
  // console.log("key: " + key)
  // editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.key)[0])
  alert("need to complete");
};
const save = (key: number) => {
  // Object.assign(dataSource.value.filter(item => key === item.key)[0], editableData[key]);
  // const websiteItem = editableData[key];
  // if (websiteItem.id) {
  //   updateClothes(websiteItem).then(response => {
  //     console.log("update website obj successfully")
  //   })
  // } else {
  //   addClothes(websiteItem).then(response => {
  //     console.log("save website obj successfully")
  //   })
  // }
  //
  // delete editableData[key];
  alert("need to complete")
};

const onDelete = (key: number) => {
  const id = dataSource.value.filter(item => key === item.key)[0].id
  if (id) deleteClothes(id)
  dataSource.value = dataSource.value.filter(item => item.key !== key);
};

const cancel = (key: number) => {
  delete editableData[key];
};

const handleAdd = () => {
  // const newData = {
  //   key: dataIndex,
  //   id: null,
  //   url: `www.baidu.com ${dataIndex}`,
  //   directory: '门户网站',
  //   description: `baidu ${dataIndex}`,
  // };
  // dataSource.value.push(newData);
  // alert("need to complete")
  clotheAddModalOpen.value = true
};

// const dialogConfirm = (e) => {
//   clotheAddModalOpen.value = false
// }

const dialogCancel = (e) => {
  clotheAddModalOpen.value = false
}

</script>
<style lang="less" scoped>
.editable-cell {
  position: relative;
  .editable-cell-input-wrapper,
  .editable-cell-text-wrapper {
    padding-right: 24px;
  }

  .editable-cell-text-wrapper {
    padding: 5px 24px 5px 5px;
  }

  .editable-cell-icon,
  .editable-cell-icon-check {
    position: absolute;
    right: 0;
    width: 20px;
    cursor: pointer;
  }

  .editable-cell-icon {
    margin-top: 4px;
    display: none;
  }

  .editable-cell-icon-check {
    line-height: 28px;
  }

  .editable-cell-icon:hover,
  .editable-cell-icon-check:hover {
    color: #108ee9;
  }

  .editable-add-btn {
    margin-bottom: 8px;
  }
}
.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-row-operations a {
  margin-right: 8px;
}

button, a {
  display: inline;
}
</style>