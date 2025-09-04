import { createStore } from 'vuex'

const permissions = {
  admin:  ['create', 'edit', 'delete', 'publish', 'admin'],
  editor: ['create', 'edit', 'publish'],
  user:   ['edit']
}

export default createStore({
  state: {
    user: { name: "Andy" },
    roles: ["admin"],
    permissions: permissions
  },
  // 修改 Vuex 中的 state（唯一推荐的方式） 调用方式: store.commit('SET_USER', user) 
  mutations: {
    SET_USER(state, user) {
      state.user = user
    },
    SET_ROLES(state, roles) {
      state.roles = roles
    },
    SET_PERMISSIONS(state, permissions) {
      state.permissions = permissions
    },
  },
  // 处理 异步逻辑（比如调用 API、定时任务）。 调用方式: store.dispatch('ASYNC_SET_USER', user) 
  actions: {
    ASYNC_SET_USER({ commit }, user) { 
      setTimeout(() => {
        commit('SET_USER', user) // 异步后调用 mutation
      }, 1000)
    }
  }, 
  getters: {
    user: state => state.user,
    roles: state => state.roles,
    permissions: state => state.permissions
  }
})


