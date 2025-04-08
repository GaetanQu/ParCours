package fr.gaetanquenouille.parcours.controller;

import java.util.List;
import fr.gaetanquenouille.parcours.service.HomeworkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import fr.gaetanquenouille.parcours.DTO.HomeworkDTO;


@RestController
@RequestMapping("/api/homeworks")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;
    
    @GetMapping
    public List<HomeworkDTO> getAllHomeworks() {
        return homeworkService.getAllHomeworks();
    }
    
}
