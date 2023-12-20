<template>
  <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">Add Folder</a-button>
  <a-upload :file-list="fileList" :action="handleUpload">
    <a-button class="editable-add-btn" style="margin-bottom: 8px" >Upload File</a-button>
  </a-upload>
  <a-table bordered :data-source="dataSource" :columns="columns">
    <template #bodyCell="{ column, text, record }">
      <template v-if="['name'].includes(column.dataIndex)">
        <div>
          <a-space>
            <template v-if="record.fileManagementType === 'FOLDER'"><FolderTwoTone /></template>
            <template v-if="record.fileManagementType === 'FILE'"><FileTwoTone /></template>

            <a-input
              v-if="editableData[record.key]"
              v-model:value="editableData[record.key][column.dataIndex]"
              style="margin: -5px 0"
            />
            <template v-else>
              {{ text }}
            </template>
          </a-space>

        </div>
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
</template>
<script lang="ts" setup>
import { computed, reactive, ref } from 'vue';
import type { Ref, UnwrapRef } from 'vue';
import { CheckOutlined, EditOutlined } from '@ant-design/icons-vue';
import { cloneDeep } from 'lodash-es';
import dayjs from "dayjs";
import {
  addFile,
  addFolder, deleteFile,
  deleteFolder,
  getManagementFileList,
  updateFile,
  updateFolder
} from "../../service/filemanagement";
import {FileAddVM, FolderUpdateVM} from "../../definition/filemanagetment/FileManagement";

import {
  FolderTwoTone,FileTwoTone
} from '@ant-design/icons-vue';
import {message} from "ant-design-vue";
import {uploadFile} from "../../service/common";

interface DataItem {
  key: number;
  id?: number;
  name: string;
  updateTime?: string;
  size?: string;
  parentId: number;
  fileManagementType: string;
}

const columns = [
  {
    title: 'name',
    dataIndex: 'name',
  },
  {
    title: 'update time',
    dataIndex: 'updateDate',
  },
  {
    title: 'size',
    dataIndex: 'size',
  },
  {
    title: 'operation',
    dataIndex: 'operation',
  },
];

const rootId : Ref<number> = ref(1);
const dataSource: Ref<DataItem[]> = ref([])

let dataIndex = 0;
getManagementFileList().then(response => {
  let managementFileDataSource = response.data.content;
  managementFileDataSource.forEach(item => {
    const data = {
      key: dataIndex++,
      id: item.id,
      name: item.name,
      updateDate: item.updateDate,
      size: item.size,
      parentId: rootId.value,
      fileManagementType: item.fileManagementType
    };
    dataSource.value.push(data)
  })
})

const count = computed(() => dataSource.value.length + 1);
const editableData: UnwrapRef<Record<string, DataItem>> = reactive({});

const edit = (key: number) => {
  console.log("key: " + key)
  editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.key)[0]);
};
const save = (key: number) => {
  Object.assign(dataSource.value.filter(item => key === item.key)[0], editableData[key]);
  const folderItem = editableData[key];
  if (folderItem.fileManagementType === 'FOLDER') {
    folderSave(folderItem);
  }
  if (folderItem.fileManagementType === 'FILE') {
    fileSave(folderItem)
  }

  delete editableData[key];
};

const folderSave = (dataItem: DataItem) => {
  const addVM = {name:dataItem.name, parentId:dataItem.parentId}
  const updateVM = {id:dataItem.id, name:dataItem.name}

  if (dataItem.id) {
    updateFolder(updateVM).then(response => {
      console.log("update website obj successfully")
    })
  } else {
    addFolder(addVM).then(response => {
      console.log("save website obj successfully")
    })
  }
}

const fileSave = (dataItem: DataItem) => {
  const addVM = {name:dataItem.name, directoryId:dataItem.parentId}
  const updateVM = {id:dataItem.id, name:dataItem.name}

  if (dataItem.id) {
    updateFile(updateVM).then(response => {
      console.log("update website obj successfully")
    })
  } else {
    addFile(addVM).then(response => {
      console.log("save website obj successfully")
    })
  }
}

const onDelete = (key: number) => {
  const dataItem = dataSource.value.filter(item => key === item.key)[0]
  const id = dataItem.id
  const fileManagementType = dataItem.fileManagementType
  if (id) {
    if (fileManagementType === 'FOLDER') deleteFolder(id)
    if (fileManagementType === 'FILE') deleteFile(id)
  }

  dataSource.value = dataSource.value.filter(item => item.key !== key);
};

const cancel = (key: number) => {
  delete editableData[key];
};

const handleAdd = () => {
  const newData = {
    key: dataIndex,
    name: "新建文件夹",
    parentId: rootId.value,
    fileManagementType: 'FOLDER'
  };
  dataSource.value.push(newData);
};

const fileList = ref([])
const handleUpload = (file : File) => {
  return new Promise(() => {
    uploadFile(file)
        .then((res) => {
          message.info('Upload file successful')
          const fileAddVM = {name: res.data, directoryId: rootId.value}
          addFile(fileAddVM)
        })
        .catch((error) => {
          message.error('Upload file failure: ' + error.message)
        })
  })
      .then((res) => {
        console.log(res.code, res.status)
        message.info('Upload file successful')})
      .catch((error) => {
        console.error(error)
        message.error('Upload file failure')
      })
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