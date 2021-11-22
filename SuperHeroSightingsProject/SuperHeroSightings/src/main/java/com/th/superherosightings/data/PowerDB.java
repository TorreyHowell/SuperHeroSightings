package com.th.superherosightings.data;

import com.th.superherosightings.entity.Power;
import java.util.List;

public interface PowerDB {

  Power getPowerById(int id);

  List<Power> getAllPowers();

  Power addPower(Power power);

  void editPower(Power power);

  void removePower(int id);

}
