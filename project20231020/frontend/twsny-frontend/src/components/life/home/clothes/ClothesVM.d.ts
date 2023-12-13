// @ts-ignore
import dayjs from "dayjs";

interface ClothesAddVM {
    name: string,
    brandId?: number
    price?: number,
    clothesType?: string,
    purchaseDate?: dayjs,
    imagePaths?: Array<string>
}