package com.th.superherosightings.controller;

import com.th.superherosightings.Service.Service;
import com.th.superherosightings.entity.Faction;
import com.th.superherosightings.entity.Power;
import com.th.superherosightings.entity.SuperHuman;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("supers")
public class SuperController {

  Service service;

  public SuperController(Service service) {
    this.service = service;
  }

  @GetMapping
  public String displaySupers(Model model) {
    List<SuperHuman> supers = service.readSupers();
    List<Power> powers = service.readPowers();
    List<Faction> factions = service.readFactions();
    model.addAttribute("supers", supers);
    model.addAttribute("factions", factions);
    model.addAttribute("powers", powers);
    return "Supers/supers";
  }

  @PostMapping("/addSuper")
  public String addSuper(@Valid SuperHuman superHuman, BindingResult result,
      HttpServletRequest request, @RequestParam("file") MultipartFile file, Model model) {

    this.checkSuper(superHuman, result, request);

    if (result.hasErrors()) {
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("factions", service.readFactions());
      model.addAttribute("powers", service.readPowers());
      model.addAttribute("superHuman", superHuman);
      return "Supers/supers";
    }

    service.createSuper(superHuman, file);

    return "redirect:/supers";
  }




  @GetMapping("edit")
  public String editSuper(Integer id, Model model) {
    model.addAttribute("superHuman", service.readSuper(id));
    model.addAttribute("factions", service.readFactions());
    model.addAttribute("powers", service.readPowers());
    return "Supers/editSuper";
  }

  @PostMapping("edit")
  public String performSuperEdit(@Valid SuperHuman superHuman, BindingResult result,
      HttpServletRequest request,@RequestParam("file") MultipartFile file, Model model) {
    checkSuper(superHuman, result, request);
    if (result.hasErrors()) {
      model.addAttribute("supers", service.readSupers());
      model.addAttribute("factions", service.readFactions());
      model.addAttribute("powers", service.readPowers());
      model.addAttribute("superHuman", superHuman);
      return "Supers/editSuper";
    }

    service.updateSuper(superHuman, file);
    return "redirect:/supers";
  }


  @GetMapping("details")
  public String displayDetails(Integer id, Model model) {
    SuperHuman superHuman = service.readSuper(id);
    model.addAttribute("super", superHuman);
    model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    model.addAttribute("sightings", service.readSightingsForSuper(superHuman));
    model.addAttribute("organizations", service.readOrganizationsForSuper(superHuman));
    return "Supers/superDetails";
  }

  @GetMapping("confirmDelete")
  public String confirmDeleteSuper(Integer id, Model model) {
    model.addAttribute("super", service.readSuper(id));
    return "Supers/confirmSuperDelete";
  }

  @GetMapping("delete")
  public String deleteSuper(Integer id) {
    service.deleteSuper(id);
    return "redirect:/supers";
  }

  @RequestMapping(value = "/image/{imageName}")
  @ResponseBody
  public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
    File image = new File("src/main/resources/Images/" + imageName);

    return Files.readAllBytes(image.toPath());
  }

  private void checkSuper(@Valid SuperHuman superHuman, BindingResult result,
      HttpServletRequest request) {
    String factionId = request.getParameter("factionId");

    String[] powerIds = request.getParameterValues("powers");
    try {
      superHuman.setFaction(service.readFaction(Integer.parseInt(factionId)));
    } catch (NumberFormatException e) {
      FieldError error = new FieldError("superHuman", "faction", "A faction is required");
      result.addError(error);
    }

    List<Power> powers = new ArrayList<>();

    if (powerIds != null) {
      for (String powerId : powerIds) {
        powers.add(service.readPower(Integer.parseInt(powerId)));
      }
    } else {
      FieldError error = new FieldError("superHuman", "power", "At least one power is required");
      result.addError(error);
    }
    superHuman.setPower(powers);
  }


}
