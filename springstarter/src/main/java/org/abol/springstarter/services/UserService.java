package org.abol.springstarter.services;

import org.abol.springstarter.models.BaseUser;

import java.util.List;

public interface UserService {
    void saveUser(BaseUser user);
    BaseUser getUserByIndex(int index);
    List<BaseUser> getAllUsers();
    void updateUser(int index, BaseUser user);
    void deleteUser(int index);
    BaseUser getUserByEmail(String email);
    BaseUser getUserById(int id);
}
