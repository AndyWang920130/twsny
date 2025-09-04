import store from '@/store';
/**
 * Action 权限指令
 * 指令用法：
 *  - 在需要控制 action 级别权限的组件上使用 v-action:[method] , 如下：
 *    <i-button v-action:add >添加用户</a-button>
 *    <a-button v-action:delete>删除用户</a-button>
 *    <a v-action:edit @click="edit(record)">修改</a>
 *
 *  - 当前用户没有权限时，组件上使用了该指令则会被隐藏
 *  - 当后台权限跟 pro 提供的模式不同时，只需要针对这里的权限过滤进行修改即可
 *
 *  @see https://github.com/vueComponent/ant-design-vue-pro/pull/53
 */
 // 检查权限的函数
const hasPermission = (roles, actionName) => {
    console.log("hasPermission ...")
    const permissions = store.getters.permissions;
    return roles.some(role => permissions[role]?.includes(actionName));
}

export default {
  install(app) {
    app.directive('action', {
      mounted(el, binding) {
        const actionName = binding.arg
        store
        const roles = store.getters.roles
        // const permissionId = vnode.context.$route.permission
        if (!hasPermission(roles, actionName)) {
          setTimeout(() => {
            el.parentNode && el.parentNode.removeChild(el)
          }, 10)
        }
      }
    })
  }
}