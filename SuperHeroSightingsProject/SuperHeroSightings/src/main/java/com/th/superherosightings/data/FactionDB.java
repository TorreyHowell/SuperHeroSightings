package com.th.superherosightings.data;

import com.th.superherosightings.entity.Faction;
import java.util.List;

public interface FactionDB {

  Faction getFactionById(int id);

  List<Faction> getAllFactions();

}
