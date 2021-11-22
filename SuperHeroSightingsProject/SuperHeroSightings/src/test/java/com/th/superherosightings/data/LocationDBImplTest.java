package com.th.superherosightings.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Organization;
import com.th.superherosightings.entity.Power;
import com.th.superherosightings.entity.Siting;
import com.th.superherosightings.entity.SuperHuman;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class LocationDBImplTest {

  @Autowired
  JdbcTemplate jdbc;

  @Autowired
  FactionDB factionDB;

  @Autowired
  LocationDB locationDB;

  @Autowired
  OrganizationDB organizationDB;

  @Autowired
  PowerDB powerDB;

  @Autowired
  SitingDB sitingDB;

  @Autowired
  SuperHumanDB superHumanDB;

  @BeforeEach
  void setUp() {

    final String UNCHECK = "SET FOREIGN_KEY_CHECKS = 0;";
    final String DELETE_LOCATION = "TRUNCATE TABLE Location";
    final String DELETE_POWER = "TRUNCATE TABLE Power";
    final String DELETE_ORGANIZATION = "TRUNCATE TABLE Organization";
    final String DELETE_SUPER = "TRUNCATE TABLE Super";
    final String DELETE_SUPER_ORG = "TRUNCATE TABLE SuperOrganization";
    final String DELETE_POWER_SUPER = "TRUNCATE TABLE PowerSuper";
    final String DELETE_SITING = "TRUNCATE TABLE Siting";
    final String CHECK = "SET FOREIGN_KEY_CHECKS = 1;";

    jdbc.update(UNCHECK);
    jdbc.update(DELETE_SITING);
    jdbc.update(DELETE_POWER_SUPER);
    jdbc.update(DELETE_SUPER_ORG);
    jdbc.update(DELETE_SUPER);
    jdbc.update(DELETE_ORGANIZATION);
    jdbc.update(DELETE_POWER);
    jdbc.update(DELETE_LOCATION);
    jdbc.update(CHECK);


  }

  @AfterEach
  void tearDown() {
  }

  @Test
  public void testAddGetLocation() {
    Location location = new Location();
    location.setCountry("Test Country");
    location.setName("Test Name");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));

    location = locationDB.addLocation(location);
    Location fromDB = locationDB.getLocationById(location.getId());

    assertEquals(location, fromDB);
  }

  @Test
  public void testGetAllLocation() {
    Location location = new Location();
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setName("Test Name");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));

    locationDB.addLocation(location);

    Location location2 = new Location();
    location2.setCountry("Test Country Two");
    location2.setZipcode("11111");
    location2.setName("Test Name");
    location2.setAddress("Test Address");
    location2.setCity("Test City");
    location2.setState("OH");
    location2.setLongitude(new BigDecimal("-81.681291"));
    location2.setLatitude(new BigDecimal("41.505491"));

    locationDB.addLocation(location2);

    List<Location> locations = locationDB.getAllLocations();

    assertEquals(locations.size(), 2);
    assertTrue(locations.contains(location));
    assertTrue(locations.contains(location2));

  }

  @Test
  public void testUpdateLocation() {

    Location location = new Location();
    location.setName("TestName");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));

    location = locationDB.addLocation(location);
    Location fromDB = locationDB.getLocationById(location.getId());
    assertEquals(location, fromDB);

    location.setCountry("Test Country 2");
    location.setName("Test Name 2");
    location.setZipcode("11112");
    location.setAddress("Test Address 2");
    location.setCity("Test City 2");
    location.setState("MA");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));

    locationDB.editLocation(location);

    Location updated = locationDB.getLocationById(location.getId());
    assertNotEquals(updated, fromDB);
    assertEquals(location, updated);

  }

  @Test
  public void testDeleteLocation() {

    Organization org = new Organization();
    org.setEmail("Test Email");
    org.setPhone("440-596-9586");
    org.setName("Test Name");
    org.setFaction(factionDB.getFactionById(1));

    Location location = new Location();
    location.setName("TestName");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));

    location = locationDB.addLocation(location);

    org.setLocation(location);

    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(factionDB.getFactionById(1));
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    SuperHuman testSuper2 = new SuperHuman();
    testSuper2.setPower(powerList);
    testSuper2.setFaction(factionDB.getFactionById(1));
    testSuper2.setName("Test Name");
    testSuper2.setDescription("Test Description");
    testSuper2 = superHumanDB.addSuper(testSuper2);

    List<SuperHuman> sprList = new ArrayList<>();
    sprList.add(testSuper);
    sprList.add(testSuper2);
    org.setSuperHumans(sprList);

    Siting testSighting = new Siting();
    testSighting.setLocation(location);
    testSighting.setSuperHuman(testSuper2);
    testSighting.setDate(LocalDate.now());
    testSighting.setDescription("Test");
    testSighting = sitingDB.addSiting(testSighting);

    org = organizationDB.addOrg(org);

    locationDB.removeLocation(location.getId());
    assertNull(locationDB.getLocationById(location.getId()));
    assertNull(sitingDB.getSitingById(testSighting.getId()));
    assertNull(organizationDB.getOrgById(org.getId()));

  }

}