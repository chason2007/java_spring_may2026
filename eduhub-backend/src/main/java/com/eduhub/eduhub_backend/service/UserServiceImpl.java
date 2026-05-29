package com.eduhub.eduhub_backend.service;

import com.eduhub.eduhub_backend.exception.MethodNotAllowedException;
import com.eduhub.eduhub_backend.exception.ResourceNotFoundException;
import com.eduhub.eduhub_backend.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final List<User> userList = new ArrayList<>();
    private static final Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");

    static {
        userList.add(new User(1, "john.doe", "password123"));
        userList.add(new User(2, "jane.smith", "securepass"));
        userList.add(new User(3, "alice.jones", "mypassword"));
        userList.add(new User(4, "bob.brown", "userpass"));
        userList.add(new User(5, "charlie.davis", "pass123"));
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public User getUserById(String idString) {
        if (specialCharPattern.matcher(idString).find()) {
            throw new IllegalArgumentException("User ID cannot contain special characters.");
        }
        long userId = Long.parseLong(idString);
        return userList.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public User addUser(User user) {
        userList.add(user);
        return user;
    }

    @Override
    public User updatePassword(String idString, String newPassword) {
        User userToUpdate = getUserById(idString);
        userToUpdate.setPassword(newPassword);
        return userToUpdate;
    }

    @Override
    public void deleteUser(String idString) {
        User userToDelete = getUserById(idString);
        if (!userList.remove(userToDelete)) {
            throw new MethodNotAllowedException("Deletion failed for user ID: " + idString);
        }
    }
}