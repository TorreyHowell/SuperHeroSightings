package com.th.superherosightings.entity;

import javax.validation.constraints.Size;

public class Power {

  private int id;


  @Size(max = 30, message = "Name cannot be more than 30 characters")
  @Size(min = 2, message = "Name must be at least 2 characters")
  private String name;


  @Size(max = 255, message = "Description cannot be more than 255 characters")
  @Size(min = 10, message = "Description must be at least 10 characters")
  private String description;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Power power = (Power) o;

    if (getId() != power.getId()) {
      return false;
    }
    if (getName() != null ? !getName().equals(power.getName()) : power.getName() != null) {
      return false;
    }
    return getDescription() != null ? getDescription().equals(power.getDescription())
        : power.getDescription() == null;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Power{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
