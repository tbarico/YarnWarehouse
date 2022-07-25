package dev.tarico.models.builders;

import dev.tarico.models.Location;
import dev.tarico.models.helpers.ValueChecker;

/**
 * Helper class to Location. Builds a Location object modeled after the builder design pattern.
 * 
 * @author Tara Arico - 7.23.2022
 */
public class LocationBuilder {
    private int locationId;
    //private int addressId;
    private int currentCapacity;
    private int totalCapacity;
    //private int managerId;
    private String phoneNumber;
    private String type;

    /**
     * Creates a LocationBuilder object.
     */
    public LocationBuilder() {}

    /**
     * Sets the location id for this object and returns itself.
     * 
     * @param locationId - id of this location. Must not be negative.
     * @return the LocationBuilder object itself
     */
    public LocationBuilder locationId(int locationId) {
        try {
            ValueChecker.checkNegative(locationId, "id");
            this.locationId = locationId;
        } catch (IllegalArgumentException e) {
            // silently fail for now
        }
        return this;
    }

    // public LocationBuilder addressId(int addressId) {
    //     ValueChecker.checkId(addressId);
    //     this.addressId = addressId;
    //     return this;
    // }

    /**
     * Sets the current capacity of the LocationBuilder object and returns itself.
     * 
     * @param currentCapacity - current capacity of this object. Must not be negative.
     * @return the LocationBuilder object itself
     */
    public LocationBuilder currentCapacity(int currentCapacity) {
        try {
            ValueChecker.checkNegative(currentCapacity, "capacity");
            this.currentCapacity = currentCapacity;
        } catch (IllegalArgumentException e) {
            // silently fail for now
        }
        return this;
    }

    /**
     * Sets the total capacity for this object and returns itself.
     * 
     * @param totalCapacity - total capacity of this object. Must not be negative.
     * @return the LocationBuilder object itself
     */
    public LocationBuilder totalCapacity(int totalCapacity) {
        try {
            ValueChecker.checkNegative(totalCapacity, "capacity");
            this.totalCapacity = totalCapacity;
        } catch (IllegalArgumentException e) {
            // silently fail for now
        }
        return this;
    }

    // public LocationBuilder managerId(int managerId) {
    //     ValueChecker.checkId(managerId);
    //     this.managerId = managerId;
    //     return this;
    // }

    /**
     * Sets the phone number for this LocationBuilder object and returns itself.
     * 
     * @param phoneNumber - phone number of this object. Must not be null. Must be either an
     *          empty string("") or a string of 10 digits (no special characters or letters).
     * @return the LocationBuilder object itself
     */
    public LocationBuilder phoneNumber(String phoneNumber) {
        try {
            ValueChecker.checkPhoneNumber(phoneNumber);
            this.phoneNumber = phoneNumber;
        } catch (RuntimeException e) {
            // silently fail for now
        }
        return this;
    }

    /**
     * Sets the LocationBuilder's type and returns itself.
     * 
     * @param type - type of location. Must not be null. Must be either "Warehouse" or "Store".
     * @return the LocationBuilder object itself
     */
    public LocationBuilder type(String type) {
        type = type.trim();
        try {
            ValueChecker.checkLocationType(type);
            this.type = type;
        } catch (RuntimeException e) {
            // silently fail for now
        }
        return this;
    }

    /**
     * Builds a Location object using this object's fields.
     * 
     * @return a Location object
     */
    public Location build() {
        return new Location(this);
    }

    /**
     * Retrieves the location id of this LocationBuilder.
     * 
     * @return location id of this object
     */
    public int getLocationId() {
        return locationId;
    }

    // public int getAddressId() {
    //     return addressId;
    // }

    /**
     * Retrieves the current capacity of this LocationBuilder.
     * 
     * @return current capacity of this object
     */
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    /**
     * Retrieves the total capacity of this LocationBuilder.
     * 
     * @return total capacity this object can hold
     */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    // public int getManagerId() {
    //     return managerId;
    // }

    /**
     * Retrieves the phone number of this LocationBuilder.
     * 
     * @return phone number of this location
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Retrieves the LocationBuilder's location type. 
     * 
     * @return type of this location
     */
    public String getType() {
        return type;
    }    
}
