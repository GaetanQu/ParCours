package fr.gaetanquenouille.parcours.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import fr.gaetanquenouille.parcours.DTO.HomeworkDTO;
import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.model.Session;

/* 
 * Example : 
 * {
 *      id
 *      label
 *      beginsAt
 *      endsAt
 *      fileUrls []
 *      subjects [
 *          {
 *              id
 *              label
 *          }
 *      ]
 *      users [
 *          id
 *          firstname
 *          lastname
 *          username
 *          roles [
 *              id
 *              name
 *          ]
 *          
 *      ]
 *      homeworks [
 *          {
 *              id
 *              label
 *              description
 *              fileUrls []
 *              user {
 *                  id
 *                  firstname
 *                  lastname
 *                  username
 *              }
 *          }
 *      ]
 * }
 */

 @Mapper
 @Component
public class SessionMapper {
    
    public SessionDTO toDTO(Session session) {
        SessionDTO sessionDTO = new SessionDTO();

        sessionDTO.setId(session.getId());
        sessionDTO.setLabel(session.getLabel());
        sessionDTO.setBeginsAt(session.getBeginsAt());
        sessionDTO.setEndsAt(session.getEndsAt());
        sessionDTO.setFileUrls(session.getFileUrls());

        sessionDTO.setUsers(session.getUsers().stream()
            .map(user -> {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setFirst_name(user.getFirst_name());
                userDTO.setLast_name(user.getLast_name());
                userDTO.setUsername(user.getUsername());
                userDTO.setRoles(
                    user.getRoles().stream()
                    .map(RoleMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList())
                );
                return userDTO;
            })
            .collect(Collectors.toList())
        );

        if (session.getSubjects() != null && !session.getSubjects().isEmpty()) {
            sessionDTO.setSubjects(
                session.getSubjects().stream()
                .map(SubjectMapper.INSTANCE::toDTO)
                .collect(Collectors.toList())
            );
        }

        if (session.getHomeworks() != null && !session.getHomeworks().isEmpty()) {
            sessionDTO.setHomeworks(session.getHomeworks().stream()
                .map(homework-> {
                    HomeworkDTO homeworkDTO = new HomeworkDTO();
                    homeworkDTO.setId(homework.getId());
                    homeworkDTO.setLabel(homework.getLabel());
                    homeworkDTO.setDescription(homework.getDescription());
                    homeworkDTO.setFileUrls(homework.getFileUrls());
                    homeworkDTO.setSubject(SubjectMapper.INSTANCE.toDTO(homework.getSubject()));
                    
                    if (!homework.getUsers().isEmpty()) {
                        homeworkDTO.setUsers(homework.getUsers().stream()
                            .map(user -> {
                                UserDTO userDTO = new UserDTO();
                                userDTO.setId(user.getId());
                                userDTO.setFirst_name(user.getFirst_name());
                                userDTO.setLast_name(user.getLast_name());
                                userDTO.setUsername(user.getUsername());

                                return userDTO;
                            }).collect(Collectors.toList())
                        );
                    }
                    return homeworkDTO;
                })
                .collect(Collectors.toList())
            );
        }
        
        return sessionDTO;
    }
}
