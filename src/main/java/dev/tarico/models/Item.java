package dev.tarico.models;

import dev.tarico.models.helpers.ValueChecker;

/**
 * Denotes a single item that is stored at a location. 
 * 
 * @author Tara Arico 7.18.22
 */
public class Item {
    private int itemId;
    private String categoryName;
    private int quantityTotal;
    private double price;
    private boolean valid;

    /**
     * Creates an item with the specified parameters.
     * 
     * @param itemId - Id of the item. Must not be negative.
     * @param categoryName - Must be either "Yarn" or "Knitting Needles". Must not be null.
     * @param quantityTotal - Amount of items per lot. Must not be negative.
     * @param price - Price of item. Must not be negative.
     */
    public Item(int itemId, String categoryName, int quantityTotal, double price) {
        try {
            ValueChecker.checkNegative(itemId, "id");
            this.itemId = itemId;
            ValueChecker.checkCategoryName(categoryName);
            this.categoryName = categoryName;
            ValueChecker.checkNegative(quantityTotal, "quantity");
            this.quantityTotal = quantityTotal;
            ValueChecker.checkNegative(price, "price");
            this.price = price;
            this.valid = true;
        } catch(RuntimeException e) {
            this.valid = false;
        }
        
    }

    /**
     * Creates an item with the specified parameters. Please note that quantityTotal will be set
     * to 0 by default.
     * 
     * @param itemId - Id of the item. Must not be negative.
     * @param categoryName - Must be either "Yarn" or "Knitting Needles". Must not be null.
     * @param price - Price of item. Must not be negative.
     */
    public Item(int itemId, String categoryName, double price) {
        this(itemId, categoryName, 0, price);
    }

    /**
     * Returns the id of the item
     * 
     * @return id of item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Sets the item's id to the parameter specified. Will invalidate item if parameter 
     * is negative.
     * 
     * @param itemId - Id of the item. Must not be negative.
     */
    public void setItemId(int itemId) {
        try {
            ValueChecker.checkNegative(itemId, "id");
            this.itemId = itemId;
        } catch(IllegalArgumentException e) {
            valid = false;
        }
    }

    /**
     * Returns the category of the item
     * 
     * @return category name of item
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the item's category to the specified parameter. Will invalidate item if
     * given a null or undefined category name.
     * 
     * @param categoryName - name of the item's category. Must not be null. Must be either "Yarn" or "Knitting Needles".
     */
    public void setCategoryName(String categoryName) {
        try {
            ValueChecker.checkCategoryName(categoryName);
            this.categoryName = categoryName;
        } catch(RuntimeException e) {
            valid = false;
        }
    }

    /**
     * Returns the total quantity of this item
     * 
     * @return total quantity of item
     */
    public int getQuantityTotal() {
        return quantityTotal;
    }

    /**
     * Sets the item's total qunatity to the parameter specified. Will invalidate item if 
     * parameter is negative.
     * 
     * @param quantityTotal - the total quantity of this item. Must not be negative.
     */
    public void setQuantityTotal(int quantityTotal) {
        try {
            ValueChecker.checkNegative(quantityTotal, "quantity");
            this.quantityTotal = quantityTotal;
        } catch(IllegalArgumentException e) {
            valid = false;
        }
    }

    /**
     * Returns the price of the item
     * 
     * @return price of item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the item to the parameter specified. Will invalidate the item if given a negative value.
     * 
     * @param price - price of the item. Must not be negative.
     */
    public void setPrice(double price) {
        try {
            ValueChecker.checkNegative(price, "price");
            this.price = price;
        } catch(IllegalArgumentException e) {
            valid = false;
        }
    }

    /**
     * Returns this item as a String.
     * 
     * @return string of items fields
     */
    @Override
    public String toString() {
        return "Item [categoryName=" + categoryName + ", itemId=" + itemId + ", price=" + price + ", quantityTotal="
                + quantityTotal + "]";
    }
    
    /**
     * Returns whether or not this item is a valid instance of item
     * 
     * @return boolean value denoting validity of object
     */
    public boolean isValid() {
        return valid;
    }
}
