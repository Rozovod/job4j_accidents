package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;


@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentService.getAccidentTypes());
        return "accidents/create";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model, @RequestParam("type.id") int typeId) {
        AccidentType type = accidentService.findTypeById(typeId);
        accident.setType(type);
        boolean isCreated = accidentService.create(accident);
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
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("accident", accidentOptional.get());
        return "accidents/edit";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam("type.id") int typeId, Model model) {
        AccidentType type = accidentService.findTypeById(typeId);
        accident.setType(type);
        var isUpdated = accidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute("message", "Инцидент не обновлен. Попробуйте еще раз.");
            return "errors/404";
        }
        model.addAttribute("message", "Инцидент обновлен успешно");
        return "success/success";
    }
}
