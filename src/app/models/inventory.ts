
/**
 * Represents an Inventory object in Java.
 * 
 * @author Tara Arico 7.29.2022
 */
export class Inventory {

    inventoryId :number;
    itemId :number;
    locationId :number;
    quantity :number;
    valid :boolean;

    constructor(inventoryId :number, itemId :number, locationId :number, quantity :number, valid :boolean) {
        this.inventoryId = inventoryId;
        this.itemId = itemId;
        this.locationId = locationId;
        this.quantity = quantity;
        this.valid = valid;
    }
}