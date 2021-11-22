package com.th.superherosightings.data;

import com.th.superherosightings.data.OrganizationDBImpl.OrgMapper;
import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Organization;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LocationDBImpl implements LocationDB {

  JdbcTemplate jdbc;

  public LocationDBImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Location getLocationById(int id) {
    final String SQL = "SELECT * FROM Location "
        + "WHERE locationId = ?";
    Location location;
    try {
      location = jdbc.queryForObject(SQL, new LocationMapper(), id);
    } catch (DataAccessException e) {
      return null;
    }
    return location;
  }

  @Override
  public List<Location> getAllLocations() {
    final String SQL = "SELECT * FROM Location";
    return jdbc.query(SQL, new LocationMapper());
  }

  @Transactional
  @Override
  public Location addLocation(Location location) {
    final String SQL =
        "INSERT INTO Location (name, description, country, address, city, state, zipcode, latitude, longitude) "
            + "VALUES (?,?,?,?,?,?,?,?,?);";
    try {
      jdbc.update(SQL,
          location.getName(),
          location.getDescription(),
          location.getCountry(),
          location.getAddress(),
          location.getCity(),
          location.getState(),
          location.getZipcode(),
          location.getLatitude(),
          location.getLongitude());
    } catch (DataAccessException e) {
      return null;
    }

    int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    location.setId(newId);
    return location;
  }

  @Override
  @Transactional
  public void removeLocation(int id) {
    final String SELECT_ORGANIZATION = "SELECT * FROM Organization WHERE locationId = ?";
    List<Organization> orgList = jdbc.query(SELECT_ORGANIZATION, new OrgMapper(), id);
    final String DELETE_SUPER_ORG = "DELETE FROM SuperOrganization WHERE organizationId = ?";
    final String DELETE_SITING = "DELETE FROM Siting WHERE locationId = ?";
    final String DELETE_ORG = "DELETE FROM Organization WHERE locationId = ?";
    final String DELETE_LOCATION = "DELETE FROM Location WHERE locationId = ?";
    try {
      jdbc.update(DELETE_SITING, id);

      if (!orgList.isEmpty()) {
        for (Organization org : orgList) {
          jdbc.update(DELETE_SUPER_ORG, org.getId());
        }
      }

      jdbc.update(DELETE_ORG, id);

      jdbc.update(DELETE_LOCATION, id);
    } catch (DataAccessException ignored) {
    }

  }

  @Override
  public void editLocation(Location location) {
    final String SQL =
        "UPDATE Location SET name = ?, description = ?, country = ?, address = ?, city = ?, state = ?, zipcode = ?, latitude = ?, longitude = ? "
            + "WHERE locationId = ?";
    try {
      jdbc.update(SQL,
          location.getName(),
          location.getDescription(),
          location.getCountry(),
          location.getAddress(),
          location.getCity(),
          location.getState(),
          location.getZipcode(),
          location.getLatitude(),
          location.getLongitude(),
          location.getId());
    } catch (DataAccessException ignored) {
    }
  }

  public static final class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int i) throws SQLException {
      Location location = new Location();
      location.setId(rs.getInt("locationId"));
      location.setName(rs.getString("name"));
      location.setDescription(rs.getString("description"));
      location.setCountry(rs.getString("country"));
      location.setAddress(rs.getString("address"));
      location.setCity(rs.getString("city"));
      location.setState(rs.getString("state"));
      location.setZipcode(rs.getString("zipcode"));
      try {
        location.setLatitude(rs.getBigDecimal("latitude").setScale(6, RoundingMode.UNNECESSARY));
        location.setLongitude(rs.getBigDecimal("longitude").setScale(6, RoundingMode.UNNECESSARY));
      } catch (NullPointerException ignored) {
      }

      return location;
    }
  }
}
