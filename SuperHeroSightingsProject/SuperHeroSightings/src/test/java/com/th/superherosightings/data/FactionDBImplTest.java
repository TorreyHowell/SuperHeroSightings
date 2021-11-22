package com.th.superherosightings.data;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.th.superherosightings.entity.Faction;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FactionDBImplTest {

  @Autowired
  FactionDB factionDB;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getFactionById() {
    Faction fromDB = factionDB.getFactionById(1);
    assertEquals(fromDB.getName(), "Hero");
  }

  @Test
  void getAllFactions() {
    List<Faction> factions = factionDB.getAllFactions();
    assertNotNull(factions);
  }
}