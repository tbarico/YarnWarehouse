package dev.tarico.services;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Helper class that validates a given input.
 * 
 * @author Tara Arico 7.22.2022
 */
public class ValueChecker {
    /**
     * Creates a ValueChecker object.
     */
    public ValueChecker() {}

    /**
     * Checks the given value to ensure its not negative. If so, throws an 
     * IllegalArgumentException with the appropiate message.
     * 
     * @param num - number to check. Must not be negative
     * @param field - name of the field being checked
     * @throws IllegalArgumentException - if the num is negative
     */
    public static void checkNegative(double num, String field) {
        if(num < 0) {
            String message;
            if(field == null) field = "";
            switch(field.toLowerCase()) {
                case "id": message = "Id must not be negative.";
                        break;
                case "quantity": message = "Quantity amount cannot be negative.";
                        break;
                case "capacity": message = "Capacity cannot be below 0.";
                        break;
                case "price": message = "Must enter a postive price number.";
                        break;
                default: message = "Invalid argument.";
                        break;
            }
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates the passed string against a number in the dddddddddd format.
     * Must not be null and only contain digits 0-9. Empty strings are allowed.
     * 
     * @param number - Must be a valid string in the correct format. Must not be null.
     * @throws IllegalArgumentException - should the string not be in the correct format
     * @throws NullPointerException - if method is passed a null argument
     */
    public static void checkPhoneNumber(String number) {
        if(number != null) {
            if(number.length() != 0) {
                String phoneFormat = "([0-9]{10})";
                Pattern pat = Pattern.compile(phoneFormat);
                Matcher mat = pat.matcher(number);
                if(!mat.matches())
                    throw new IllegalArgumentException("Invalid Phone number.");
            }
        } else
            throw new NullPointerException("Phone number must not be null.");
    }

    /**
     * Validates the given string against a set list of acceptable loaction types.
     * Must not be null. Must be either "Warehouse" or "Store"
     * 
     * @param type - type to check against
     * @throws IllegalArgumentException - thrown if not given a specific location type
     * @throws NullPointerExpection - thrown if given a null value
     */
    public static void checkLocationType(String type) {
        if(type != null) {
            if(!type.equals("Warehouse") && !type.equals("Store"))
                throw new IllegalArgumentException("Invalid location type.");
        } else
            throw new NullPointerException("Type must not be null.");
    }

    /**
     * Validates the given string against a set list of acceptable category names. Must not
     * be null. Must be either "Yarn" or "Knitting Needles".
     * 
     * @param name - name to check against
     * @throws IllegalArgumentException - thrown if not given a specific category name
     * @throws NullPointerException - thrown if given a null value
     */
    public static void checkCategoryName(String name){
        if(name != null) {
            if(!name.equals("Yarn") && !name.equals("Knitting Needles"))
                throw new IllegalArgumentException("Invalid category name.");
        } else
            throw new NullPointerException("Category name must not be null.");
    }

    public static void checkLocationQuantity(int amountToAdd, int existingAmount, int totalCapacity) {
        if((amountToAdd + existingAmount) > totalCapacity) {
            throw new RuntimeException("Amount exceeds location's total capacity.");
        }

    }
}
