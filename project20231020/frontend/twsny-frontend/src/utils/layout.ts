import {h, reactive, Ref, ref} from "vue";
import {PieChartOutlined} from "@ant-design/icons-vue";
import {defaultPath} from "../router"
import {getMenuListByParentId, getRootMenuList} from "../service/menu";
import {addWebsite} from "../service/website";

interface navMenuItem {
    key: string,
    label: string,
    title: string
}

interface subMenuIChildrenItem {
    key: string,
    label: string,
    title: string,
    url: string
}

interface subMenuItem {
    key: string,
    icon: any,
    label: string,
    title: string,
    children: Array<subMenuIChildrenItem>
}

// @ts-ignore
const initNavMenu = async () => {
    const defaultNavMenuItems: Ref<navMenuItem[]> = ref([])
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
    return defaultNavMenuItems
}

// @ts-ignore
export const defaultNavMenuItems = await initNavMenu()

const initSubMenu = async (parentId: number) => {
    const subNavMenuItems: Ref<subMenuItem[]> = ref([])
    await getMenuListByParentId(parentId).then(response => {
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
                data.children.push(...childrenArray)
            }
            subNavMenuItems.value.push(data)
        })
    })
    return subNavMenuItems;
}

// @ts-ignore
const defaultLifeSubNavMenuItems = await initSubMenu(132)

// @ts-ignore
const defaultWorkSubNavMenuItems = await initSubMenu(133)

// @ts-ignore
const defaultEntertainmentSubNavMenuItems = await initSubMenu(134)

// @ts-ignore
const defaultConfigSubNavMenuItems = await initSubMenu(135)

console.log("defaultNavMenuItems: ", defaultNavMenuItems)
console.log("defaultLifeSubNavMenuItems: ", defaultLifeSubNavMenuItems)
console.log("defaultWorkSubNavMenuItems: ", defaultWorkSubNavMenuItems)
console.log("defaultEntertainmentSubNavMenuItems: ", defaultEntertainmentSubNavMenuItems)
console.log("defaultConfigSubNavMenuItems: ", defaultConfigSubNavMenuItems)


// @ts-ignore
export const getSubNavMenuItems = (key: string) => {
    if (key == '132') return defaultLifeSubNavMenuItems;
    if (key == '133') return defaultWorkSubNavMenuItems;
    if (key == '134') return defaultEntertainmentSubNavMenuItems;
    if (key == '135') return defaultConfigSubNavMenuItems;
}

