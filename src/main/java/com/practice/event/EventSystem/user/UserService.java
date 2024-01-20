package com.practice.event.EventSystem.user;

import com.practice.event.EventSystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) throw new IllegalArgumentException("User with specified email not found");
        return optionalUser.get();
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) throw new IllegalArgumentException("User with specified ID not found");
        return optionalUser.get();
    }

    public void addUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("Email already in use");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(userRepository::delete);
    }

    public void updateStudent(Long studentId, String name, String email, LocalDate birth) {
        Optional<User> userOptional = userRepository.findById(studentId);
        if(userOptional.isEmpty()) throw new IllegalStateException("Student not found");
        User user = userOptional.get();
        if(name != null) user.setName(name);
        if(birth != null) user.setBirthDate(birth);
        if (email != null && userRepository.findByEmail(email).isEmpty()) user.setEmail(email);

        userRepository.save(user);
    }
}
