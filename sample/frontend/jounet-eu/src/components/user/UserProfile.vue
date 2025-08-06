<script setup lang="ts">
import { ref } from 'vue'

import {Card, Descriptions, DescriptionsItem, Button, Avatar, Modal, Form, FormItem, Input, Select, SelectOptGroup, SelectOption, DatePicker} from 'ant-design-vue';
import type { UserProfileFormT, UserProfileT } from '@/apis/UserD';
import dayjs, { Dayjs } from 'dayjs';
// 定义用户信息类型


const dateFormat = 'YYYY-MM-DD';
const userDate = ref<Dayjs>(dayjs('2025-06-06', dateFormat));

// 用户信息示例（可替换为实际接口返回数据）
const user = ref<UserProfileT>({
  username: 'admin',
  nickname: 'Andy',
  avatar: '/images/userAvatar.jpg', // 示例头像
  gender: 1,
  phone: '137****9999',
  email: 'admin@example.com',
  birthday: userDate.value
})


// 编辑表单临时数据
const editForm = ref<UserProfileFormT>({
  nickname: '',
  avatar: '',
  phone: '',
  email: '',
})



// 编辑事件
const showEditModal = () => {
    editForm.value = {
    nickname: user.value.nickname ?? '',
    gender : user.value.gender,
    avatar: user.value.avatar,
    phone: user.value.phone,
    email: user.value.email,
    birthday: user.value.birthday
  }
  isModalVisible.value = true
}

const isModalVisible = ref(false)

const handleOk = () => {
  user.value.nickname = editForm.value.nickname
  user.value.avatar = editForm.value.avatar
  user.value.phone = editForm.value.phone
  user.value.email = editForm.value.email
  user.value.gender = editForm.value.gender
  user.value.birthday = editForm.value.birthday

  isModalVisible.value = false
}


</script>

<template>
    <div>
        <Card title="个人中心" :bordered="false">
            <Descriptions title="用户信息" bordered :column="1">
            <DescriptionsItem label="账号">{{ user.username }}</DescriptionsItem>
            <DescriptionsItem label="昵称">{{ user.nickname }}</DescriptionsItem>
            <DescriptionsItem label="头像">
                <Avatar :src="user.avatar" shape="square" size="large" />
            </DescriptionsItem>
            <DescriptionsItem label="性别">{{ user.gender == 0 ? 'female' : 'male'}}</DescriptionsItem>
            <DescriptionsItem label="手机号">{{ user.phone }}</DescriptionsItem>
            <DescriptionsItem label="邮箱">{{ user.email }}</DescriptionsItem>
            <DescriptionsItem label="生日">{{ user.birthday }}</DescriptionsItem>
            </Descriptions>
            <div style="margin-top: 16px; text-align: right;">
            <Button type="primary" @click="showEditModal">编辑</Button>
            </div>
        </Card>

        <!-- 编辑弹窗 -->
        <Modal
        title="编辑个人信息"
        v-model:open="isModalVisible"
        @ok="handleOk"
        ok-text="保存"
        cancel-text="取消"
        >
            <Form layout="vertical">
                <FormItem label="昵称">
                <Input v-model:value="editForm.nickname" placeholder="请输入昵称" />
                </FormItem>
                <FormItem label="手机号">
                <Input v-model:value="editForm.phone" placeholder="请输入手机号" />
                </FormItem>
                <FormItem label="性别">
                    <Select v-model:value="editForm.gender">
                        <SelectOption :value="1">male</SelectOption>
                        <SelectOption :value="0">female</SelectOption>
                    </Select>
                </FormItem>
                <FormItem label="邮箱">
                <Input v-model:value="editForm.email" placeholder="请输入邮箱" />
                </FormItem>
                <FormItem label="头像链接">
                <Input v-model:value="editForm.avatar" placeholder="请输入头像链接" />
                </FormItem>
                <FormItem label="生日">
                <DatePicker v-model:value="editForm.birthday" />
                </FormItem>
            </Form>
        </Modal>
    </div>
  
</template>

<style scoped>
/* 可选：自定义样式 */
</style>