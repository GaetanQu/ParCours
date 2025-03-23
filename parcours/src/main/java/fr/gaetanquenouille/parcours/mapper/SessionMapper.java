package fr.gaetanquenouille.parcours.mapper;

import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.model.Session;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);
    
    SessionDTO toDTO(Session session);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    Session toEntity(SessionDTO sessionDTO);
}