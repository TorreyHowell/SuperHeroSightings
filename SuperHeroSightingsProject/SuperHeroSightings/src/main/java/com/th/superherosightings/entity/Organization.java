package com.th.superherosightings.entity;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Organization {

  private int id;
  private Faction faction;
  @NotBlank(message = "A name is required")
  @Size(max = 30, message = "Cannot exceed 30 characters")
  private String name;

  @NotBlank(message = "A description is required")
  private String description;
  private Location location;

  @Size(max = 15, message = "Enter Valid Phone Number")
  @Size(min = 10, message = "Enter Valid Phone Number")
  private String phone;

  @Email(message = "Valid email required")
  @NotBlank(message = "Email is required")
  private String email;
  private List<SuperHuman> superHumans;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Faction getFaction() {
    return faction;
  }

  public void setFaction(Faction faction) {
    this.faction = faction;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<SuperHuman> getSuperHumans() {
    return superHumans;
  }

  public void setSuperHumans(List<SuperHuman> superHumans) {
    this.superHumans = superHumans;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Organization that = (Organization) o;

    if (getId() != that.getId()) {
      return false;
    }
    if (getFaction() != null ? !getFaction().equals(that.getFaction())
        : that.getFaction() != null) {
      return false;
    }
    if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
      return false;
    }
    if (getDescription() != null ? !getDescription().equals(that.getDescription())
        : that.getDescription() != null) {
      return false;
    }
    if (getLocation() != null ? !getLocation().equals(that.getLocation())
        : that.getLocation() != null) {
      return false;
    }
    if (getPhone() != null ? !getPhone().equals(that.getPhone()) : that.getPhone() != null) {
      return false;
    }
    if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) {
      return false;
    }
    return getSuperHumans() != null ? getSuperHumans().equals(that.getSuperHumans())
        : that.getSuperHumans() == null;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (getFaction() != null ? getFaction().hashCode() : 0);
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
    result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    result = 31 * result + (getSuperHumans() != null ? getSuperHumans().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Organization{" +
        "id=" + id +
        ", faction=" + faction +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", location=" + location +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", superHumans=" + superHumans +
        '}';
  }
}
