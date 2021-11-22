package com.th.superherosightings.data;

import com.th.superherosightings.entity.Location;
import java.util.List;

public interface LocationDB {

  Location getLocationById(int id);

  List<Location> getAllLocations();

  Location addLocation(Location location);

  void removeLocation(int id);

  void editLocation(Location location);

}
