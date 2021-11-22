package com.th.superherosightings.data;

import com.th.superherosightings.entity.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PowerDBImpl implements PowerDB {

  JdbcTemplate jdbc;

  public PowerDBImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Power getPowerById(int id) {
    final String SQL = "SELECT * FROM Power "
        + "WHERE powerId = ?";
    Power power;
    try {
      power = jdbc.queryForObject(SQL, new PowerMapper(), id);
    } catch (DataAccessException e) {
      return null;
    }
    return power;
  }

  @Override
  public List<Power> getAllPowers() {
    final String SQL = "SELECT * FROM Power "
        + "ORDER BY name ASC";
    return jdbc.query(SQL, new PowerMapper());
  }


  @Override
  @Transactional
  public Power addPower(Power power) {
    final String SQL = "INSERT INTO Power(name, description) "
        + "VALUES (?,?)";
    jdbc.update(SQL, power.getName(), power.getDescription());
    int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    power.setId(newId);
    return power;
  }

  @Override
  public void editPower(Power power) {
    final String SQL = "UPDATE Power SET name = ?, description = ? "
        + "WHERE powerId = ?";
    try {
      jdbc.update(SQL, power.getName(),
          power.getDescription(),
          power.getId());
    } catch (DataAccessException ignored) {
    }
  }

  @Override
  @Transactional
  public void removePower(int id) {
    final String DELETE_POWER = "DELETE FROM Power WHERE powerId = ?";
    final String DELETE_POWER_SUPER = "DELETE FROM PowerSuper WHERE powerId = ?";
    try {
      jdbc.update(DELETE_POWER_SUPER, id);
      jdbc.update(DELETE_POWER, id);
    } catch (DataAccessException ignored) {
    }
  }

  public static final class PowerMapper implements RowMapper<Power> {

    @Override
    public Power mapRow(ResultSet rs, int i) throws SQLException {
      Power power = new Power();
      power.setId(rs.getInt("powerId"));
      power.setName(rs.getString("name"));
      power.setDescription(rs.getString("description"));
      return power;
    }
  }
}
