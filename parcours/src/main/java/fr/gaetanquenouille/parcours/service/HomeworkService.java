package fr.gaetanquenouille.parcours.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gaetanquenouille.parcours.DTO.HomeworkDTO;
import fr.gaetanquenouille.parcours.mapper.HomeworkMapper;
import fr.gaetanquenouille.parcours.repository.HomeworkRepository;

@Service
public class HomeworkService {
    
    @Autowired
    private HomeworkRepository homeworkRepository;

    // Get all homeworks
    public List<HomeworkDTO> getAllHomeworks(){
        return homeworkRepository.findAll().stream()
            .map(HomeworkMapper::toDTO)
            .collect(Collectors.toList());
    }
}
