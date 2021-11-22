package com.th.superherosightings.data;

import com.th.superherosightings.data.FactionDBImpl.FactionMapper;
import com.th.superherosightings.data.LocationDBImpl.LocationMapper;
import com.th.superherosightings.data.PowerDBImpl.PowerMapper;
import com.th.superherosightings.data.SuperHumanDBImpl.SuperMapper;
import com.th.superherosightings.entity.Faction;
import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Siting;
import com.th.superherosightings.entity.SuperHuman;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SitingDBImpl implements SitingDB {

  JdbcTemplate jdbc;

  public SitingDBImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Siting getSitingById(int id) {
    final String SQL = "SELECT * FROM Siting WHERE sitingId = ?";
    Siting siting;
    try {
      siting = jdbc.queryForObject(SQL, new SitingMapper(), id);
    } catch (DataAccessException e) {
      return null;
    }
    if (siting != null) {
      this.assignSuper(siting);
      this.assignLocation(siting);
    }
    return siting;
  }

  public void assignSuper(Siting siting) {
    final String SELECT_SUPER = "SELECT * FROM Siting s "
        + "JOIN Super spr ON spr.superId = s.superId "
        + "WHERE s.sitingId = ?";
    SuperHuman spr;
    try {
      spr = jdbc.queryForObject(SELECT_SUPER, new SuperMapper(), siting.getId());
    } catch (DataAccessException ignored) {
      return;
    }
    final String SELECT_POWERS = "SELECT * FROM Super s "
        + "LEFT OUTER JOIN PowerSuper ps ON s.superId = ps.powerId "
        + "LEFT OUTER JOIN Power p ON p.powerId = ps.powerId "
        + "WHERE s.superId = ?";

    spr.setPower(jdbc.query(SELECT_POWERS, new PowerMapper(), spr.getSuperId()));

    final String SELECT_FACTION = "SELECT * FROM Super s "
        + "JOIN Faction f ON s.factionId = f.factionId "
        + "WHERE s.superId = ?";
    Faction faction = new Faction();
    try {
      faction = jdbc.queryForObject(SELECT_FACTION, new FactionMapper(), spr.getSuperId());
    } catch (DataAccessException ignored) {
    }
    spr.setFaction(faction);
    siting.setSuperHuman(spr);

  }

  public void assignLocation(Siting siting) {
    final String SQL = "SELECT * FROM Location l "
        + "JOIN Siting s ON l.locationId = s.locationId "
        + "WHERE s.sitingId = ?";
    try {
      siting.setLocation(jdbc.queryForObject(SQL, new LocationMapper(), siting.getId()));
    } catch (DataAccessException ignored) {
    }
  }

  @Override
  public List<Siting> getAllSitings() {
    final String SQL = "SELECT * FROM Siting "
        + "ORDER BY date DESC ";
    List<Siting> sitings = jdbc.query(SQL, new SitingMapper());
    this.assignSupersLocations(sitings);
    return sitings;
  }

  private void assignSupersLocations(List<Siting> sitings) {
    for (Siting siting : sitings) {
      this.assignLocation(siting);
      this.assignSuper(siting);
    }
  }

  @Override
  @Transactional
  public Siting addSiting(Siting siting) {
    final String INSERT_SITING = "INSERT INTO Siting(superId, locationId, description, date) "
        + "VALUES(?,?,?,?)";

    try {
      jdbc.update(INSERT_SITING, siting.getSuperHuman().getSuperId(),
          siting.getLocation().getId(),
          siting.getDescription(),
          siting.getDate().toString());
    } catch (DataAccessException e) {
      return null;
    }
    siting.setId(jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
    return siting;
  }

  @Override
  @Transactional
  public void editSiting(Siting siting) {
    final String UPDATE_SITING =
        "UPDATE Siting SET superId = ?, locationId = ?, description = ?, date = ? "
            + "WHERE sitingId = ?";

    try {
      jdbc.update(UPDATE_SITING, siting.getSuperHuman().getSuperId(),
          siting.getLocation().getId(),
          siting.getDescription(),
          siting.getDate().toString(),
          siting.getId());
    } catch (DataAccessException ignored) {
    }

  }

  @Override
  @Transactional
  public void removeSiting(int id) {
    final String DELETE_SITING = "DELETE FROM Siting WHERE sitingId = ?";
    try {
      jdbc.update(DELETE_SITING, id);
    } catch (DataAccessException ignore) {
    }


  }

  @Override
  public List<Siting> getSitingsForSuper(SuperHuman superHuman) {
    final String SQL = "SELECT * FROM Siting s "
        + "JOIN Super spr ON s.superId = spr.superId "
        + "WHERE spr.superId = ?";
    List<Siting> sitings = jdbc.query(SQL, new SitingMapper(), superHuman.getSuperId());
    this.assignSupersLocations(sitings);
    return sitings;
  }

  @Override
  public List<Siting> getSitingsForLocation(Location location) {
    final String SQL = "SELECT * FROM Siting WHERE locationId = ?";
    List<Siting> sitings = jdbc.query(SQL, new SitingMapper(), location.getId());
    this.assignSupersLocations(sitings);
    return sitings;
  }

  @Override
  public List<Siting> getSitingsForDate(LocalDate date) {
    if (date == null) {
      return null;
    }
    final String SQL = "SELECT * FROM Siting "
        + "WHERE date LIKE ?";
    List<Siting> sitings = jdbc.query(SQL, new SitingMapper(), date.toString());
    this.assignSupersLocations(sitings);
    return sitings;
  }

  @Override
  public List<Siting> getRecentSitings() {
    final String SQL = "SELECT * FROM Siting "
        + "ORDER BY date DESC LIMIT 10";
    List<Siting> sightings = jdbc.query(SQL, new SitingMapper());
    this.assignSupersLocations(sightings);
    return sightings;
  }

  public static final class SitingMapper implements RowMapper<Siting> {

    @Override
    public Siting mapRow(ResultSet rs, int i) throws SQLException {
      Siting siting = new Siting();
      siting.setId(rs.getInt("sitingId"));
      siting.setDescription(rs.getString("description"));
      LocalDate date = LocalDate
          .parse(rs.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      siting.setDate(date);
      return siting;
    }
  }
}
