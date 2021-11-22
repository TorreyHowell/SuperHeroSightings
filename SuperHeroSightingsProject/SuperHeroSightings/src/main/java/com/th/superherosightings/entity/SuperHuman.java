package com.th.superherosightings.entity;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SuperHuman {

  private int superId;

  @Size(max = 30, message = "Name cannot exceed 30 characters")
  @NotBlank(message = "A name is required")
  private String name;

  private Faction faction;
  private List<Power> power;

  @NotBlank(message = "A description is required")
  private String description;

  private String photoPath;


  public int getSuperId() {
    return superId;
  }

  public void setSuperId(int superId) {
    this.superId = superId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Faction getFaction() {
    return faction;
  }

  public void setFaction(Faction faction) {
    this.faction = faction;
  }

  public List<Power> getPower() {
    return power;
  }

  public void setPower(List<Power> power) {
    this.power = power;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPhotoPath() {
    return photoPath;
  }

  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SuperHuman that = (SuperHuman) o;

    if (getSuperId() != that.getSuperId()) {
      return false;
    }
    if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
      return false;
    }
    if (getFaction() != null ? !getFaction().equals(that.getFaction())
        : that.getFaction() != null) {
      return false;
    }
    if (getPower() != null ? !getPower().equals(that.getPower()) : that.getPower() != null) {
      return false;
    }
    if (getDescription() != null ? !getDescription().equals(that.getDescription())
        : that.getDescription() != null) {
      return false;
    }
    return getPhotoPath() != null ? getPhotoPath().equals(that.getPhotoPath())
        : that.getPhotoPath() == null;
  }

  @Override
  public int hashCode() {
    int result = getSuperId();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getFaction() != null ? getFaction().hashCode() : 0);
    result = 31 * result + (getPower() != null ? getPower().hashCode() : 0);
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    result = 31 * result + (getPhotoPath() != null ? getPhotoPath().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SuperHuman{" +
        "superId=" + superId +
        ", name='" + name + '\'' +
        ", faction=" + faction +
        ", power=" + power +
        ", description='" + description + '\'' +
        ", photoPath='" + photoPath + '\'' +
        '}';
  }
}
