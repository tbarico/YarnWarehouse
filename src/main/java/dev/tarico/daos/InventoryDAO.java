package dev.tarico.daos;

import java.util.List;
import dev.tarico.models.Inventory;
import dev.tarico.models.Item;

/**
 * Interface that defines several methods to access data concerning the Inventory table.
 * Specific implementations should override these methods in concrete classes.
 * 
 * @author Tara Arico - 7.25.2022
 */
public interface InventoryDAO {
    public List<Inventory> findAll();
    public Inventory findById(int inventory_id);
    //find the list of items in a given location(i.e. the full inventory at that location)
    public List<Item> findItemsAtLocation(int locationId);
    public boolean addInventory(Inventory inventory);
    public boolean updateQuantity(int id, int quantity);
    public boolean updateLocation(int id, int locationId);
    public boolean updateInventory(Inventory inventory);
    public boolean removeInventory(int id);   
}