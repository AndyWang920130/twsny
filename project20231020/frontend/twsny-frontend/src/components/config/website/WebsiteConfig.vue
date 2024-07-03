<template>
  <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">Add</a-button>
  <a-table bordered :data-source="dataSource" :columns="columns">
    <template #bodyCell="{ column, text, record }">
      <template v-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations">
          <a>Edit</a><a>Delete</a>
        </div>
      </template>
    </template>
  </a-table>
</template>
<script lang="ts" setup>

const columns = [
  {
    title: 'name',
    dataIndex: 'name',
  },
  {
    title: 'value',
    dataIndex: 'value',
  },
  {
    title: 'category',
    dataIndex: 'category',
  },
  {
    title: 'operation',
    dataIndex: 'operation',
  },
];

const dataSource = [
  {
    'name': '1111',
    'value': 'value1111',
    'category': 'category111',
  },
  {
    'name': '22222',
    'value': 'value2222',
    'category': 'category2222',
  },
];

const handleAdd = () => {
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