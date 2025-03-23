package fr.gaetanquenouille.parcours.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gaetanquenouille.parcours.DTO.SubjectDTO;
import fr.gaetanquenouille.parcours.error.ResourceNotFoundException;
import fr.gaetanquenouille.parcours.mapper.SubjectMapper;
import fr.gaetanquenouille.parcours.model.Subject;
import fr.gaetanquenouille.parcours.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // Get all subjects
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
            .map(SubjectMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }

    // Get one subject by id
    public SubjectDTO findSubjectById(Long id) {
        return subjectRepository.findById(id)
            .map(SubjectMapper.INSTANCE::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Subject"));
    }

    // Create a new subject
    public Subject createSubject(SubjectDTO subjectDTO) {
        Subject subject = SubjectMapper.INSTANCE.toEntity(subjectDTO);
        return subjectRepository.save(subject);
    }

    // Update subject
    public SubjectDTO updateSubject(Long subjectId, SubjectDTO subjectDTO) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new ResourceNotFoundException("Subject"));
        subject.setLabel(subjectDTO.getLabel());
        return SubjectMapper.INSTANCE.toDTO(subjectRepository.save(subject));
    }

    // Delete subject by id
    public void deleteSubjectById(Long id) {
        if (subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject");
        }
        subjectRepository.deleteById(id);
    }
}
