<template>
  <div class="clearfix">
    <a-upload
        v-model:file-list="fileList"
        :action="upload"
        list-type="picture-card"
        :before-upload="beforeUpload"
        @preview="handlePreview"
        @change="handleChange"
    >
      <div v-if="fileList.length < 5">
        <plus-outlined />
        <div style="margin-top: 8px">Upload</div>
      </div>
    </a-upload>
    <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { PlusOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import type { Ref, UnwrapRef } from 'vue';
import type { UploadProps } from 'ant-design-vue';
import type { UploadChangeParam } from 'ant-design-vue';
import { message } from 'ant-design-vue';
import {uploadFile} from "../../service/clothes";


interface FileItem {
  id: number,
  name: string,
  url: string,
  status: string
}

interface FileUploadProp {
  fileList: Array<FileItem>;
}
const fileUploadProp = defineProps<FileUploadProp>()
const fileList = ref<UploadProps['fileList']>(fileUploadProp.fileList)

function getBase64(file: File) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');

const handleChange = (info: UploadChangeParam) => {
  if (info.file.status !== 'uploading') {
    console.log(info.file, info.fileList);
  }
  if (info.file.status === 'done') {
    message.success(`${info.file.name} file uploaded successfully`);
  } else if (info.file.status === 'error') {
    message.error(`${info.file.name} file upload failed.`);
  }
};

const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = '';
};
const handlePreview = async (file: UploadProps['fileList'][number]) => {
  if (!file.url && !file.preview) {
    file.preview = (await getBase64(file.originFileObj)) as string;
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
};

const beforeUpload = (file: UploadProps['fileList'][number], fileList) => {
  // const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
  // if (!isJpgOrPng) {
  //   message.error('You can only upload JPG file!');
  // }
  // const isLt2M = file.size / 1024 / 1024 < 2;
  // if (!isLt2M) {
  //   message.error('Image must smaller than 2MB!');
  // }
  // return isJpgOrPng && isLt2M;
  return true;
};

const upload = (file : UploadProps['fileList'][number]) => {
  // return new Promise((resolve, reject) => {
  //   const reader = new FileReader();
  //   reader.readAsDataURL(file);
  //   reader.onload = () => resolve(reader.result);
  //   reader.onerror = error => reject(error);
  // });
  // uploadFile(file)
  //     .then(message.info('Upload file successful'))
  //     .catch(message.error('Upload file failure'))
  return new Promise(() => {
    uploadFile(file)
        .then((res) => {
          // console.log(res, res.status)
          message.info('Upload file successful')})
          fileList.value.push(file)
        .catch((error) => {
          // console.error(error)
          // console.log("Upload file failure: " + error.message)
          message.error('Upload file failure')
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
<style scoped>
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>