package dev.tarico.daos;

import java.util.List;
import dev.tarico.models.Inventory;

/**
 * Interface that defines several methods to access data concerning the Inventory table.
 * Specific implementations should override these methods in concrete classes.
 * 
 * @author Tara Arico - 7.25.2022
 */
public interface InventoryDAO {
    public List<Inventory> findAll();
    public Inventory findById(int inventoryId);
    //find the list of inventories in a given location(i.e. the full inventory at that location)
    public List<Inventory> findInventoryAtLocation(int locationId);
    public boolean addInventory(Inventory inventory);
    public boolean updateQuantity(int inventoryId, int quantity);
    public boolean updateLocation(int inventoryId, int locationId);
    public boolean updateInventory(Inventory inventory);
    public boolean removeInventory(int invetoryId);   
    public List<Inventory> findItemsInInventory(int itemId);
    public boolean removeInventories(List<Inventory> inventories);
    public Inventory findItemAtLocation(int itemId, int locationId);
}
