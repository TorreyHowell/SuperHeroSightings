package com.th.superherosightings.controller;

import com.th.superherosightings.Service.Service;
import com.th.superherosightings.entity.Power;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("powers")
public class PowerController {

  Service service;

  public PowerController(Service service) {
    this.service = service;
  }

  @GetMapping
  public String displayPowers(Model model) {
    List<Power> powers = service.readPowers();
    model.addAttribute("powers", powers);
    model.addAttribute("power", new Power());
    return "Powers/powers";
  }

  @GetMapping("details")
  public String displayPowerDetails(Integer id, Model model) {
    Power power = service.readPower(id);
    model.addAttribute("supers", service.readSupersForPower(power));
    model.addAttribute("power", power);
    return "Powers/powerDetails";
  }

  @PostMapping("addPower")
  public String addPower(@Valid Power power, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("powers", service.readPowers());
      model.addAttribute("power", power);
      return "Powers/powers";
    }
    service.createPower(power);
    return "redirect:/powers";
  }

  @GetMapping("confirmDelete")
  public String confirmDelete(Integer id, Model model) {
    Power power = service.readPower(id);
    model.addAttribute("power", power);
    return "Powers/confirmPowerDelete";
  }

  @GetMapping("edit")
  public String editPower(Integer id, Model model) {
    Power power = service.readPower(id);
    model.addAttribute("power", power);
    return "Powers/editPower";
  }

  @GetMapping("details/edit")
  public String editPowerFromDetails(Integer id, Model model) {
    Power power = service.readPower(id);
    model.addAttribute("power", power);
    return "Powers/editPowerFromDetails";
  }

  @PostMapping("/details/edit")
  public String performEditPowerFromDetails(@Valid Power power, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("power", power);
      return "Powers/editPower";
    }
    service.updatePower(power);
    return "redirect:/powers/details/?id=" + power.getId();
  }

  @PostMapping("/edit")
  public String performEditPower(@Valid Power power, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("power", power);
      return "Powers/editPower";
    }
    service.updatePower(power);
    return "redirect:/powers";
  }

  @GetMapping("delete")
  public String deletePower(Integer id) {
    service.deletePower(id);
    return "redirect:/powers";
  }


}