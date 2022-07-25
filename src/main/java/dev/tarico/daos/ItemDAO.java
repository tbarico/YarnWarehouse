package dev.tarico.daos;

import java.util.List; 
import dev.tarico.models.Item;

/**
 * Interface that defines several methods to access data concerning the Item table.
 * Specific implementations should override these methods in concrete classes.
 * 
 * @author Tara Arico - 7.25.2022
 */
public interface ItemDAO {
    public List<Item> findAll();
    public Item findById(int id);
    public List<Item> findByCategoryName(String name);
    public boolean addItem(Item item);
    public boolean updatePrice(int id, double price);
    public boolean updateQuantity(int id, int quantity);
    public boolean updateItem(Item item);
    public boolean removeItem(int id);
}