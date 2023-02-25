package dev.tarico.models;

import dev.tarico.services.ValueChecker;

/**
 * Inventory keeps track of a single item, its location, and quantity.
 * 
 * @author Tara Arico - 7.23.2022
 */
public class Inventory {
    private int inventoryId;
    private int itemId;
    private int locationId;
    private int quantity;
    private boolean valid;

    /**
     * Default, no-params constructor.
     */
    public Inventory() {}

    /**
     * Creates an Inventory object with the specified parameters.
     * 
     * @param inventoryId - id of this Inventory object. Must not be negative.
     * @param itemId - id of the item stored in this Inventory object. Must not be negative.
     * @param locationId - id of the Inventory's location. Must not be negative.
     * @param quantity - amount of items being stored at this location. Must not be negative.
     */
    public Inventory(int inventoryId, int itemId, int locationId, int quantity) {
        try {
            ValueChecker.checkNegative(inventoryId, "id");
            this.inventoryId = inventoryId;
            ValueChecker.checkNegative(itemId, "id");
            this.itemId = itemId;
            ValueChecker.checkNegative(locationId, "id");
            this.locationId = locationId;
            ValueChecker.checkNegative(quantity, "quantity");
            this.quantity = quantity;
            this.valid = true;
        } catch(IllegalArgumentException e) { 
            this.valid = false;
        }
    }

    /**
     * Creates an Inventory object with the specified parameters. Using this constructor will 
     * make 0 the default quantity of the item specified.
     * 
     * @param inventoryId - id of this Inventory object. Must not be negative.
     * @param itemId - id of the item stored in this Inventory object. Must not be negative.
     * @param locationId - id of the Inventory's location. Must not be negative.
     */
    public Inventory(int inventoryId, int itemId, int locationId) {
        this(inventoryId, itemId, locationId, 0);
    }

    /**
     * Retrieves the id of the object.
     * 
     * @return id of this Inventory object
     */
    public int getInventoryId() {
        return inventoryId;
    }

    /**
     * Sets the id of this Inventory object to the argument specified.
     * 
     * @param inventoryId - id to set. Must not be negative.
     */
    public void setInventoryId(int inventoryId) {
        try {
            ValueChecker.checkNegative(inventoryId, "id");
            this.inventoryId = inventoryId;
        } catch (IllegalArgumentException e) {
            //silently fail for now
        }
    }
    
    /**
     * Retrieves the id of the Inventory's item.
     * 
     * @return id of item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Sets the id of the item in this Inventory object to the argument specified.
     * 
     * @param itemID - id to set. Must not be negative
     */
    public void setItemId(int itemId) {
        try {
            ValueChecker.checkNegative(itemId, "id");
            this.itemId = itemId;
        } catch (IllegalArgumentException e) {
            //silently fail for now
        }
    }

    /**
     * Retrieves the id of this Inventory's location.
     * 
     * @return the id of the location
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Sets the Inventory's location id to the argument specified.
     * 
     * @param locationId - id of location to set. Must not be negative.
     */
    public void setLocationId(int locationId) {
        try {
            ValueChecker.checkNegative(locationId, "id");
            this.locationId = locationId;
        } catch (IllegalArgumentException e) { 
            //silently fail for now
        }
    }

    /**
     * Retrieves the amount of items stored in this Inventory object.
     * 
     * @return quantity of items stored here
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the amount of items stored in this Inventory object to the argument specified.
     * 
     * @param quantity - quantity of items. Must not be negative.
     */
    public void setQuantity(int quantity) {
        try {
            ValueChecker.checkNegative(quantity, "quantity");
            this.quantity = quantity;
        } catch (IllegalArgumentException e) {
            // silently fail for now
        }
    }

    /**
     * Returns whether or not this object is a valid instance of Inventory
     * 
     * @return boolean value denoting validity of object
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns this Inventory object as a String
     * 
     * @return string of this object
     */
    @Override
    public String toString() {
        return "Inventory [inventoryId=" + inventoryId + ", itemId=" + itemId + ", locationId=" + locationId
                + ", quantity=" + quantity + "]";
    }
    
}
