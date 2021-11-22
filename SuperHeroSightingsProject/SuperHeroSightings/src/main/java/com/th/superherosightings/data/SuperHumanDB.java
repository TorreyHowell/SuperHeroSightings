package com.th.superherosightings.data;

import com.th.superherosightings.entity.Power;
import com.th.superherosightings.entity.SuperHuman;
import java.util.List;

public interface SuperHumanDB {

  SuperHuman getSuperById(int id);

  List<SuperHuman> getAllSupers();

  SuperHuman addSuper(SuperHuman superHuman);

  void editSuper(SuperHuman superHuman);

  void removeSuperById(int id);

  List<SuperHuman> getSupersForPower(Power power);
}
