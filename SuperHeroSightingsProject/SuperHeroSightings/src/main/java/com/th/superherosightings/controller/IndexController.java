package com.th.superherosightings.controller;

import com.th.superherosightings.Service.Service;
import com.th.superherosightings.entity.Siting;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  Service service;

  public IndexController(Service service) {
    this.service = service;
  }

  @GetMapping("/")
  public String displaySightings(Model model) {
    List<Siting> sightings = service.readRecentSightings();
    model.addAttribute("sightings", sightings);
    model.addAttribute("dateFormatter", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    return "index";
  }
}
