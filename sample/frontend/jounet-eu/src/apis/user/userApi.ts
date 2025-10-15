import { api } from "../utils/api";
const base_path = '/user'
export const getUsers = () => api.get(base_path);
export const getUserById = (id: number) => api.get(base_path + `/${id}`);
export const createUser = (data: any) => api.post(base_path + '/', data);
export const updateUser = (id: number, data: any) => api.put(base_path + `/${id}`, data);
export const deleteUser = (id: number) => api.delete(base_path + `/${id}`);