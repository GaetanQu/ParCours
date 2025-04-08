package fr.gaetanquenouille.parcours.mapper;

import java.util.stream.Collectors;

import fr.gaetanquenouille.parcours.DTO.HomeworkDTO;
import fr.gaetanquenouille.parcours.DTO.SessionDTO;
import fr.gaetanquenouille.parcours.DTO.UserDTO;
import fr.gaetanquenouille.parcours.model.Homework;

public class HomeworkMapper {

    public static HomeworkDTO toDTO(Homework homework) {
        HomeworkDTO homeworkDTO = new HomeworkDTO();
        homeworkDTO.setId(homework.getId());
        homeworkDTO.setLabel(homework.getLabel());
        homeworkDTO.setDescription(homework.getDescription());
        homeworkDTO.setHasDone(homework.getHasDone().stream()
            .map(userWoHasDone -> {
                UserDTO userWhoHasDoneDTO = new UserDTO();
                userWhoHasDoneDTO.setId(userWoHasDone.getId());
                userWhoHasDoneDTO.setFirst_name(userWoHasDone.getFirst_name());
                userWhoHasDoneDTO.setLast_name(userWoHasDone.getLast_name());
                userWhoHasDoneDTO.setUsername(userWoHasDone.getUsername());

                return userWhoHasDoneDTO;
            }).collect(Collectors.toList())
        );

        if (homework.getFileUrls() != null && !homework.getFileUrls().isEmpty()) {
            homeworkDTO.setFileUrls(homework.getFileUrls());            
        }

        if (homework.getUsers() != null && !homework.getUsers().isEmpty()) {
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

        if (homework.getSession() != null) {
            homeworkDTO.setSession(new SessionDTO(
                homework.getSession().getId(),
                homework.getSession().getLabel(),
                homework.getSession().getBeginsAt(),
                homework.getSession().getEndsAt(),
                homework.getSession().getFileUrls(),
                homework.getUsers().stream()
                    .map(homeworkUser -> {
                        UserDTO homeworkUserDTO = new UserDTO();
                        homeworkUserDTO.setId(homeworkUser.getId());
                        homeworkUserDTO.setFirst_name(homeworkUser.getFirst_name());
                        homeworkUserDTO.setLast_name(homeworkUser.getLast_name());
                        homeworkUserDTO.setUsername(homeworkUser.getUsername());
                        return homeworkUserDTO;
                    }).collect(Collectors.toList()),
                homework.getSession().getSubjects().stream()
                    .map(SubjectMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList()),
                null
            ));
        }

        return homeworkDTO;
    }
}
