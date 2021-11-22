package com.th.superherosightings.Service;

import com.th.superherosightings.data.FactionDB;
import com.th.superherosightings.data.ImageDB;
import com.th.superherosightings.data.LocationDB;
import com.th.superherosightings.data.OrganizationDB;
import com.th.superherosightings.data.PowerDB;
import com.th.superherosightings.data.SitingDB;
import com.th.superherosightings.data.SuperHumanDB;
import com.th.superherosightings.entity.Faction;
import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Organization;
import com.th.superherosightings.entity.Power;
import com.th.superherosightings.entity.Siting;
import com.th.superherosightings.entity.SuperHuman;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

  SitingDB sitingDB;
  PowerDB powerDB;
  FactionDB factionDB;
  LocationDB locationDB;
  OrganizationDB organizationDB;
  SuperHumanDB superHumanDB;
  ImageDB imageDB;


  public ServiceImpl(SitingDB sitingDB, PowerDB powerDB,
      FactionDB factionDB, LocationDB locationDB,
      OrganizationDB organizationDB, SuperHumanDB superHumanDB,
      ImageDB imageDB) {
    this.sitingDB = sitingDB;
    this.powerDB = powerDB;
    this.factionDB = factionDB;
    this.locationDB = locationDB;
    this.organizationDB = organizationDB;
    this.superHumanDB = superHumanDB;
    this.imageDB = imageDB;
  }

  @Override
  public List<Siting> readSitings() {
    return sitingDB.getAllSitings();
  }

  @Override
  public List<Power> readPowers() {
    return powerDB.getAllPowers();
  }

  @Override
  public Power createPower(Power power) {
    return powerDB.addPower(power);
  }

  @Override
  public Power readPower(int id) {
    return powerDB.getPowerById(id);
  }

  @Override
  public void deletePower(int id) {
    powerDB.removePower(id);
  }

  @Override
  public void updatePower(Power power) {
    powerDB.editPower(power);
  }

  @Override
  public List<Faction> readFactions() {
    return factionDB.getAllFactions();
  }

  @Override
  public Faction readFaction(int id) {
    return factionDB.getFactionById(id);
  }

  @Override
  public List<SuperHuman> readSupers() {
    return superHumanDB.getAllSupers();
  }

  @Override
  public SuperHuman readSuper(int id) {
    return superHumanDB.getSuperById(id);
  }

  @Override
  public SuperHuman createSuper(SuperHuman superHuman, MultipartFile file) {
    superHuman.setPhotoPath(this.saveImageReturnFileName(superHuman, file));
    return superHumanDB.addSuper(superHuman);
  }

  @Override
  public void updateSuper(SuperHuman superHuman, MultipartFile file) {
    if(superHuman.getPhotoPath().equals("Hero") || superHuman.getPhotoPath().equals("Villain")){
     superHuman.setPhotoPath(this.saveImageReturnFileName(superHuman, file));
    } else {
      this.updateImage(superHuman, file);
    }
    superHumanDB.editSuper(superHuman);
  }

  @Override
  public void deleteSuper(int id) {
    SuperHuman superHuman = this.readSuper(id);
    if(!superHuman.getPhotoPath().equals("Hero") && !superHuman.getPhotoPath().equals("Villain")){
      imageDB.deleteImage(superHuman.getPhotoPath());
    }

    superHumanDB.removeSuperById(id);
  }

  @Override
  public List<Location> readLocations() {
    return locationDB.getAllLocations();
  }

  @Override
  public Location readLocation(int id) {
    return locationDB.getLocationById(id);
  }

  @Override
  public Location createLocation(Location location) {
    return locationDB.addLocation(location);
  }

  @Override
  public void updateLocation(Location location) {
    locationDB.editLocation(location);
  }

  @Override
  public void deleteLocation(int id) {
    locationDB.removeLocation(id);
  }

  @Override
  public List<Siting> readSightings() {
    return sitingDB.getAllSitings();
  }

  @Override
  public Siting createSighting(Siting sighting) {
    return sitingDB.addSiting(sighting);
  }

  @Override
  public Siting readSighting(int id) {
    return sitingDB.getSitingById(id);
  }

  @Override
  public void updateSighting(Siting siting) {
    sitingDB.editSiting(siting);
  }

  @Override
  public void deleteSighting(int id) {
    sitingDB.removeSiting(id);
  }

  @Override
  public List<Organization> readOrganizations() {
    return organizationDB.getAllOrg();
  }

  @Override
  public Organization createOrganization(Organization org) {
    return organizationDB.addOrg(org);
  }

  @Override
  public List<Siting> sortSightings(LocalDate date) {
    return sitingDB.getSitingsForDate(date);
  }

  @Override
  public Organization readOrganization(int id) {
    return organizationDB.getOrgById(id);
  }

  @Override
  public void deleteOrganization(int id) {
    organizationDB.removeOrg(id);
  }

  @Override
  public void updateOrganization(Organization org) {
    organizationDB.editOrg(org);
  }

  @Override
  public List<Siting> readRecentSightings() {
    return sitingDB.getRecentSitings();
  }

  @Override
  public List<SuperHuman> readSupersForPower(Power power) {
    return superHumanDB.getSupersForPower(power);
  }

  @Override
  public List<Siting> readSightingsForSuper(SuperHuman superHuman) {
    return sitingDB.getSitingsForSuper(superHuman);
  }

  @Override
  public List<Organization> readOrganizationsForSuper(SuperHuman superHuman) {
    return organizationDB.getOrgForSuper(superHuman);
  }

  @Override
  public List<Siting> readSightingsForLocation(Location location) {
    return sitingDB.getSitingsForLocation(location);
  }

  private String saveImageReturnFileName(SuperHuman superHuman, MultipartFile file){
    String type = file.getContentType();

    if (type != null && type.split("/")[0].equals("image")){
      String fileName = ("" + superHuman.getName() + System.currentTimeMillis()).hashCode() + "";
      if(imageDB.saveImage(file, fileName)){
        return fileName;
      }
    }

    return superHuman.getFaction().getName();
  }

  private void updateImage(SuperHuman superHuman, MultipartFile file){
    String type = file.getContentType();
    if (type != null && type.split("/")[0].equals("image")){
      imageDB.updateImage(file, superHuman.getPhotoPath());
    }
  }
}


