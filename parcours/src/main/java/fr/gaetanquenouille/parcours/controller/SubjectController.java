package fr.gaetanquenouille.parcours.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.gaetanquenouille.parcours.DTO.SubjectDTO;
import fr.gaetanquenouille.parcours.service.SubjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    // Get all subjects
    @GetMapping
    public List<SubjectDTO> getAll() {
        return subjectService.getAllSubjects();
    }

    // Get one subject by id
    @GetMapping("/{id}")
    public SubjectDTO getOne(@PathVariable Long id) {
        return subjectService.findSubjectById(id);
    }

    // Create a new subject
    @PostMapping
    public SubjectDTO create(@RequestBody SubjectDTO subjectDTO) {
        subjectService.createSubject(subjectDTO);
        return subjectDTO;
    }

    // Update subject
    @PutMapping("/{id}")
    public SubjectDTO update(@PathVariable Long id, @RequestBody SubjectDTO subjectDTO) {
        return subjectService.updateSubject(id, subjectDTO);
    }
    
    // Delete subject
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return "Subject deleted";
    }
}
