package com.eduhub.eduhub_backend.service;

import com.eduhub.eduhub_backend.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String idString);
    User addUser(User user);
    User updatePassword(String idString, String newPassword);
    void deleteUser(String idString);
}