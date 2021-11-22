package com.th.superherosightings.data;

import com.th.superherosightings.entity.Organization;
import com.th.superherosightings.entity.SuperHuman;
import java.util.List;

public interface OrganizationDB {

  Organization getOrgById(int id);

  List<Organization> getAllOrg();

  Organization addOrg(Organization org);

  void editOrg(Organization org);

  void removeOrg(int id);

  List<Organization> getOrgForSuper(SuperHuman superHuman);

}
