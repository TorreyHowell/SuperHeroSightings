package com.th.superherosightings.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.th.superherosightings.entity.Faction;
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
class SuperHumanDBImplTest {

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
  public void testCreateGetSuper() {

    Faction testFaction = factionDB.getFactionById(1);
    Power testPower = new Power();
    testPower.setName("Test Power");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    List<Power> powerList = new ArrayList<>();
    powerList.add(testPower);

    Power testPower2 = new Power();
    testPower2.setName("Test Power");
    testPower2.setDescription("Test Description");
    testPower2 = powerDB.addPower(testPower2);
    powerList.add(testPower2);

    SuperHuman testSuper = new SuperHuman();
    testSuper.setPower(powerList);
    testSuper.setName("Test Name");
    testSuper.setFaction(testFaction);
    testSuper.setDescription("Test Description");

    testSuper = superHumanDB.addSuper(testSuper);
    SuperHuman fromDB = superHumanDB.getSuperById(testSuper.getSuperId());

    assertEquals(testSuper, fromDB);


  }

  @Test
  public void testGetAllSuper() {
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
    SuperHuman fromDB = superHumanDB.getSuperById(testSuper.getSuperId());
    assertEquals(testSuper, fromDB);

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

    List<SuperHuman> supers = superHumanDB.getAllSupers();

    assertEquals(2, supers.size());
    assertTrue(supers.contains(testSuper));
    assertTrue(supers.contains(testSuper2));

  }

  @Test
  public void testDeleteSuper() {
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

    superHumanDB.removeSuperById(testSuper.getSuperId());

    assertNull(superHumanDB.getSuperById(testSuper.getSuperId()));

    assertFalse(organizationDB.getOrgById(org.getId()).getSuperHumans().contains(testSuper));

  }

  @Test
  public void testUpdateSuper() {
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
    SuperHuman fromDB = superHumanDB.getSuperById(testSuper.getSuperId());
    assertEquals(testSuper, fromDB);

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

    superHumanDB.editSuper(testSuper2);
    fromDB = superHumanDB.getSuperById(testSuper.getSuperId());

    assertNotEquals(fromDB, testSuper);

  }

  @Test
  public void testGetSupersForPowers() {

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
    SuperHuman fromDB = superHumanDB.getSuperById(testSuper.getSuperId());
    assertEquals(testSuper, fromDB);

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

    List<SuperHuman> supers = superHumanDB.getSupersForPower(testPower);

    assertEquals(supers.size(), 1);
    assertTrue(supers.contains(testSuper));

  }


}