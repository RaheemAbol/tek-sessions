package org.abol.springstarter.services;

import org.abol.springstarter.models.BaseUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final List<BaseUser> users = new ArrayList<>();

    @Override
    public void saveUser(BaseUser user) {
        users.add(user);
    }

    @Override
    public BaseUser getUserByIndex(int index) {
        return (index >= 0 && index < users.size()) ? users.get(index) : null;
    }

    @Override
    public List<BaseUser> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void updateUser(int index, BaseUser user) {
        if (index >= 0 && index < users.size()) {
            users.set(index, user);
        }
    }

    @Override
    public void deleteUser(int index) {
        if (index >= 0 && index < users.size()) {
            users.remove(index);
        }
    }

    @Override
    public BaseUser getUserByEmail(String email) {
        return users.stream().filter(user -> email.equals(user.getEmail())).findFirst().orElse(null);
    }

    @Override
    public BaseUser getUserById(int id) {
        return (id >= 0 && id < users.size()) ? users.get(id) : null;
    }
}
