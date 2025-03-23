package fr.gaetanquenouille.parcours.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.gaetanquenouille.parcours.DTO.SubjectDTO;
import fr.gaetanquenouille.parcours.model.Subject;

@Mapper
public interface SubjectMapper {
    SubjectMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(SubjectMapper.class);

    SubjectDTO toDTO(Subject subject);

    @Mapping(target = "sessions", ignore = true)
    Subject toEntity(SubjectDTO subjectDTO);
}
