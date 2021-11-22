package com.th.superherosightings.controller;

import com.th.superherosightings.Service.Service;
import com.th.superherosightings.entity.Location;
import com.th.superherosightings.entity.Siting;
import com.th.superherosightings.entity.SuperHuman;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SightingController {

  Service service;

  public SightingController(Service service) {
    this.service = service;
  }

  @GetMapping("/sightings")
  public String viewSightings(Model model) {
    List<SuperHuman> supers = service.readSupers();
    List<Location> locations = service.readLocations();
    List<Siting> sightings = service.readSightings();
    model.addAttribute("supers", supers);
    model.addAttribute("locations", locations);
    model.addAttribute("sightings", sightings);
    model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    return "Sightings/sightings";
  }

  @GetMapping("sightings/date")
  public String sortSightings(Model model, HttpServletRequest request) {
    LocalDate sortDate = null;
    try {
      sortDate = LocalDate.parse(request.getParameter("filter"));
    } catch (DateTimeParseException ignored) {
    }
    List<Siting> sightings = service.sortSightings(sortDate);
    List<Location> locations = service.readLocations();

    model.addAttribute("sortDate", sortDate);
    model.addAttribute("sightings", sightings);
    model.addAttribute("locations", locations);
    model.addAttribute("sightings", sightings);
    model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    return "Sightings/sightings";
  }

  @PostMapping("sightings/addSighting")
  public String addSighting(@Valid Siting siting, BindingResult result, HttpServletRequest request,
      Model model) {
    ValidateSighting(siting, result, request);

    if (result.hasErrors()) {
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("locations", service.readLocations());
      model.addAttribute("sightings", service.readSightings());
      model.addAttribute("siting", siting);
      model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
      return "Sightings/sightings";
    }
    service.createSighting(siting);

    return "redirect:/sightings";

  }

  @GetMapping("sightings/confirmDelete")
  public String confirmDelete(Integer id, Model model) {
    model.addAttribute("sighting", service.readSighting(id));
    model.addAttribute("deleteLink", "/sightings/delete/?id=" + id);
    return "Sightings/confirmSightingDelete";
  }

  @GetMapping("sightings/details/confirmDelete")
  public String confirmDeleteFromDetails(Integer id, Model model) {
    model.addAttribute("sighting", service.readSighting(id));
    model.addAttribute("prev", "/sightings/details/?id=" + id);
    model.addAttribute("deleteLink", "/sightings/details/delete/?id=" + id);
    return "Sightings/confirmSightingDelete";
  }

  @GetMapping("/sighting-confirmDelete")
  public String confirmDeleteFromHome(Integer id, Model model) {
    model.addAttribute("sighting", service.readSighting(id));
    model.addAttribute("deleteLink", "/sighting-delete/?id=" + id);
    model.addAttribute("prev", "/");
    return "Sightings/confirmSightingDelete";
  }

  @GetMapping("/sighting-details/confirmDelete")
  public String confirmDeleteFromHomeDetails(Integer id, Model model) {
    model.addAttribute("sighting", service.readSighting(id));
    model.addAttribute("deleteLink", "/sighting-details/delete/?id=" + id);
    model.addAttribute("prev", "/sighting-details/?id=" + id);
    return "Sightings/confirmSightingDelete";
  }

  @GetMapping("sightings/delete")
  public String deleteSighting(Integer id) {
    service.deleteSighting(id);
    return "redirect:/sightings";
  }

  @GetMapping("/sightings/details/delete")
  public String deleteSightingFromDetails(Integer id) {
    service.deleteSighting(id);
    return "redirect:/sightings";
  }


  @GetMapping("/sighting-delete")
  public String deleteSightingFromHome(Integer id) {
    service.deleteSighting(id);
    return "redirect:/";
  }

  @GetMapping("/sighting-details/delete")
  public String deleteSightingFromHomeDetails(Integer id) {
    service.deleteSighting(id);
    return "redirect:/";
  }

  @GetMapping("sightings/edit")
  public String editSighting(Integer id, Model model) {
    model.addAttribute("siting", service.readSighting(id));
    model.addAttribute("supers", service.readSupers());
    model.addAttribute("locations", service.readLocations());
    model.addAttribute("prev", "/sightings" );
    return "Sightings/editSighting";
  }

  @GetMapping("sightings/details/edit")
  public String editSightingFromDetails(Integer id, Model model) {
    model.addAttribute("siting", service.readSighting(id));
    model.addAttribute("supers", service.readSupers());
    model.addAttribute("locations", service.readLocations());
    model.addAttribute("prev", "/sightings/details/?id=" + id);
    return "Sightings/editSighting";
  }

  @GetMapping("/sighting-details/edit")
  public String editSightingFromHomeDetails(Integer id, Model model) {
    model.addAttribute("siting", service.readSighting(id));
    model.addAttribute("supers", service.readSupers());
    model.addAttribute("locations", service.readLocations());
    model.addAttribute("prev", "/sighting-details/?id=" + id);
    return "Sightings/editSighting";
  }

  @GetMapping("/edit-sighting")
  public String editSightingFromHome(Integer id, Model model) {
    model.addAttribute("siting", service.readSighting(id));
    model.addAttribute("supers", service.readSupers());
    model.addAttribute("locations", service.readLocations());
    model.addAttribute("prev", "/");
    return "Sightings/editSighting";
  }

  @PostMapping("sightings/edit")
  public String performEdit(@Valid Siting siting, BindingResult result, HttpServletRequest request,
      Model model) {
    ValidateSighting(siting, result, request);

    if (result.hasErrors()) {
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("locations", service.readLocations());
      model.addAttribute("sightings", service.readSightings());
      model.addAttribute("siting", siting);
      model.addAttribute("prev", "/sightings/details/?id=" + siting.getId());
      return "Sightings/editSighting";
    }

    service.updateSighting(siting);

    return "redirect:/sightings";
  }

  @PostMapping("sightings/details/edit")
  public String performEditFromDetails(@Valid Siting siting, BindingResult result, HttpServletRequest request,
      Model model) {
    ValidateSighting(siting, result, request);

    if (result.hasErrors()) {
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("locations", service.readLocations());
      model.addAttribute("sightings", service.readSightings());
      model.addAttribute("siting", siting);
      model.addAttribute("prev", "/sightings/details/?id=" + siting.getId());
      return "Sightings/editSighting";
    }

    service.updateSighting(siting);

    return "redirect:/sightings/details/?id=" + siting.getId();
  }

  @PostMapping("sighting-details/edit")
  public String performEditFromHomeDetails(@Valid Siting siting, BindingResult result, HttpServletRequest request,
      Model model) {
    ValidateSighting(siting, result, request);

    if (result.hasErrors()) {
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("locations", service.readLocations());
      model.addAttribute("sightings", service.readSightings());
      model.addAttribute("siting", siting);
      model.addAttribute("prev", "/sighting-details/?id=" + siting.getId());
      return "Sightings/editSighting";
    }

    service.updateSighting(siting);

    return "redirect:/sighting-details/?id=" + siting.getId();
  }

  @PostMapping("/edit-sighting")
  public String performEditFromHome(@Valid Siting siting, BindingResult result, HttpServletRequest request,
      Model model) {
    ValidateSighting(siting, result, request);

    if (result.hasErrors()) {
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("locations", service.readLocations());
      model.addAttribute("sightings", service.readSightings());
      model.addAttribute("siting", siting);
      model.addAttribute("prev", "/");
      return "Sightings/editSighting";
    }

    service.updateSighting(siting);

    return "redirect:/";
  }

  @GetMapping("sightings/details")
  public String sightingDetails(Integer id, Model model) {
    model.addAttribute("sighting", service.readSighting(id));
    model.addAttribute("editLink", "/sightings/details/edit/?id=" + id);
    model.addAttribute("deleteLink", "/sightings/details/confirmDelete/?id=" + id);

    return "Sightings/sightingDetails";
  }

  @GetMapping("/sighting-details")
  public String sightingDetailsFromHome(Integer id, Model model) {
    model.addAttribute("sighting", service.readSighting(id));
    model.addAttribute("prev", "/");
    model.addAttribute("editLink", "/sighting-details/edit/?id=" + id);
    model.addAttribute("deleteLink", "/sighting-details/confirmDelete/?id=" + id);

    return "Sightings/sightingDetails";
  }

  private void ValidateSighting(@Valid Siting siting, BindingResult result,
      HttpServletRequest request) {
    String superId = request.getParameter("superId");
    try {
      siting.setSuperHuman(service.readSuper(Integer.parseInt(superId)));
    } catch (NumberFormatException e) {
      FieldError error = new FieldError("siting", "superHuman", "A super must be selected");
      result.addError(error);
    }
    String locationId = request.getParameter("locationId");
    try {
      siting.setLocation(service.readLocation(Integer.parseInt(locationId)));
    } catch (NumberFormatException e) {
      FieldError error = new FieldError("siting", "location", "A location must be selected");
      result.addError(error);
    }
    String date = request.getParameter("sitingDate");
    try {
      LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      siting.setDate(localDate);
    } catch (DateTimeParseException | NullPointerException e) {
      FieldError error = new FieldError("siting", "date", "Enter a valid date");
      result.addError(error);
    }
  }




}
