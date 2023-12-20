// @ts-ignore
import dayjs from "dayjs";

interface FolderAddVM {
    name: string,
    parentId?: number
}

interface FolderUpdateVM {
    id: number,
    name?: string
}

interface FileAddVM {
    name: string,
    directoryId: number
}

interface FileUpdateVM {
    id: number,
    name: string
}