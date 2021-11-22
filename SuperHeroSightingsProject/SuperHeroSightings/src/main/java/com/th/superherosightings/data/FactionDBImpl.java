package com.th.superherosightings.data;

import com.th.superherosightings.entity.Faction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FactionDBImpl implements FactionDB {

  JdbcTemplate jdbc;

  public FactionDBImpl(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public Faction getFactionById(int id) {
    final String SQL = "SELECT * FROM Faction "
        + "WHERE factionId = ?";
    Faction faction;
    try {
      faction = jdbc.queryForObject(SQL, new FactionMapper(), id);
    } catch (DataAccessException e) {
      return null;
    }
    return faction;
  }

  @Override
  public List<Faction> getAllFactions() {
    final String SQL = "SELECT * FROM Faction";
    return jdbc.query(SQL, new FactionMapper());
  }

  public final static class FactionMapper implements RowMapper<Faction> {

    @Override
    public Faction mapRow(ResultSet rs, int i) throws SQLException {
      Faction faction = new Faction();
      faction.setId(rs.getInt("factionId"));
      faction.setName(rs.getString("factionName"));
      return faction;
    }
  }
}
