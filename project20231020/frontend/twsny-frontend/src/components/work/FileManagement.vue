<template>
  <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAddFolder">Add Folder</a-button>
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

            <template v-else-if="record.fileManagementType === 'FOLDER'">
              <a-button type="link" style="margin: -5px 0" @click="handleFolderClick(record.id)">{{ text }}</a-button>
            </template>

            <template v-else-if="record.fileManagementType === 'FILE'">
<!--              <a-button type="link" style="margin: -5px 0" @click="downloadIframe('http://localhost:8080/api/v1/resources/' + record.name)">{{ text }}</a-button>-->
<!--              <a-button type="link" style="margin: -5px 0" @click="downloadIframe('https://shanghaiaedobs.obs.cn-east-3.myhuaweicloud.com:443/tog/template/%E5%BF%97%E6%84%BF%E8%80%85%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF.xlsx')">{{ text }}</a-button>-->
              <a-button type="link" style="margin: -5px 0" @click="openFile('http://localhost:8080/api/v1/resources/' + record.name)">{{ text }}</a-button>
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
import {Item} from "../../definition/FormData";
import {useRoute, useRouter} from "vue-router";
import {defaultPath} from "../../router";


// interface FileManagementProp {
//   rootFolderId: Number;
// }
//
// const props = defineProps<FileManagementProp>()
//
// const rootId : Ref<number> = ref(props.rootFolderId)

// const rootId : Ref<number> = ref(this.$route.params.rootFolderId)

const router = useRouter()
const route = useRoute()

console.log("params: " + route.params.folderId)
const folderId = ref(route.params.folderId)

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

// if (!folderId) folderId.value = '1'
const rootId: Ref<number> = ref(1);
if (folderId.value) {
    rootId.value = parseInt(<string>folderId.value);
}

const dataSource: Ref<DataItem[]> = ref([])

let dataIndex = 0;

const init = () => {
  dataSource.value.splice(0)
  getManagementFileList(rootId.value).then(response => {
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
}

init()

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
      dataSource.value.filter(item => dataItem.key === item.key)[0].id = response.data.id
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
    if (fileManagementType === 'FOLDER') deleteFolder(id).catch(error => {
      message.error('Delete folder failure, errorCode: ' + error.message)
    })
    if (fileManagementType === 'FILE') deleteFile(id)
  }

  dataSource.value = dataSource.value.filter(item => item.key !== key);
};

const cancel = (key: number) => {
  delete editableData[key];
};

const handleAddFolder = () => {
  const newData = {
    key: dataIndex++,
    name: "新建文件夹",
    parentId: rootId.value,
    fileManagementType: 'FOLDER'
  };
  dataSource.value.push(newData);
};

const handleAddFile = (file) => {
  const newData = {
    key: dataIndex++,
    name: file.name,
    updateDate: file.lastModifiedDate,
    parentId:file.directory.id,
    fileManagementType: 'FILE'
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
          addFile(fileAddVM).then(response => {
            handleAddFile(response.data)
          })
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

const handleFolderClick = (id: number) => {
  rootId.value = id
  router.push(defaultPath.fileManagement + "/" + id)
  // router.push(defaultPath.clothes)
  init()
}

const downloadIframe = (url: string) => {
  const iframe = document.createElement("iframe");
  iframe.style.display = "none"; // 防止影响页面
  iframe.style.height = "0"; // 防止影响页面
  iframe.src = url;
  document.body.appendChild(iframe); // 这一行必须，iframe挂在到dom树上才会发请求
  setTimeout(() => {
    iframe.remove();
  }, 5 * 60 * 1000);
};

const openFile = (url: string) => {
  window.open(url)
};

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