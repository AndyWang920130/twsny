// @ts-ignore
import { request } from '../request/request'
// @ts-ignore
import { ClothesAddVM } from '../components/life/home/clothes/ClothesVM'

export const getClothesList = () => {
    return request({
        url: '/clothes',
        method: 'get'
    })
};

export const getClothes = (id: number) => {
    const url = '/clothes' + '/${id}'
    return request({
        url: url,
        method: 'get'
    })
};

export const addClothes = (data: ClothesAddVM) => {
    return request({
        url: '/clothes',
        method: 'post',
        data: data
    })
};

export const updateClothes = (data) => {
    return request({
        url: '/clothes',
        method: 'put',
        data: data
    })
};


export const deleteClothes = (id: number) => {
    const url = '/clothes' + '/${id}'
    return request({
        url: url,
        method: 'delete',
    })
};


export const uploadFile = (file: File) => {
    const formData = new FormData()
    formData.append('file', file)
    const url = '/upload/common'
    return request({
        url: url,
        method: 'post',
        data: formData
    })
};




