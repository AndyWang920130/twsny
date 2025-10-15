import { getUsers, getUserById, createUser, updateUser, deleteUser } from "@/apis/user/userApi";
import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
  state: () => ({
    users: [] as any[],
    currentUser: null as any
  }),
  actions: {
    async fetchUsers() {
      const { data } = await getUsers();
      this.users = data;
    },
    async fetchUser(id: number) {
      const { data } = await getUserById(id);
      this.currentUser = data;
    },
    async addUser(user: any) {
      await createUser(user);
      await this.fetchUsers();
    },
    async editUser(id: number, user: any) {
      await updateUser(id, user);
      await this.fetchUsers();
    },
    async removeUser(id: number) {
      await deleteUser(id);
      await this.fetchUsers();
    }
  }
});