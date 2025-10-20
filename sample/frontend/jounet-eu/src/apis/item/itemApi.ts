import { api } from "../utils/api";
const base_path = '/items'
export const fetchItems = (params?: any) => api.get(base_path, params);
export const fetchItem = (id: number) => api.get(base_path + `/${id}`);
export const createItem = (data: any) => api.post(base_path + '/', data);
export const updateItem = (id: number, data: any) => api.put(base_path + `/${id}`, data);
export const deleteItem = (id: number) => api.delete(base_path + `/${id}`);