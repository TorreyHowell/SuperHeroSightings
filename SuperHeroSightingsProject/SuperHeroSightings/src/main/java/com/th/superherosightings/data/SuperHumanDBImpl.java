package com.th.superherosightings.data;

import com.th.superherosightings.data.FactionDBImpl.FactionMapper;
import com.th.superherosightings.data.PowerDBImpl.PowerMapper;
import com.th.superherosightings.entity.Power;
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
public class SuperHumanDBImpl implements SuperHumanDB {

  JdbcTemplate jdbc;

  public SuperHumanDBImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public SuperHuman getSuperById(int id) {
    final String SQL = "SELECT * FROM Super WHERE superId = ?";
    SuperHuman spr;
    try {
      spr = jdbc.queryForObject(SQL, new SuperMapper(), id);
    } catch (DataAccessException ex) {
      return null;
    }

    this.assignFaction(spr);
    this.assignPower(spr);
    return spr;
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

  @Override
  public List<SuperHuman> getAllSupers() {
    final String SQL = "SELECT * FROM Super "
        + "ORDER BY superName ASC";
    List<SuperHuman> sprList = jdbc.query(SQL, new SuperMapper());
    this.assignFactionsAndPowers(sprList);
    return sprList;
  }

  private void assignFactionsAndPowers(List<SuperHuman> sprList) {
    for (SuperHuman spr : sprList) {
      this.assignPower(spr);
      this.assignFaction(spr);
    }
  }

  @Override
  @Transactional
  public SuperHuman addSuper(SuperHuman superHuman) {
    final String SQL = "INSERT INTO Super(factionId, superName, description, photo) "
        + "VALUES(?,?,?,?)";
    try {
      jdbc.update(SQL,
          superHuman.getFaction().getId(),
          superHuman.getName(),
          superHuman.getDescription(),
          superHuman.getPhotoPath());
    } catch (DataAccessException e) {
      return null;
    }
    int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    superHuman.setSuperId(newId);
    insertPowerSuper(superHuman);
    return superHuman;
  }

  public void insertPowerSuper(SuperHuman superHuman) {
    final String SQL = "INSERT INTO PowerSuper(powerId, SuperId) "
        + "VALUES (?,?)";
    for (Power power : superHuman.getPower()) {
      jdbc.update(SQL,
          power.getId(),
          superHuman.getSuperId());
    }

  }

  @Override
  @Transactional
  public void editSuper(SuperHuman superHuman) {
    final String UPDATE_SUPER =
        "UPDATE Super SET factionId = ?, superName = ?, description = ?, photo = ? "
            + "WHERE superId = ?";
    try {
      jdbc.update(UPDATE_SUPER, superHuman.getFaction().getId(),
          superHuman.getName(),
          superHuman.getDescription(),
          superHuman.getPhotoPath(),
          superHuman.getSuperId());
    } catch (DataAccessException e) {
      return;
    }
    final String DELETE_POWER_SUPER = "DELETE FROM PowerSuper WHERE superId = ?";
    jdbc.update(DELETE_POWER_SUPER, superHuman.getSuperId());
    this.insertPowerSuper(superHuman);

  }

  @Override
  @Transactional
  public void removeSuperById(int id) {
    final String DELETE_SUPER_ORG = "DELETE FROM SuperOrganization WHERE superId = ?";
    jdbc.update(DELETE_SUPER_ORG, id);

    final String DELETE_SUPER_POWER = "DELETE FROM PowerSuper WHERE superId = ?";
    jdbc.update(DELETE_SUPER_POWER, id);

    final String DELETE_SITING = "DELETE FROM Siting WHERE superId = ?";
    jdbc.update(DELETE_SITING, id);

    final String DELETE_SUPER = "DELETE FROM Super WHERE superId = ?";
    jdbc.update(DELETE_SUPER, id);

  }


  @Override
  public List<SuperHuman> getSupersForPower(Power power) {
    final String SQL = "SELECT * FROM PowerSuper ps "
        + "JOIN Power p ON ps.powerId = p.powerId "
        + "JOIN Super s ON s.superId = ps.superId "
        + "WHERE p.powerId = ?";
    List<SuperHuman> sprList = jdbc.query(SQL, new SuperMapper(), power.getId());
    this.assignFactionsAndPowers(sprList);
    return sprList;
  }

  public static final class SuperMapper implements RowMapper<SuperHuman> {

    @Override
    public SuperHuman mapRow(ResultSet rs, int i) throws SQLException {
      SuperHuman spr = new SuperHuman();
      spr.setSuperId(rs.getInt("superId"));
      spr.setName(rs.getString("superName"));
      spr.setDescription(rs.getString("description"));
      spr.setPhotoPath(rs.getString("photo"));
      return spr;
    }
  }
}


