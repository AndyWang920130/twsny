<script setup lang="ts">
defineProps<{
  msg: string
}>()
import { Popover } from 'ant-design-vue';

const onChange = (current: number) => {
  console.log(current);
};
</script>

<template>
  <div style="height: 800px;">
    <!-- <div> -->
    <!-- 全局的方式使用antdesign才能使用a前缀的方式来访问 a-popover -->
    <Popover title="Title">
      <template #content>
        <p>Content</p>
        <p>Content</p>
      </template>
      <a-button type="primary">hello</a-button>
  </Popover>

  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
