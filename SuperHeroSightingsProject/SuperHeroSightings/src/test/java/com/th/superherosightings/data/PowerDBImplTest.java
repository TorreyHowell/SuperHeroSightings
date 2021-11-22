package com.th.superherosightings.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.th.superherosightings.entity.Power;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class PowerDBImplTest {

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
  public void testAddGetPower() {
    Power testPower = new Power();
    testPower.setName("Test Name");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    Power fromDB = powerDB.getPowerById(testPower.getId());

    assertEquals(testPower, fromDB);
  }

  @Test
  public void testGetAllPowers() {
    Power testPower = new Power();
    testPower.setName("Test Name");
    testPower.setDescription("Test Description");
    powerDB.addPower(testPower);

    Power testPower2 = new Power();
    testPower2.setName("Test Name");
    testPower2.setDescription("Test Description");
    powerDB.addPower(testPower2);

    List<Power> powers = powerDB.getAllPowers();

    assertEquals(2, powers.size());
    assertTrue(powers.contains(testPower));
    assertTrue(powers.contains(testPower2));


  }

  @Test
  public void testUpdatePower() {
    Power testPower = new Power();
    testPower.setName("Test Name");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);

    Power old = powerDB.getPowerById(testPower.getId());

    testPower.setName("Changed Test Name");
    testPower.setDescription("Changed Test Description");
    powerDB.editPower(testPower);

    assertNotEquals(testPower, old);

    Power updated = powerDB.getPowerById(testPower.getId());

    assertEquals(updated, testPower);

  }

  @Test
  public void testDeletePower() {
    Power testPower = new Power();
    testPower.setName("Test Name");
    testPower.setDescription("Test Description");
    testPower = powerDB.addPower(testPower);
    powerDB.getPowerById(testPower.getId());

    powerDB.removePower(testPower.getId());

    assertNull(powerDB.getPowerById(testPower.getId()));


  }

}