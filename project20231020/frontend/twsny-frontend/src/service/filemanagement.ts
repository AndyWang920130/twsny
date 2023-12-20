import { request } from '../request/request'
import {ClothesAddVM} from "../definition/clothes/Clothes";
import {FileAddVM, FileUpdateVM, FolderAddVM, FolderUpdateVM} from "../definition/filemanagetment/FileManagement";
import {UnwrapRef} from "vue";

export const getManagementFileList = () => {
    return request({
        url: '/fileManagement',
        method: 'get'
    })
};


export const addFolder = (data: FolderAddVM) => {
    return request({
        url: '/directory',
        method: 'post',
        data: data
    })
};

export const updateFolder = (data: FolderUpdateVM) => {
    return request({
        url: '/directory',
        method: 'put',
        data: data
    })
};

export const deleteFolder = (id: number) => {
    const url = '/directory/' + id
    return request({
        url: url,
        method: 'delete',
    })
};


export const addFile = (data: FileAddVM) => {
    return request({
        url: '/file',
        method: 'post',
        data: data
    })
};

export const updateFile = (data: FileUpdateVM) => {
    return request({
        url: '/file',
        method: 'put',
        data: data
    })
};

export const deleteFile = (id: number) => {
    const url = '/file/' + id
    return request({
        url: url,
        method: 'delete',
    })
};