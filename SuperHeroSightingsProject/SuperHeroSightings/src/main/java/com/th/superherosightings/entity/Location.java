package com.th.superherosightings.entity;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Location {

  private int id;

  @Size(max = 30, message = "Name cannot be more than 30 characters")
  @Size(min = 2, message = "Name must be at least 2 characters")
  private String name;

  @Size(max = 30, message = "Name cannot be more than 30 characters")
  @NotBlank
  private String country;

  @Size(max = 30, message = "Address cannot be more than 30 characters")
  @NotBlank
  private String address;

  @Size(max = 30, message = "City cannot be more than 30 characters")
  @NotBlank
  private String city;

  @Size(max = 2, message = "State cannot be more than 2 characters")
  @NotBlank
  private String state;

  @Size(max = 5, message = "Enter a valid zipcode")
  @Size(min = 5, message = "Enter a valid zipcode")
  private String zipcode;


  @Max(value = 100, message = "Invalid Longitude")
  @Min(value = -90, message = "Invalid Longitude")
  @NotNull(message = "Latitude required")
  private BigDecimal latitude;

  @Max(value = 180, message = "Invalid Latitude")
  @Min(value = -180, message = "Invalid Latitude")
  @NotNull(message = "Longitude required")
  private BigDecimal longitude;


  @Size(max = 255, message = "Description cannot be more than 255 characters")
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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
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

    Location location = (Location) o;

    if (getId() != location.getId()) {
      return false;
    }
    if (getName() != null ? !getName().equals(location.getName()) : location.getName() != null) {
      return false;
    }
    if (getCountry() != null ? !getCountry().equals(location.getCountry())
        : location.getCountry() != null) {
      return false;
    }
    if (getAddress() != null ? !getAddress().equals(location.getAddress())
        : location.getAddress() != null) {
      return false;
    }
    if (getCity() != null ? !getCity().equals(location.getCity()) : location.getCity() != null) {
      return false;
    }
    if (getState() != null ? !getState().equals(location.getState())
        : location.getState() != null) {
      return false;
    }
    if (getZipcode() != null ? !getZipcode().equals(location.getZipcode())
        : location.getZipcode() != null) {
      return false;
    }
    if (getLongitude() != null ? !getLongitude().equals(location.getLongitude())
        : location.getLongitude() != null) {
      return false;
    }
    if (getLatitude() != null ? !getLatitude().equals(location.getLatitude())
        : location.getLatitude() != null) {
      return false;
    }
    return getDescription() != null ? getDescription().equals(location.getDescription())
        : location.getDescription() == null;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
    result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
    result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
    result = 31 * result + (getState() != null ? getState().hashCode() : 0);
    result = 31 * result + (getZipcode() != null ? getZipcode().hashCode() : 0);
    result = 31 * result + (getLongitude() != null ? getLongitude().hashCode() : 0);
    result = 31 * result + (getLatitude() != null ? getLatitude().hashCode() : 0);
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Location{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", country='" + country + '\'' +
        ", address='" + address + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", zipcode='" + zipcode + '\'' +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", description='" + description + '\'' +
        '}';
  }
}
