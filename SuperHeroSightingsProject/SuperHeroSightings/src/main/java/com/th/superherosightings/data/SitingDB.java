package com.th.superherosightings.data;

import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Siting;
import com.th.superherosightings.entity.SuperHuman;
import java.time.LocalDate;
import java.util.List;

public interface SitingDB {

  Siting getSitingById(int id);

  List<Siting> getAllSitings();

  Siting addSiting(Siting siting);

  void editSiting(Siting siting);

  void removeSiting(int id);

  List<Siting> getSitingsForSuper(SuperHuman superHuman);

  List<Siting> getSitingsForLocation(Location location);

  List<Siting> getSitingsForDate(LocalDate date);

  List<Siting> getRecentSitings();

}
