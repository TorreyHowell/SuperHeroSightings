package com.th.superherosightings.Service;

import com.th.superherosightings.entity.Faction;
import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Organization;
import com.th.superherosightings.entity.Power;
import com.th.superherosightings.entity.Siting;
import com.th.superherosightings.entity.SuperHuman;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface Service {

  List<Siting> readSitings();

  Power createPower(Power power);

  List<Power> readPowers();

  Power readPower(int id);

  void updatePower(Power power);

  void deletePower(int id);

  List<Faction> readFactions();

  Faction readFaction(int id);

  List<SuperHuman> readSupers();

  SuperHuman readSuper(int id);

  SuperHuman createSuper(SuperHuman superHuman, MultipartFile file);

  void updateSuper(SuperHuman superHuman, MultipartFile file);

  void deleteSuper(int id);

  List<Location> readLocations();

  Location readLocation(int id);

  Location createLocation(Location location);

  void updateLocation(Location location);

  void deleteLocation(int id);

  List<Siting> readSightings();

  Siting createSighting(Siting sighting);

  Siting readSighting(int id);

  void updateSighting(Siting siting);

  void deleteSighting(int id);

  List<Organization> readOrganizations();

  Organization readOrganization(int id);

  void deleteOrganization(int id);

  void updateOrganization(Organization org);

  Organization createOrganization(Organization org);

  List<Siting> sortSightings(LocalDate date);

  List<Siting> readRecentSightings();

  List<SuperHuman> readSupersForPower(Power power);

  List<Siting> readSightingsForSuper(SuperHuman superHuman);

  List<Organization> readOrganizationsForSuper(SuperHuman superHuman);

  List<Siting> readSightingsForLocation(Location location);
}
