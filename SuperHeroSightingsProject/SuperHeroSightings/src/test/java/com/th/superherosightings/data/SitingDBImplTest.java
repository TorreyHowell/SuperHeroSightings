package com.th.superherosightings.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.th.superherosightings.entity.Faction;
import com.th.superherosightings.entity.Location;
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
class SitingDBImplTest {

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
  public void testAddGetSiting() {
    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.now());
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);

    Siting fromDB = sitingDB.getSitingById(siting.getId());

    assertEquals(siting, fromDB);

  }

  @Test
  public void testGetAllSitings() {

    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.now());
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);

    Location location2 = new Location();
    location2.setName("Test Name");
    location2.setCountry("Test Country");
    location2.setZipcode("11111");
    location2.setAddress("Test Address");
    location2.setCity("Test City");
    location2.setState("OH");
    location2.setLongitude(new BigDecimal("-81.681290"));
    location2.setLatitude(new BigDecimal("41.505493"));
    location2 = locationDB.addLocation(location2);

    Siting siting2 = new Siting();
    siting2.setDate(LocalDate.now());
    siting2.setLocation(location2);
    siting2.setSuperHuman(testSuper);
    siting2.setDescription("Test Description");

    siting2 = sitingDB.addSiting(siting2);

    List<Siting> sitings = sitingDB.getAllSitings();

    assertEquals(sitings.size(), 2);
    assertTrue(sitings.contains(siting));
    assertTrue(sitings.contains(siting2));

  }

  @Test
  public void testUpdateSiting() {
    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.now());
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);
    Siting fromDB = sitingDB.getSitingById(siting.getId());

    Faction testFaction2 = factionDB.getFactionById(2);
    Power testPower2 = new Power();
    testPower2.setId(testSuper.getSuperId());
    testPower2.setName("Test Power 2");
    testPower2.setDescription("Test Description 2");
    testPower2 = powerDB.addPower(testPower2);
    List<Power> powerList2 = new ArrayList<>();
    powerList2.add(testPower2);

    SuperHuman testSuper2 = new SuperHuman();
    testSuper2.setSuperId(testSuper.getSuperId());
    testSuper2.setPower(powerList2);
    testSuper2.setFaction(testFaction2);
    testSuper2.setName("Test Name 2");
    testSuper2.setDescription("Test Description 2");
    superHumanDB.addSuper(testSuper2);

    siting.setSuperHuman(testSuper2);
    siting.setDate(LocalDate.now().minusDays(1));
    Location location2 = new Location();
    location2.setName("Test Name");
    location2.setCountry("Test Country");
    location2.setZipcode("11111");
    location2.setAddress("Test Address");
    location2.setCity("Test City");
    location2.setState("OH");
    location2.setLongitude(new BigDecimal("-81.681290"));
    location2.setLatitude(new BigDecimal("41.505493"));
    location2 = locationDB.addLocation(location2);
    siting.setLocation(location2);
    siting.setDescription("Updated Test Description");

    sitingDB.editSiting(siting);
    Siting updated = sitingDB.getSitingById(siting.getId());

    assertNotEquals(fromDB, updated);
    assertEquals(updated.getLocation(), location2);

  }

  @Test
  public void testDeleteSiting() {

    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.now());
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);
    assertNotNull(siting);

    sitingDB.removeSiting(siting.getId());
    assertNull(sitingDB.getSitingById(siting.getId()));

  }

  @Test
  public void testGetSitingsForSuper() {
    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.now());
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);

    List<Siting> sitings = sitingDB.getSitingsForSuper(testSuper);

    assertTrue(sitings.contains(siting));
  }

  @Test
  public void testGetSitingsForLocation() {
    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.now());
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);

    Location location2 = new Location();
    location2.setName("Test Name");
    location2.setCountry("Test Country");
    location2.setZipcode("11111");
    location2.setAddress("Test Address");
    location2.setCity("Test City");
    location2.setState("OH");
    location2.setLongitude(new BigDecimal("-81.681290"));
    location2.setLatitude(new BigDecimal("41.505493"));
    location2 = locationDB.addLocation(location2);

    Siting siting2 = new Siting();
    siting2.setDate(LocalDate.now());
    siting2.setLocation(location2);
    siting2.setSuperHuman(testSuper);
    siting2.setDescription("Test Description");

    siting2 = sitingDB.addSiting(siting2);

    List<Siting> sitings = sitingDB.getSitingsForLocation(location);
    assertTrue(sitings.contains(siting));

    List<Siting> sitings2 = sitingDB.getSitingsForLocation(location2);
    assertTrue(sitings2.contains(siting2));


  }

  @Test
  public void testGetSitingsForDate() {
    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.parse("2021-06-26"));
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);

    List<Siting> sitings = sitingDB.getSitingsForDate(LocalDate.parse("2021-06-26"));

    assertTrue(sitings.contains(siting));
  }

  @Test
  public void testGetRecentSightings(){

    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    location = locationDB.addLocation(location);

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);
    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setFaction(testFaction);
    testSuper.setName("Test Name");
    testSuper.setDescription("Test Description");
    testSuper = superHumanDB.addSuper(testSuper);

    Siting siting = new Siting();
    siting.setDate(LocalDate.parse("2014-01-01"));
    siting.setLocation(location);
    siting.setSuperHuman(testSuper);
    siting.setDescription("Test Description");

    siting = sitingDB.addSiting(siting);

    Siting siting2 = new Siting();
    siting2.setDate(LocalDate.parse("2013-01-01"));
    siting2.setLocation(location);
    siting2.setSuperHuman(testSuper);
    siting2.setDescription("Test Description");

    siting2 = sitingDB.addSiting(siting2);

    Siting siting3 = new Siting();
    siting3.setDate(LocalDate.parse("2015-01-01"));
    siting3.setLocation(location);
    siting3.setSuperHuman(testSuper);
    siting3.setDescription("Test Description");

    siting3 = sitingDB.addSiting(siting3);

    List<Siting> sortedList = sitingDB.getRecentSitings();

    assertEquals(siting3 , sortedList.get(0));
    assertEquals(siting, sortedList.get(1));
    assertEquals(siting2, sortedList.get(2));

  }

}