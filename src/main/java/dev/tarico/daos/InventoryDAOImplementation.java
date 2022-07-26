package dev.tarico.daos;

import java.util.List;
import dev.tarico.models.Inventory;
import dev.tarico.conf.YarnDBConnection;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO that retrieves data pertaining to the Inventory table in the database.
 * Implements InventoryDAO.
 * 
 * @author Tara Arico - 7.26.2022
 */
public class InventoryDAOImplementation implements InventoryDAO {

    /**
     * Adds an Inventory object into the database.
     * 
     * @param inventory - Inventory object to insert into the Inventory table.
     * @return true if successfully added, false otherwise.
     */
    @Override
    public boolean addInventory(Inventory inventory) {
        String query = "INSERT INTO inventory VALUES(?, ?, ?, ?)";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, inventory.getInventoryId());
            pstatement.setInt(2, inventory.getLocationId());
            pstatement.setInt(3, inventory.getItemId());
            pstatement.setInt(4, inventory.getQuantity());
            int affectedRows = pstatement.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return false;
    }

    /**
     * Queries the database for a list of all Inventory objects.
     * 
     * @return list of Inventory objects. Null if none found.
     */
    @Override
    public List<Inventory> findAll() {
        String query = "SELECT * FROM inventory";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            ArrayList<Inventory> inventories = new ArrayList<Inventory>();
            while(results.next()) {
                Inventory inventory = new Inventory(results.getInt("inventory_id"), results.getInt("item_num"), results.getInt("locat_id"), results.getInt("quantity"));
                inventories.add(inventory);
            }
            return inventories;
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Queries the database for an Inventory object corresponding to the given id.
     * 
     * @param inventoryId - id to query the Inventory table for.
     * @return Inventory object created from the given row in the Inventory table. Null if not found. 
     */
    @Override
    public Inventory findById(int inventoryId) {
        String query = "SELECT * FROM inventory WHERE inventory_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, inventoryId);
            ResultSet result = pstatement.executeQuery();
            if(result.next()) {
                Inventory inventory = new Inventory(result.getInt("inventory_id"), result.getInt("item_num"), result.getInt("locat_id"), result.getInt("quantity"));
                return inventory;
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Queries the database for a list of Inventory objects at a given location.
     * 
     * @param locationId - location to query the Inventory table with.
     * @return list of Inventory objects at the given location. Null if not found. 
     */
    @Override
    public List<Inventory> findInventoryAtLocation(int locationId) {
        String query = "SELECT * FROM inventory WHERE locat_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, locationId);
            ResultSet result = pstatement.executeQuery();
            ArrayList<Inventory> inventories = new ArrayList<Inventory>();
            while(result.next()) {
                Inventory inventory = new Inventory(result.getInt("inventory_id"), result.getInt("item_num"), result.getInt("locat_id"), result.getInt("quantity"));
                inventories.add(inventory);
            }
            return inventories;
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Removes a row from the Inventory table that corresponds to the given id.
     * 
     * @param inventoryId - the id to query the correct row to remove
     * @return true if successful, false otherwise.
     */
    @Override
    public boolean removeInventory(int inventoryId) {
        String query = "DELETE FROM inventory WHERE inventory_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, inventoryId);
            int affectedRows = pstatement.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return false;
    }

    /**
     * Updates the location where the given inventory is located in the database.
     * 
     * @param inventoryId - id of the inventory to update.
     * @param locationId - new location where the Inventory object is stored.
     * @return true is successful, false otherwise.
     */
    @Override
    public boolean updateLocation(int inventoryId, int locationId) {
        String query = "UPDATE inventory SET locat_id = ? WHERE inventory_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, locationId);
            pstatement.setInt(2, inventoryId);
            int affectedRows = pstatement.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return false;
    }

    /**
     * Updates the quantity of items given the inventory id.
     * 
     * @param inventoryId - id of the inventory row to query.
     * @param quantity - new amount of items in this inventory.
     * @return true is successful, false otherwise.
     */
    @Override
    public boolean updateQuantity(int inventoryId, int quantity) {
        String query = "UPDATE inventory SET quantity = ? WHERE inventory_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, quantity);
            pstatement.setInt(2, inventoryId);
            int affectedRows = pstatement.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return false;
    }

    /**
     * Updates a row in the Inventory table using an Inventory object.
     * 
     * @param inventory - updated Inventory object to insert into table.
     * @return true is successful, false otherwise.
     */
    @Override
    public boolean updateInventory(Inventory inventory) {
        String query = "UPDATE inventory SET locat_id = ?, item_num = ?, quantity = ? " +
                "WHERE inventory_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, inventory.getLocationId());
            pstatement.setInt(2, inventory.getItemId());
            pstatement.setInt(3, inventory.getQuantity());
            pstatement.setInt(4, inventory.getInventoryId());
            int affectedRows = pstatement.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return false;
    }
    
}
