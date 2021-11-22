package com.th.superherosightings.data;

import com.th.superherosightings.data.FactionDBImpl.FactionMapper;
import com.th.superherosightings.data.LocationDBImpl.LocationMapper;
import com.th.superherosightings.data.PowerDBImpl.PowerMapper;
import com.th.superherosightings.data.SuperHumanDBImpl.SuperMapper;
import com.th.superherosightings.entity.Organization;
import com.th.superherosightings.entity.SuperHuman;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrganizationDBImpl implements OrganizationDB {

  JdbcTemplate jdbc;

  public OrganizationDBImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Organization getOrgById(int id) {
    final String SQL = "SELECT * FROM Organization "
        + "WHERE organizationId = ?";
    Organization org;
    try {
      org = jdbc.queryForObject(SQL, new OrgMapper(), id);
    } catch (DataAccessException e) {
      return null;
    }
    if (org != null) {
      assignFaction(org);
      assignSupers(org);
      assignLocation(org);
    }
    return org;
  }

  private void assignFaction(Organization org) {
    final String SELECT_FACTION = "SELECT * FROM Organization o "
        + "JOIN Faction f ON f.factionId = o.factionId "
        + "WHERE o.organizationId = ?";
    org.setFaction(jdbc.queryForObject(SELECT_FACTION, new FactionMapper(), org.getId()));
  }

  private void assignSupers(Organization org) {
    final String SELECT_SUPERS = "SELECT * FROM SuperOrganization so "
        + "JOIN Super s ON so.superId = s.superId "
        + "WHERE so.organizationId = ?";
    List<SuperHuman> sprList = jdbc.query(SELECT_SUPERS, new SuperMapper(), org.getId());
    assignFactionsAndPowers(sprList);
    org.setSuperHumans(sprList);
  }

  private void assignFactionsAndPowers(List<SuperHuman> sprList) {
    for (SuperHuman spr : sprList) {
      this.assignPower(spr);
      this.assignFaction(spr);
    }
  }

  private void assignPower(SuperHuman spr) {
    final String SQL = "SELECT * FROM PowerSuper ps "
        + "JOIN Power p ON ps.powerId = p.powerId "
        + "JOIN Super s ON ps.superId = s.superId "
        + "WHERE s.superId = ?";
    spr.setPower(jdbc.query(SQL, new PowerMapper(), spr.getSuperId()));
  }

  private void assignFaction(SuperHuman spr) {
    final String SQL = "SELECT * FROM Super s "
        + "JOIN Faction f ON s.factionId = f.factionId "
        + "WHERE s.superId = ?";
    spr.setFaction(jdbc.queryForObject(SQL, new FactionMapper(), spr.getSuperId()));
  }

  private void assignLocation(Organization org) {
    final String SELECT_LOCATION = "SELECT l.locationId, l.name, l.description, country, address, city, state, zipcode, latitude, longitude, l.locationId FROM Organization o "
        + "JOIN Location l ON o.locationId = l.locationId "
        + "WHERE o.organizationId = ?";
    org.setLocation(jdbc.queryForObject(SELECT_LOCATION, new LocationMapper(), org.getId()));
  }

  @Override
  public List<Organization> getAllOrg() {
    final String SELECT_ORG = "SELECT * FROM Organization "
        + "ORDER BY name ASC ";
    List<Organization> orgList = jdbc.query(SELECT_ORG, new OrgMapper());
    this.assignFactionsPowersLocations(orgList);
    return orgList;
  }

  private void assignFactionsPowersLocations(List<Organization> orgList) {
    for (Organization org : orgList) {
      this.assignSupers(org);
      this.assignFaction(org);
      this.assignLocation(org);
    }
  }


  @Override
  @Transactional
  public Organization addOrg(Organization org) {
    final String INSERT_ORG =
        "INSERT INTO Organization(factionId, name, locationId, description, phone, email) "
            + "VALUES(?,?,?,?,?,?)";
    try {
      jdbc.update(INSERT_ORG,
          org.getFaction().getId(),
          org.getName(),
          org.getLocation().getId(),
          org.getDescription(),
          org.getPhone(),
          org.getEmail());
    } catch (DataAccessException e) {
      return null;
    }
    org.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
    insertSuperOrg(org);
    return org;
  }

  private void insertSuperOrg(Organization org) {
    final String INSERT_SUPER_ORG = "INSERT INTO SuperOrganization(organizationId, superId) "
        + "VALUES(?,?)";
    for (SuperHuman superHuman : org.getSuperHumans()) {
      jdbc.update(INSERT_SUPER_ORG, org.getId(), superHuman.getSuperId());
    }
  }

  @Override
  @Transactional
  public void editOrg(Organization org) {
    final String UPDATE_ORG =
        "UPDATE Organization SET factionId = ?, name = ?, description = ?, locationId = ?, phone = ?, email = ? "
            + "WHERE organizationId = ?";
    try {
      jdbc.update(UPDATE_ORG,
          org.getFaction().getId(),
          org.getName(),
          org.getDescription(),
          org.getLocation().getId(),
          org.getPhone(),
          org.getEmail(),
          org.getId());
    } catch (DataAccessException e) {
      return;
    }

    final String DELETE_SUPER_ORG = "DELETE FROM SuperOrganization WHERE organizationId = ?";
    jdbc.update(DELETE_SUPER_ORG, org.getId());

    this.insertSuperOrg(org);


  }

  @Override
  @Transactional
  public void removeOrg(int id) {
    final String DELETE_SUPER_ORG = "DELETE FROM superOrganization WHERE organizationId = ?";
    final String DELETE_ORG = "DELETE FROM Organization WHERE organizationId = ?";

    try {
      jdbc.update(DELETE_SUPER_ORG, id);
      jdbc.update(DELETE_ORG, id);
    } catch (DataAccessException ignored) {

    }

  }

  @Override
  public List<Organization> getOrgForSuper(SuperHuman superHuman) {
    final String SELECT_ORG = "SELECT * FROM SuperOrganization so "
        + "JOIN Organization o ON o.organizationId = so.organizationId "
        + "WHERE so.superId = ?";
    List<Organization> orgList = jdbc.query(SELECT_ORG, new OrgMapper(), superHuman.getSuperId());
    this.assignFactionsPowersLocations(orgList);
    return orgList;

  }

  public static final class OrgMapper implements RowMapper<Organization> {

    @Override
    public Organization mapRow(ResultSet rs, int i) throws SQLException {
      Organization org = new Organization();
      org.setId(rs.getInt("organizationId"));
      org.setName(rs.getString("name"));
      org.setDescription(rs.getString("description"));
      org.setPhone(rs.getString("phone"));
      org.setEmail(rs.getString("email"));
      return org;
    }
  }
}
