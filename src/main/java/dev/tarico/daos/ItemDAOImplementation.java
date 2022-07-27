package dev.tarico.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dev.tarico.conf.YarnDBConnection;
import dev.tarico.models.Item;

/**
 * Retrieves data pertaining to the Item table from a MySQL backend database.
 * Implements ItemDAO.
 * 
 * @author Tara Arico - 7.27.2022
 */
public class ItemDAOImplementation implements ItemDAO {

    /**
     * Adds a row to the Item table using an Item object.
     * 
     * @param item - item to add.
     * @return true if insertion was successful, false otherwise.
     */
    @Override
    public boolean addItem(Item item) {
        String query = "INSERT INTO item VALUES(?, ?, ?, ?)";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, item.getItemId());
            pstatement.setString(2, item.getCategoryName());
            pstatement.setDouble(3, item.getPrice());
            pstatement.setInt(4, item.getQuantityTotal());
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
     * Retrieves the data from the Item table and returns a list of Item objects.
     * 
     * @return list of Items corresponding to rows in the Item table.
     */
    @Override
    public List<Item> findAll() {
        String query = "SELECT * FROM item";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            ArrayList<Item> items = new ArrayList<Item>();
            while(results.next()) {
                Item item = new Item(results.getInt("item_id"), results.getString("category_name"), results.getInt("total_quantity"), results.getDouble("price"));
                items.add(item);
            }
            return items;
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Retrieves all Items that match the specified category provided.
     * 
     * @param name - category name to match on.
     * @return list of Items that match the given category.
     */
    @Override
    public List<Item> findByCategoryName(String name) {
        String query = "SELECT * FROM item WHERE category_name = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setString(1, name);
            ResultSet results = pstatement.executeQuery();
            ArrayList<Item> items = new ArrayList<Item>();
            while(results.next()) {
                Item item = new Item(results.getInt("item_id"), results.getString("category_name"), results.getInt("total_quantity"), results.getDouble("price"));
                items.add(item);
            }
            return items;
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Retrieves a single row from the Item table given the item's id.
     * 
     * @param itemId - id of the item to query for.
     * @return Item object that corresponds to the retrieved row in the Item table.
     */
    @Override
    public Item findById(int itemId) {
        String query = "SELECT * FROM item WHERE item_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, itemId);
            ResultSet result = pstatement.executeQuery();
            if(result.next()) {
                Item item = new Item(result.getInt("item_id"), result.getString("category_name"), result.getInt("total_quantity"), result.getDouble("price"));
                return item;
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Removes a row from the Item table given the item id. Please note that calling
     * this method will also remove item references in the Inventory table.
     * 
     * @param itemId - id of the item to be removed.
     * @return true if deletion was successful, false otherwise.
     */
    @Override
    public boolean removeItem(int itemId) {
        //Need to first find and remove any references to this itemId from the Inventory table
        InventoryDAOImplementation inventoryDAO = new InventoryDAOImplementation();
        if (inventoryDAO.removeInventories(inventoryDAO.findItemsInInventory(itemId))) {
            String query = "DELETE FROM item WHERE item_id = ?";
            try (Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
                conn.setAutoCommit(false);
                PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstatement.setInt(1, itemId);
                int affectedRows = pstatement.executeUpdate();
                if (affectedRows == 1) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                // silently fail for now
            }
            return false;
        }
        return false;
    }

    /**
     * Updates the price of the item in the Item table.
     * 
     * @param itemId - id of the item to be updated.
     * @param price - price to set the item to.
     * @return true if update was successful, false otherwise.
     */
    @Override
    public boolean updatePrice(int itemId, double price) {
        String query = "UPDATE item SET price = ? WHERE item_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setDouble(1, price);
            pstatement.setInt(2, itemId);
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
     * Updates the quantity of the item in the Item table.
     * 
     * @param itemId - id of the item to be updated.
     * @param quantity - amount to set the item to.
     * @return true if update was successful, false otherwise.
     */
    @Override
    public boolean updateQuantity(int itemId, int quantity) {
        String query = "UPDATE item SET total_quantity = ? WHERE item_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, quantity);
            pstatement.setInt(2, itemId);
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
     * Updates a row in the Item table using an Item object.
     * 
     * @param item - Item object to insert into the database.
     * @return true if update was successful, false otherwise.
     */
    @Override
    public boolean updateItem(Item item) {
        String query = "UPDATE item SET category_name = ?, price = ?, total_quantity = ? WHERE item_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setString(1, item.getCategoryName());
            pstatement.setDouble(2, item.getPrice());
            pstatement.setInt(3, item.getQuantityTotal());
            pstatement.setInt(4, item.getItemId());
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
