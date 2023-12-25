import {h, reactive, ref} from "vue";
import {PieChartOutlined} from "@ant-design/icons-vue";
import {defaultPath} from "../router"

interface navMenuItem {
    key: string,
    label: string,
    title: string
}

export const defaultNavMenuItems = ref<navMenuItem[]>([
    {key : "life", label : "生活", title: "生活"},
    {key : "work", label : "工作", title: "工作"},
    {key : "entertainment", label : "娱乐", title: "娱乐"}])


export const defaultLifeSubNavMenuItems = reactive([
        {
            key: 'lifeSub3',
            icon: () => h(PieChartOutlined),
            label: '网站管理',
            title: '网站管理',
            url: defaultPath.websites
        },
        {
            key: 'lifeSub1',
            icon: () => h(PieChartOutlined),
            label: '居家',
            title: '居家',
            children: [
                {
                    key: 'lifeSub1_children1',
                    label: '美食',
                    title: '美食',
                    url: defaultPath.cooking
                },
                {
                    key: 'lifeSub1_children2',
                    label: '园艺',
                    title: '园艺',
                    url: defaultPath.root
                },
                {
                    key: 'lifeSub1_children3',
                    label: '衣橱',
                    title: '衣橱',
                    url: defaultPath.clothes
                },
            ]
        },
        {
            key: 'lifeSub2',
            icon: () => h(PieChartOutlined),
            label: '旅行',
            title: '旅行',
            children: [
                {
                    key: 'lifeSub2_children1',
                    label: '行程',
                    title: '行程',
                    url: defaultPath.root
                },
                {
                    key: 'lifeSub2_children2',
                    label: '酒店',
                    title: '酒店',
                    url: defaultPath.root
                }
            ]
        },
    ]
)

export const defaultWorkSubNavMenuItems = reactive([
        {
            key: 'workSub1',
            icon: () => h(PieChartOutlined),
            label: '编程',
            title: '编程',
            children: [
                {
                    key: 'workSub1_children1',
                    label: '后端',
                    title: '后端',
                    url: defaultPath.root
                },
                {
                    key: 'workSub1_children2',
                    label: '前端',
                    title: '前端',
                    url: defaultPath.root
                },
                {
                    key: 'workSub1_children3',
                    label: '部署',
                    title: '部署',
                    url: defaultPath.root
                },
            ]
        },
        {
            key: 'workSub2',
            icon: () => h(PieChartOutlined),
            label: '专利',
            title: '专利',
            children: [
                {
                    key: 'workSub2_children1',
                    label: '检索',
                    title: '检索',
                    url: defaultPath.root
                },
                {
                    key: 'workSub2_children2',
                    label: '编写',
                    title: '编写',
                    url: defaultPath.root
                },
                {
                    key: 'workSub2_children3',
                    label: '申请',
                    title: '申请',
                    url: defaultPath.root
                }
            ]
        },
    {
        key: 'workSub3',
        icon: () => h(PieChartOutlined),
        label: '文件',
        title: '文件',
        children: [
            {
                key: 'workSub3_children1',
                label: '文件管理',
                title: '文件管理',
                url: defaultPath.fileManagement,
            }
        ]
    },
    ]
)

export const defaultEntertainmentSubNavMenuItems = reactive([
        {
            key: 'entertainmentSub1',
            icon: () => h(PieChartOutlined),
            label: '游戏',
            title: '游戏',
            children: [
                {
                    key: 'entertainmentSub1_children1',
                    label: '英雄联盟',
                    title: '英雄联盟',
                },
                {
                    key: 'entertainmentSub1_children2',
                    label: '塞尔达',
                    title: '塞尔达',
                    url: defaultPath.zelda
                },
            ]
        },
        {
            key: 'entertainmentSub2',
            icon: () => h(PieChartOutlined),
            label: '其他',
            title: '其他',
            children: [
                {
                    key: 'entertainmentSub2_children1',
                    label: '抖音',
                    title: '抖音',
                    url: defaultPath.root
                },
                {
                    key: 'entertainmentSub2_children2',
                    label: '网购',
                    title: '网购',
                    url: defaultPath.root
                }
            ]
        },
    ]
)


// @ts-ignore
export const defaultNavKeyMap = new Map()
defaultNavKeyMap.set("life", defaultLifeSubNavMenuItems)
defaultNavKeyMap.set("work", defaultWorkSubNavMenuItems)
defaultNavKeyMap.set("entertainment", defaultEntertainmentSubNavMenuItems)