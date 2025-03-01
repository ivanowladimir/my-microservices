package ru.ivanov.userservice.mapper;

import org.mapstruct.Mapper;
import ru.ivanov.userservice.dto.UserDTO;
import ru.ivanov.userservice.entity.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMain userDTOToUserMain(UserDTO userDTO);

    default UserMain toEntity(UserDTO dto) {
        UserMain userMain = new UserMain();
        userMain.setUsername(dto.getUsername());
        userMain.setEmail(dto.getEmail());

        UserPersonal personal = new UserPersonal();
        personal.setLastName(dto.getLastName());
        personal.setFirstName(dto.getFirstName());
        personal.setMiddleName(dto.getMiddleName());
        personal.setBirthDate(dto.getBirthDate());
        personal.setBirthPlace(dto.getBirthPlace());
        personal.setUserMain(userMain);

        UserContact contact = new UserContact();
        contact.setPhone(dto.getPhone());
        contact.setActive(dto.isContactIsActive());
        contact.setUserMain(userMain);

        UserAddress address = new UserAddress();
        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHouse(dto.getHouse());
        address.setActive(dto.isAddressIsActive());
        address.setUserMain(userMain);

        UserDocument document = new UserDocument();
        document.setDocumentType(dto.getDocumentType());
        document.setSeries(dto.getSeries());
        document.setNumber(dto.getNumber());
        document.setIssuedBy(dto.getIssuedBy());
        document.setIssuedDate(dto.getIssuedDate());
        document.setActive(dto.isDocumentIsActive());
        document.setUserMain(userMain);

        userMain.setUserPersonal(personal);
        userMain.setUserContact(contact);
        userMain.setUserAddress(address);
        userMain.setUserDocument(document);

        return userMain;
    }
}