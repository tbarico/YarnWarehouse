
/**
 * Represents an Item object in Java.
 * 
 * @author Tara Arico 7.30.2022
 */
export class Item {
    itemId :number;
    categoryName :string;
    quantityTotal :number;
    price :number;
    valid :boolean;

    constructor(itemId: number, categoryName: string, quantityTotal: number, price: number, valid: boolean) {
        this.itemId = itemId;
        this.categoryName = categoryName;
        this.quantityTotal = quantityTotal;
        this.price = price;
        this.valid = valid;
    }
}