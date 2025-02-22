package ru.ivanov.userservice.mapper;

import ru.ivanov.userservice.dto.UserDTO;
import ru.ivanov.userservice.entity.UserMain;
import ru.ivanov.userservice.entity.UserAddress;
import ru.ivanov.userservice.entity.UserContact;
import ru.ivanov.userservice.entity.UserPersonal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMain userDTOToUserMain(UserDTO userDTO);

    default UserMain toEntity(UserDTO dto) {
        UserMain userMain = new UserMain();
        userMain.setUsername(dto.getUsername());
        userMain.setEmail(dto.getEmail());

        UserPersonal personal = new UserPersonal();
        personal.setFullName(dto.getFullName());
        personal.setBirthDate(dto.getBirthDate());
        personal.setUserMain(userMain);

        UserContact contact = new UserContact();
        contact.setPhone(dto.getPhone());
        contact.setUserMain(userMain);

        UserAddress address = new UserAddress();
        address.setAddress(dto.getAddress());
        address.setUserMain(userMain);

        userMain.setUserPersonal(personal);
        userMain.setUserContact(contact);
        userMain.setUserAddress(address);

        return userMain;
    }
}
