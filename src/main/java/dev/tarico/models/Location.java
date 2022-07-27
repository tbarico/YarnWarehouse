package dev.tarico.models;

import dev.tarico.models.builders.LocationBuilder;
import dev.tarico.services.ValueChecker;

/**
 * Representation of a physical location (either a Store or Warehouse) where items are 
 * stored.
 * 
 * @author Tara Arico - 7.23.2022
 */
public class Location {
    private int locationId;
    //private int addressId;
    private int currentCapacity;
    private int totalCapacity;
    //private int managerId;
    private String phoneNumber;
    private String type;
    private boolean valid;

    /**
     * Creates a Location object using a LocationBuilder.
     * 
     * @param builder - LocationBuilder object with the neccessary fields
     */
    public Location(LocationBuilder builder) {
        try {
            ValueChecker.checkNegative(builder.getLocationId(), "id");
            this.locationId = builder.getLocationId();
            // ValueChecker.checkNegative(builder.getAddressId(), "id");
            // this.addressId = builder.getAddressId();

            ValueChecker.checkNegative(builder.getCurrentCapacity(), "capacity");
            this.currentCapacity = builder.getCurrentCapacity();
            ValueChecker.checkNegative(builder.getTotalCapacity(), "capacity");
            this.totalCapacity = builder.getTotalCapacity();

            // ValueChecker.checkNegative(builder.getManagerId(), "id");
            // this.managerId = builder.getManagerId();

            ValueChecker.checkPhoneNumber(builder.getPhoneNumber());
            this.phoneNumber = builder.getPhoneNumber();
            ValueChecker.checkLocationType(builder.getType());
            this.type = builder.getType();
            this.valid = true;
        } catch (RuntimeException e) {
            this.valid = false;
        }
    }

    /**
     * Creats a Location object with the parameters specified. Both the Location's phone number
     * and current capacity will be set to ""(an empty string) and 0 by default.
     * 
     * @param locationId - id of this location. Must not be negative.
     * @param totalCapactiy - total item capacity this location can hold. Must not be negative.
     * @param type - location type. Must not be null. Must be either "Warehouse" or "Store".
     */
    public Location(int locationId, int totalCapacity, String type) {
        this(new LocationBuilder().locationId(locationId).totalCapacity(totalCapacity).type(type).currentCapacity(0).phoneNumber(""));
    }

    /**
     * Creats a Location object with the parameters specified. Location's phone number will be
     * set to ""(an empty string) by default.
     * 
     * @param locationId - id of this location. Must not be negative.
     * @param currentCapacity - current capacity at this location. Must not be negative.
     * @param totalCapactiy - total item capacity this location can hold. Must not be negative.
     * @param type - location type. Must not be null. Must be either "Warehouse" or "Store".
     */
    public Location(int locationId, int currentCapacity, int totalCapacity, String type) {
        this(new LocationBuilder().locationId(locationId).totalCapacity(totalCapacity).type(type).currentCapacity(currentCapacity).phoneNumber(""));
    }

    /**
     * Creats a Location object with the parameters specified. Location's current capacity 
     * will be set to 0 by default.
     * 
     * @param locationId - id of this location. Must not be negative.
     * @param totalCapactiy - total item capacity this location can hold. Must not be negative.
     * @param phoneNumer - phone number at this location. Must not be null. Must be either an
     *          empty string or a string of 10 digits (no special characters or letters).
     * @param type - location type. Must not be null. Must be either "Warehouse" or "Store".
     */
    public Location(int locationId, int totalCapacity, String phoneNumber, String type) {
        this(new LocationBuilder().locationId(locationId).totalCapacity(totalCapacity).type(type).currentCapacity(0).phoneNumber(phoneNumber));
    }

    /**
     * Creats a Location object with the parameters specified. 
     * 
     * @param locationId - id of this location. Must not be negative.
     * @param currentCapacity - current capacity at this location. Must not be negative.
     * @param totalCapactiy - total item capacity this location can hold. Must not be negative.
     * @param phoneNumer - phone number at this location. Must not be null. Must be either an
     *          empty string or a string of 10 digits (no special characters or letters).
     * @param type - location type. Must not be null. Must be either "Warehouse" or "Store".
     */
    public Location(int locationId, int currentCapacity, int totalCapacity, String phoneNumber, String type) {
        this(new LocationBuilder().locationId(locationId).totalCapacity(totalCapacity).type(type).currentCapacity(currentCapacity).phoneNumber(phoneNumber));
    }

    /**
     * Retrieves the id of this Location.
     * 
     * @return id of this location object
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Sets the location id to the specified parameter.
     * 
     * @params locationId - id to be set. Must not be negative.
     */
    public void setLocationId(int locationId) {
        try {
            ValueChecker.checkNegative(locationId, "id");
            this.locationId = locationId;
        } catch (IllegalArgumentException e) {
            // silently fail for now
        }
    }

    // public int getAddressId() {
    //     return addressId;
    // }

    // public void setAddressId(int addressId) {
    //     ValueChecker.checkNegative(addressId, "id");
    //     this.addressId = addressId;
    // }

    /**
     * Retrieves the current amount of items at this Location.
     * 
     * @return current capacity of location object
     */
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    /**
     * Updates the Location's current capacity to the parameter specified.
     * 
     * @param currentCapacity - updated current capacity at this location. Must not be negative.
     */
    public void setCurrentCapacity(int currentCapacity) {
        try {
            ValueChecker.checkNegative(currentCapacity, "capacity");
            this.currentCapacity = currentCapacity;
        } catch (IllegalArgumentException e) {
            // silently fail for now
        }
    }

    /**
     * Retrieves the total capacity this Location can hold.
     * 
     * @return total capacity of this location object
     */
    public int getTotalCapacity() {
        return totalCapacity;
    }

    /**
     * Sets the total capacity this Location can hold.
     * 
     * @params totalCapacity - total capacity at this location. Must not be negative.
     */
    public void setTotalCapacity(int totalCapacity) {
        try {
            ValueChecker.checkNegative(totalCapacity, "capacity");
            this.totalCapacity = totalCapacity;
        } catch (IllegalArgumentException e) {
            // silently fail for now
        }
    }

    // public int getManagerId() {
    //     return managerId;
    // }

    // public void setManagerId(int managerId) {
    //     ValueChecker.checkNegative(managerId, "id");
    //     this.managerId = managerId;
    // }

    /**
     * Retrieves the phone number on record for this Location.
     * 
     * @return phone number of this location object
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number at this Location to the one specified.
     * 
     * @param phoneNumber - Location's phone number. Must not be null. Must be either
     *          an empty string("") or a string 10 digits long(no special characters or letters).
     */
    public void setPhoneNumber(String phoneNumber) {
        try {
            ValueChecker.checkPhoneNumber(phoneNumber);
            this.phoneNumber = phoneNumber;
        } catch (RuntimeException e) {
            // silently fail for now
        }
    }

    /**
     * Retrieves the type of location this Location object is.
     * 
     * @return location type of this object
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of this Location object.
     * 
     * @param type - location's type. Must not be null. Must be either "Warehouse" or "Store".
     */
    public void setType(String type) {
        try {
            ValueChecker.checkLocationType(type);
            this.type = type;
        } catch (RuntimeException e) {
            // silently fail for now
        }
    }

    /**
     * Retruns whether or not this Location object is valid.
     * 
     * @return boolean value denoting validity of this object
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns this Location object as a String.
     * 
     * @return String of this object
     */
    @Override
    public String toString() {
        return "Location [currentCapacity=" + currentCapacity + ", locationId=" + locationId + ", phoneNumber="
                + phoneNumber + ", totalCapacity=" + totalCapacity + ", type=" + type + ", valid=" + valid + "]";
    }
}
