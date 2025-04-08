package fr.gaetanquenouille.parcours.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.gaetanquenouille.parcours.DTO.HomeworkDTO;
import fr.gaetanquenouille.parcours.DTO.RoleDTO;
import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.DTO.SubjectDTO;
import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.model.Role;
import fr.gaetanquenouille.parcours.model.User;
import fr.gaetanquenouille.parcours.repository.RoleRepository;

/*
 * Example :
 * {
 *      id
 *      firstname
 *      lastname
 *      username
 *      roles [
 *          {
 *              id
 *              name
 *          }
 *      ]
 *      sessions [
 *          {
 *              id
 *              label
 *              beginsAt
 *              endsAt
 *              fileUrls []
 *              subjects [
 *                  {
 *                      id
 *                      label
 *                  }
 *              ]
 *              homeworks [
 *                  id
 *                  label
 *                  description
 *                  fileUrls []
 *                  subjects {
 *                      id
 *                      label
 *                  }
 *              ]
 *          }
 *      ]
 * }
 */

@Mapper
@Component
public class UserMapper {

    @Autowired
    RoleRepository roleRepository;

    public UserDTO toDTO(User user) {
        Role teacherRole = roleRepository.findByName("ROLE_TEACHER");

        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirst_name(user.getFirst_name());
        userDTO.setLast_name(user.getLast_name());
        userDTO.setUsername(user.getFirst_name() + user.getLast_name());

        if ( user.getRoles() != null && !user.getRoles().isEmpty()) {
            userDTO.setRoles(user.getRoles().stream()
                .map(role -> {
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setId(role.getId());
                    roleDTO.setName(role.getName());
                    return roleDTO;
                })
                .collect(Collectors.toList()));
        }

        if (user.getSessions() != null && !user.getSessions().isEmpty()) {
            userDTO.setSessions(user.getSessions().stream()
                .map(session -> {
                    SessionDTO sessionDTO = new SessionDTO();
                    sessionDTO.setId(session.getId());
                    sessionDTO.setLabel(session.getLabel());
                    sessionDTO.setBeginsAt(session.getBeginsAt());
                    sessionDTO.setEndsAt(session.getEndsAt());
                    sessionDTO.setFileUrls(session.getFileUrls());
                    sessionDTO.setSubjects(session.getSubjects().stream()
                        .map(SubjectMapper.INSTANCE::toDTO)
                        .collect(Collectors.toList()));

                    if (session.getHomeworks() != null && !session.getHomeworks().isEmpty()) {
                        sessionDTO.setHomeworks(session.getHomeworks().stream()
                            .map(homework -> {
                                if (!homework.getUsers().contains(user) && !user.getRoles().contains(teacherRole)) {
                                    return null;
                                }

                                HomeworkDTO homeworkDTO = new HomeworkDTO();
                                homeworkDTO.setId(homework.getId());
                                homeworkDTO.setLabel(homework.getLabel());
                                homeworkDTO.setDescription(homework.getDescription());
                                homeworkDTO.setFileUrls(homework.getFileUrls());
                                homeworkDTO.setSubject(new SubjectDTO(homework.getSubject().getId(), homework.getSubject().getLabel()));
                                homeworkDTO.setUsers(homework.getUsers().stream()
                                    .map(homeworkUser -> {
                                        UserDTO homworkUserDTO = new UserDTO();
                                        homworkUserDTO.setId(homeworkUser.getId());
                                        homworkUserDTO.setFirst_name(homeworkUser.getFirst_name());
                                        homworkUserDTO.setLast_name(homeworkUser.getLast_name());
                                        homworkUserDTO.setUsername(homeworkUser.getUsername());

                                        return homworkUserDTO;
                                    }).collect(Collectors.toList())
                                );
                                return homeworkDTO;
                            }).collect(Collectors.toList())
                        );
                    }
                    return sessionDTO;
                }).collect(Collectors.toList()));
        }
        return userDTO;
    }
}
