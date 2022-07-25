package dev.tarico.daos;

import java.util.List;

import dev.tarico.conf.YarnDBConnection;
import dev.tarico.models.Location;
import dev.tarico.models.builders.LocationBuilder;
import java.sql.*;
import java.util.ArrayList;

/**
 * Retrieves data pertaining to the Location table from a MySQL backend database.
 * Implements LocationDAO.
 * 
 * @author Tara Arico - 7.25.2022
 */
public class LocationDAOImplementation implements LocationDAO {

    /**
     * Queries the Location table and retrieves every row.
     * 
     * @return an ArrayList of Location objects. Null if none found.
     */
    @Override
    public List<Location> findAll() {
        String query = "SELECT * FROM location";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);
            ArrayList<Location> locations = new ArrayList<Location>();
            while(results.next()) {
                LocationBuilder builder = new LocationBuilder().locationId(results.getInt("location_id")).currentCapacity(results.getInt("curr_capacity")).totalCapacity(results.getInt("total_capacity")).phoneNumber(results.getString("phone_number")).type(results.getString("type"));
                Location location = new Location(builder);
                locations.add(location);
            }
            return locations;
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Queries the Location table, returning the row with the specified locationId.
     * Returns null if no row matches the id given.
     * 
     * @param locationId - id of the location to search for.
     * @return Location object created from the row in the database. Null if not found.
     */
    @Override
    public Location findById(int locationId) {
        String query = "SELECT * FROM location WHERE location_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, locationId);
            ResultSet result = pstatement.executeQuery();
            if(result.next()) {
                LocationBuilder builder = new LocationBuilder().locationId(result.getInt("location_id")).currentCapacity(result.getInt("curr_capacity")).totalCapacity(result.getInt("total_capacity")).phoneNumber(result.getString("phone_number")).type(result.getString("type"));
                return new Location(builder);
            }
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Queries the Location table, returning rows that match the given type of Location.
     * 
     * @param type - String representation of the type of location to search for.
     * @return an ArrayList of locations returned by the query. Null if none found.
     */
    @Override
    public List<Location> findByType(String type) {
        String query = "SELECT * FROM location WHERE type = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setString(1, type);
            ResultSet result = pstatement.executeQuery();
            ArrayList<Location> locations = new ArrayList<Location>();
            while(result.next()) {
                LocationBuilder builder = new LocationBuilder().locationId(result.getInt("location_id")).currentCapacity(result.getInt("curr_capacity")).totalCapacity(result.getInt("total_capacity")).phoneNumber(result.getString("phone_number")).type(result.getString("type"));
                locations.add(new Location(builder));
            }
            return locations;
        } catch(SQLException e) {
            //silently fail for now
        }
        return null;
    }

    /**
     * Deletes the specified row from the Location table using the locationId.
     * 
     * @param locationId - id of the location to remove from the Location table.
     * @return true if successful, false otherwise.
     */
    @Override
    public boolean removeLocation(int locationId) {
        String query = "DELETE FROM location WHERE location_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, locationId);
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
     * Updates the current capacity of the specified row from the Location 
     * table using the locationId.
     * 
     * @param locationId - id of the Location to update.
     * @param currentCapacity - the capacity to set the current capacity to.
     * @return true if successfully updated row, false otherwise.
     */
    @Override
    public boolean updateCurrentCapacity(int locationId, int currentCapacity) {
        String query = "UPDATE location SET curr_capacity = ? WHERE location_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1, currentCapacity);
            pstatement.setInt(2, locationId);
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
     * Updates a row in the Location table using a Location object.
     * 
     * @param location - object to update the row with.
     * @return true if row was updated successfully, false otherwise
     */
    @Override
    public boolean updateLocation(Location location) {
        String query = "UPDATE location SET type = ?, curr_capacity = ?, total_capacity = ?, " +
                    "phone_number = ? WHERE location_id = ?";
        try(Connection conn = YarnDBConnection.getConnectionInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstatement.setString(1, location.getType());
            pstatement.setInt(2, location.getCurrentCapacity());
            pstatement.setInt(3, location.getTotalCapacity());
            pstatement.setString(4, location.getPhoneNumber());
            pstatement.setInt(5, location.getLocationId());
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
