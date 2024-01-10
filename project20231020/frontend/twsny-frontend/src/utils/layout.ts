import {h, reactive, Ref, ref} from "vue";
import {PieChartOutlined} from "@ant-design/icons-vue";
import {defaultPath} from "../router"
import {getMenuListByParentId, getRootMenuList} from "../service/menu";

interface navMenuItem {
    key: string,
    label: string,
    title: string
}

export const defaultNavMenuItems : Ref<navMenuItem[]> = ref([])
// @ts-ignore
await getRootMenuList().then(response => {
    response.data.content.forEach(item => {
        const data = {
            key: item.id,
            label: item.name,
            title: item.name,
        };
        // return data;
        defaultNavMenuItems.value.push(data)
    })
})

export const defaultLifeSubNavMenuItems = ref([])
// @ts-ignore
await getMenuListByParentId(132).then(response => {
    response.data.content.forEach(item => {
        const data = {
            key: item.id,
            icon: () => h(PieChartOutlined),
            label: item.name,
            title: item.name,
            children: []
        };

        if (item.children) {
            const childrenArray = []
            item.children.forEach(childrenItem => {
                const childrenData = {
                    key: childrenItem.id,
                    label: childrenItem.name,
                    title: childrenItem.name,
                    url: childrenItem.url
                }
                childrenArray.push(childrenData)
            })
            data.children = childrenArray
        }
        // return data;
        defaultLifeSubNavMenuItems.value.push(data)
    })
})

export const defaultWorkSubNavMenuItems = ref([])
// @ts-ignore
await getMenuListByParentId(133).then(response => {
    response.data.content.forEach(item => {
        const data = {
            key: item.id,
            icon: () => h(PieChartOutlined),
            label: item.name,
            title: item.name,
            children: []
        };

        if (item.children) {
            const childrenArray = [];
            item.children.forEach(childrenItem => {
                const childrenData = {
                    key: childrenItem.id,
                    label: childrenItem.name,
                    title: childrenItem.name,
                    url: childrenItem.url
                }
                childrenArray.push(childrenData)
            })
            data.children = childrenArray
        }
        // return data;
        defaultWorkSubNavMenuItems.value.push(data)
    })
})

export const defaultEntertainmentSubNavMenuItems = ref([])
// @ts-ignore
await getMenuListByParentId(134).then(response => {
    response.data.content.forEach(item => {
        const data = {
            key: item.id,
            icon: () => h(PieChartOutlined),
            label: item.name,
            title: item.name,
            children: []
        };

        if (item.children) {
            const childrenArray = []
            item.children.forEach(childrenItem => {
                const childrenData = {
                    key: childrenItem.id,
                    label: childrenItem.name,
                    title: childrenItem.name,
                    url: childrenItem.url
                }
                childrenArray.push(childrenData)
            })
            data.children = childrenArray
        }
        // return data;
        defaultEntertainmentSubNavMenuItems.value.push(data)
    })
})

export const defaultConfigSubNavMenuItems = ref([])
// @ts-ignore
await getMenuListByParentId(135).then(response => {
    response.data.content.forEach(item => {
        const data = {
            key: item.id,
            icon: () => h(PieChartOutlined),
            label: item.name,
            title: item.name,
            children: []
        };

        if (item.children) {
            const childrenArray = []
            item.children.forEach(childrenItem => {
                const childrenData = {
                    key: childrenItem.id,
                    label: childrenItem.name,
                    title: childrenItem.name,
                    url: childrenItem.url
                }
                childrenArray.push(childrenData)
            })
            data.children = childrenArray
        }
        // return data;
        defaultConfigSubNavMenuItems.value.push(data)
    })
})


// @ts-ignore
export const defaultNavKeyMap = new Map()
defaultNavKeyMap.set(132, reactive(defaultLifeSubNavMenuItems))
defaultNavKeyMap.set(133, reactive(defaultWorkSubNavMenuItems))
defaultNavKeyMap.set(134, reactive(defaultEntertainmentSubNavMenuItems))
defaultNavKeyMap.set(135, reactive(defaultConfigSubNavMenuItems))