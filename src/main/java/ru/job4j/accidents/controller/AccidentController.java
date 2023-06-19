package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;


@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getRules());
        return "accidents/create";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model,
                       @RequestParam("rIds") List<Integer> rIds,
                       @RequestParam("type.id") int typeId) {
        boolean isCreated = accidentService.create(accident, typeId, rIds);
        if (!isCreated) {
            model.addAttribute("message", "Инцидент не добавлен. Попробуйте еще раз.");
            return "errors/409";
        }
        model.addAttribute("message", "Инцидент добавлен успешно");
        return "success/success";
    }

    @GetMapping("/editAccident/{id}")
    public String viewEditAccident(@PathVariable int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден. Попробуйте выбрать другой.");
            return "errors/404";
        }
        model.addAttribute("types", accidentTypeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getRules());
        model.addAttribute("accident", accidentOptional.get());
        return "accidents/edit";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, Model model,
                         @RequestParam("type.id") int typeId,
                         @RequestParam("rIds") List<Integer> rIds) {
        var isUpdated = accidentService.update(accident, typeId, rIds);
        if (!isUpdated) {
            model.addAttribute("message", "Инцидент не обновлен. Попробуйте еще раз.");
            return "errors/404";
        }
        model.addAttribute("message", "Инцидент обновлен успешно");
        return "success/success";
    }
}
