package com.th.superherosightings.controller;

import com.th.superherosightings.Service.Service;
import com.th.superherosightings.entity.Location;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("locations")
public class LocationController {

  Service service;

  public LocationController(Service service) {
    this.service = service;
  }

  @GetMapping
  public String viewLocations(Model model) {
    List<Location> locations = service.readLocations();
    model.addAttribute("locations", locations);
    model.addAttribute("location", new Location());
    return "Locations/locations";
  }

  @PostMapping("addLocation")
  public String addLocation(@Valid Location location, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("location", location);
      model.addAttribute("locations", service.readLocations());
      return "Locations/locations";
    }
    service.createLocation(location);
    return "redirect:/locations";
  }

  @GetMapping("edit")
  public String editPower(Integer id, Model model) {
    Location location = service.readLocation(id);
    model.addAttribute("location", location);
    return "Locations/editLocation";
  }

  @PostMapping("edit")
  public String performEditPower(@Valid Location location, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("location", location);
      return "Locations/editLocation";
    }
    service.updateLocation(location);
    return "redirect:/locations";
  }

  @GetMapping("details")
  public String displayLocationDetails(Integer id, Model model) {
    Location location = service.readLocation(id);
    model.addAttribute("location", location);
    model.addAttribute("sightings", service.readSightingsForLocation(location));
    model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    return "Locations/locationDetails";
  }

  @GetMapping("confirmDelete")
  public String confirmDelete(Integer id, Model model) {
    model.addAttribute("location", service.readLocation(id));
    return "Locations/confirmLocationDelete";
  }

  @GetMapping("delete")
  public String deleteLocation(Integer id) {
    service.deleteLocation(id);
    return "redirect:/locations";
  }
}
