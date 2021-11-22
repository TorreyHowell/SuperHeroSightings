package com.th.superherosightings.entity;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

public class Siting {

  private int id;
  private SuperHuman superHuman;
  private Location location;

  @Size(max = 255, message = "Description cannot exceed 255 characters")
  @NotBlank(message = "A description is required")
  private String description;

  @PastOrPresent(message = "Date cannot be in the future")
  private LocalDate date;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public SuperHuman getSuperHuman() {
    return superHuman;
  }

  public void setSuperHuman(SuperHuman superHuman) {
    this.superHuman = superHuman;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Siting siting = (Siting) o;

    if (getId() != siting.getId()) {
      return false;
    }
    if (getSuperHuman() != null ? !getSuperHuman().equals(siting.getSuperHuman())
        : siting.getSuperHuman() != null) {
      return false;
    }
    if (getLocation() != null ? !getLocation().equals(siting.getLocation())
        : siting.getLocation() != null) {
      return false;
    }
    if (getDescription() != null ? !getDescription().equals(siting.getDescription())
        : siting.getDescription() != null) {
      return false;
    }
    return getDate() != null ? getDate().equals(siting.getDate()) : siting.getDate() == null;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (getSuperHuman() != null ? getSuperHuman().hashCode() : 0);
    result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Siting{" +
        "id=" + id +
        ", superHuman=" + superHuman +
        ", location=" + location +
        ", description='" + description + '\'' +
        ", date=" + date +
        '}';
  }
}
