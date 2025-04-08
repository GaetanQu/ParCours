package fr.gaetanquenouille.parcours.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.gaetanquenouille.parcours.DTO.SubjectDTO;
import fr.gaetanquenouille.parcours.model.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    static SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);
    
    SubjectDTO toDTO(Subject subject);

    Subject toEntity(SubjectDTO subjectDTO);
}
