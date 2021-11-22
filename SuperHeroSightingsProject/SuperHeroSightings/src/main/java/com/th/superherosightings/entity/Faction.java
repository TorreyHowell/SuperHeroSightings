package com.th.superherosightings.entity;

public class Faction {

  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Faction faction = (Faction) o;

    if (getId() != faction.getId()) {
      return false;
    }
    return getName() != null ? getName().equals(faction.getName()) : faction.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Faction{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
