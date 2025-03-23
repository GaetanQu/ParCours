package fr.gaetanquenouille.parcours.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.gaetanquenouille.parcours.DTO.RoleDTO;
import fr.gaetanquenouille.parcours.model.Role;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(Role role);

    Role toEntity(RoleDTO roleDTO);
}
