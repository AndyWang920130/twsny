<script setup lang="ts">
import { reactive, ref } from 'vue';
import { message,Form, FormItem, Card, Button, InputPassword } from 'ant-design-vue';
import type { FormInstance } from 'ant-design-vue';
import type { PasswordChangeFormT } from '@/apis/UserD';
import type { Rule } from 'ant-design-vue/es/form';

const formState = reactive<PasswordChangeFormT>({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
});

const formRef = ref<FormInstance>();

const rules: Record<string, Rule[]>  = {
  oldPassword: [
    { required: true, message: 'Please enter your old password', trigger: 'blur' },
  ],
  newPassword: [
    { required: true, message: 'Please enter your new password', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: 'Please confirm your new password', trigger: 'blur' },
    {
      validator: (_rule: any, value: string) => {
        if (value !== formState.newPassword) {
          return Promise.reject('The two passwords do not match');
        }
        return Promise.resolve();
      },
      trigger: 'blur',
    },
  ],
};

const handleSubmit = () => {
  formRef.value?.validate().then(() => {
    // TODO: 调用后端API修改密码
    message.success('Password changed successfully!');
  }).catch(() => {
    message.error('Please check the form');
  });
};
</script>

<template>
  <Card title="Change Password" style="max-width: 400px; margin: auto;">
    <Form
      :model="formState"
      :rules="rules"
      ref="formRef"
      layout="vertical"
    >
      <FormItem label="Old Password" name="oldPassword">
        <InputPassword v-model:value="formState.oldPassword" />
      </FormItem>

      <FormItem label="New Password" name="newPassword">
        <InputPassword v-model:value="formState.newPassword" />
      </FormItem>

      <FormItem label="Confirm Password" name="confirmPassword">
        <InputPassword v-model:value="formState.confirmPassword" />
      </FormItem>

      <FormItem style="text-align: right;">
        <Button type="primary" @click="handleSubmit">Change Password</Button>
      </FormItem>
    </Form>
  </Card>
</template>
<style></style>

