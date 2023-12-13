// @ts-ignore
import dayjs from "dayjs";

interface Item {
    label: string;
    type: ItemType;
    placeholder: string;
    options?: Array<SelectOption>; //?代表可选属性
    value?: string | dayjs | Array<String> | Array<FileItem>;
}

type ItemType = 'input' | 'option' | 'radio'| 'checkBox' | 'datetime' | 'fileUpload'

interface SelectOption {
    label: string;
    value: string;
}

export interface FileItem {
    id: number,
    name: string,
    url?: string,
    status: string,
    percent?: number
}