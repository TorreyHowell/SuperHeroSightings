package com.th.superherosightings.controller;

import com.th.superherosightings.Service.Service;
import com.th.superherosightings.entity.Organization;
import com.th.superherosightings.entity.SuperHuman;
import java.util.ArrayList;
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
@RequestMapping("organizations")
public class OrganizationController {

  Service service;

  public OrganizationController(Service service) {
    this.service = service;
  }

  @GetMapping
  public String viewOrganizations(Model model) {
    model.addAttribute("factions", service.readFactions());
    model.addAttribute("supers", service.readSupers());
    model.addAttribute("locations", service.readLocations());
    model.addAttribute("organizations", service.readOrganizations());
    return "Organizations/organizations";
  }

  @PostMapping("addOrganization")
  public String addOrganization(@Valid Organization organization, BindingResult result,
      HttpServletRequest request, Model model) {
    if (validateOrganization(organization, result, request, model)) {
      return "Organizations/organizations";
    }

    service.createOrganization(organization);

    return "redirect:/organizations";
  }

  @GetMapping("details")
  public String viewDetails(Integer id, Model model) {
    Organization org = service.readOrganization(id);
    model.addAttribute("organization", org);
    return "Organizations/organizationDetails";
  }

  @GetMapping("edit")
  public String editOrganization(Integer id, Model model) {
    model.addAttribute("organization", service.readOrganization(id));
    model.addAttribute("factions", service.readFactions());
    model.addAttribute("supers", service.readSupers());
    model.addAttribute("locations", service.readLocations());

    return "Organizations/editOrganization";


  }

  @PostMapping("edit")
  public String performEdit(@Valid Organization organization, BindingResult result,
      HttpServletRequest request, Model model) {
    if (validateOrganization(organization, result, request, model)) {
      return "Organizations/editOrganization";
    }
    service.updateOrganization(organization);
    return "redirect:/organizations";
  }


  @GetMapping("confirmDelete")
  public String confirmDelete(Integer id, Model model) {
    model.addAttribute("organization", service.readOrganization(id));

    return "Organizations/confirmOrganizationDelete";
  }

  @GetMapping("delete")
  public String deleteOrganization(Integer id) {
    service.deleteOrganization(id);
    return "redirect:/organizations";
  }

  private boolean validateOrganization(@Valid Organization organization, BindingResult result,
      HttpServletRequest request, Model model) {
    String[] superIds = request.getParameterValues("supers");

    List<SuperHuman> supers = new ArrayList<>();
    if (superIds != null) {
      for (String superId : superIds) {
        supers.add(service.readSuper(Integer.parseInt(superId)));
      }
      organization.setSuperHumans(supers);
    } else {
      FieldError error = new FieldError("organization", "superHumans", "A super must be selected");
      result.addError(error);
    }

    try {
      organization
          .setLocation(service.readLocation(Integer.parseInt(request.getParameter("locationId"))));
    } catch (NumberFormatException e) {
      FieldError error = new FieldError("organization", "location", "Select location");
      result.addError(error);
    }

    try {
      organization
          .setFaction(service.readFaction(Integer.parseInt(request.getParameter("factionId"))));
    } catch (NumberFormatException e) {
      FieldError error = new FieldError("organization", "faction", "A faction must be selected");
      result.addError(error);
    }

    if (result.hasErrors()) {
      model.addAttribute("factions", service.readFactions());
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("locations", service.readLocations());
      model.addAttribute("organizations", service.readOrganizations());
      model.addAttribute("organization", organization);
      return true;
    }
    return false;
  }
}
