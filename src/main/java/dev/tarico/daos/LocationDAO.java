package dev.tarico.daos;

import java.util.List;
import dev.tarico.models.Location;

/**
 * Interface that defines several methods to access data concerning the Location table.
 * Specific implementations should override these methods in concrete classes.
 * 
 * @author Tara Arico - 7.25.2022
 */
public interface LocationDAO {
    public List<Location> findAll();
    public Location findById(int locationId);
    public List<Location> findByType(String type);
    public boolean updateCurrentCapacity(int locationId, int currentCapacity);
    public boolean updateLocation(Location location);
    public boolean removeLocation(int locationId);
}
