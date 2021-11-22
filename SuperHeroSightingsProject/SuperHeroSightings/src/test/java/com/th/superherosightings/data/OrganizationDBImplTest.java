package com.th.superherosightings.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Organization;
import com.th.superherosightings.entity.Power;
import com.th.superherosightings.entity.SuperHuman;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class OrganizationDBImplTest {

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
  public void testAddGetOrg() {
    Organization org = new Organization();
    org.setEmail("Test Email");
    org.setPhone("440-596-9586");
    org.setName("Test Name");
    org.setFaction(factionDB.getFactionById(1));

    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    org.setLocation(locationDB.addLocation(location));

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

    org = organizationDB.addOrg(org);
    Organization fromDB = organizationDB.getOrgById(org.getId());

    assertEquals(org, fromDB);

  }

  @Test
  public void testGetAllOrg() {
    Organization org = new Organization();
    org.setEmail("Test Email");
    org.setPhone("440-596-9586");
    org.setName("Test Name");
    org.setFaction(factionDB.getFactionById(1));

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
    List<SuperHuman> sprList = new ArrayList<>();
    sprList.add(testSuper);
    org.setSuperHumans(sprList);

    org = organizationDB.addOrg(org);

    Organization org2 = new Organization();
    org2.setEmail("Test Email");
    org2.setPhone("440-596-9586");
    org2.setName("Test Name");
    org2.setFaction(factionDB.getFactionById(1));
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
    org2.setLocation(location2);
    org2.setSuperHumans(sprList);
    org2 = organizationDB.addOrg(org2);

    List<Organization> fromDBList = organizationDB.getAllOrg();

    assertEquals(2, fromDBList.size());
    assertTrue(fromDBList.contains(org2));
    assertTrue(fromDBList.contains(organizationDB.getOrgById(org.getId())));
  }

  @Test
  public void testUpdateOrg() {
    Organization org = new Organization();
    org.setEmail("Test Email");
    org.setPhone("440-596-9586");
    org.setName("Test Name");
    org.setFaction(factionDB.getFactionById(1));

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
    List<SuperHuman> sprList = new ArrayList<>();
    sprList.add(testSuper);
    org.setSuperHumans(sprList);

    org = organizationDB.addOrg(org);
    Organization original = organizationDB.getOrgById(org.getId());

    org.setEmail("Test Email change");
    org.setPhone("440-596-9581");
    org.setName("Test Name1");
    org.setDescription("Test Description");

    SuperHuman testSuper2 = new SuperHuman();
    testSuper2.setPower(powerList);
    testSuper2.setFaction(factionDB.getFactionById(1));
    testSuper2.setName("Test Name");
    testSuper2.setDescription("Test Description");
    testSuper2 = superHumanDB.addSuper(testSuper);
    sprList.add(testSuper2);
    org.setSuperHumans(sprList);

    organizationDB.editOrg(org);

    Organization fromDB = organizationDB.getOrgById(org.getId());

    assertNotEquals(fromDB, original);
  }

  @Test
  public void testDeleteOrg() {

    Organization org = new Organization();
    org.setEmail("Test Email");
    org.setPhone("440-596-9586");
    org.setName("Test Name");
    org.setFaction(factionDB.getFactionById(1));

    Location location = new Location();
    location.setName("Test Name");
    location.setCountry("Test Country");
    location.setZipcode("11111");
    location.setAddress("Test Address");
    location.setCity("Test City");
    location.setState("OH");
    location.setLongitude(new BigDecimal("-81.681290"));
    location.setLatitude(new BigDecimal("41.505493"));
    org.setLocation(locationDB.addLocation(location));

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

    org = organizationDB.addOrg(org);
    Organization fromDB = organizationDB.getOrgById(org.getId());
    assertEquals(org, fromDB);

    organizationDB.removeOrg(org.getId());

    assertNull(organizationDB.getOrgById(org.getId()));
  }

  @Test
  public void testGetOrgForSuper() {
    Organization org = new Organization();
    org.setEmail("Test Email");
    org.setPhone("440-596-9586");
    org.setName("Test Name");
    org.setFaction(factionDB.getFactionById(1));

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

    org = organizationDB.addOrg(org);
    List<Organization> orgList = organizationDB.getOrgForSuper(testSuper);
    assertTrue(orgList.contains(org));

  }
}