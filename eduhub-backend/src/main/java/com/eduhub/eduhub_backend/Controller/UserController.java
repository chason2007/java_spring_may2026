package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.model.User;
import com.eduhub.eduhub_backend.exception.ResourceNotFoundException;
import com.eduhub.eduhub_backend.exception.MethodNotAllowedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final List<User> userList = new ArrayList<>();
    private static final Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");

    static {
        userList.add(new User("1", "user1", "pass1"));
        userList.add(new User("2", "user2", "pass2"));
        userList.add(new User("3", "user3", "pass3"));
        userList.add(new User("4", "user4", "pass4"));
        userList.add(new User("5", "user5", "pass5"));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByPathVariable(@PathVariable String userId) {
        if (specialCharPattern.matcher(userId).find()) {
            throw new IllegalArgumentException("User ID cannot contain special characters.");
        }
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByRequestParam(@RequestParam String userId) {
        if (specialCharPattern.matcher(userId).find()) {
            throw new IllegalArgumentException("User ID cannot contain special characters.");
        }
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        userList.add(newUser);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable String userId, @RequestBody String newPassword) {
        Optional<User> userOptional = userList.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            return ResponseEntity.ok("Password updated successfully");
        }
        throw new ResourceNotFoundException("User not found with id: " + userId);
    }

    @DeleteMapping("/{userId}/password")
    public ResponseEntity<String> removePassword(@PathVariable String userId) {
        boolean removed = false;
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUserId().equals(userId)) {
                user.setPassword(null);
                removed = true;
                break;
            }
        }
        if (!removed) {
             throw new MethodNotAllowedException("Failed to remove password, user not found.");
        }
        return ResponseEntity.ok("Password removed successfully");
    }
}