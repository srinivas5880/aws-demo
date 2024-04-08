package com.sree.controller;

import com.sree.entity.User;
import com.sree.exception.ResourceNotFoundException;
import com.sree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    // get all users
    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long userId){
        return this.userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User not found with user id"+userId));
    }

    // create user
    @PostMapping("/create")
    public User cretaeUser(@RequestBody User user){
        return this.userRepository.save(user);
    }

    // update user
   @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") long userId, @RequestBody User user){

       User existingUser = this.userRepository.findById(userId).
               orElseThrow(()-> new ResourceNotFoundException("User not found with user id"+userId));
       existingUser.setFirstName(user.getFirstName());
       existingUser.setLastName(user.getLastName());
       existingUser.setEmail(user.getEmail());
       return this.userRepository.save(existingUser);
    }

    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long userId) {
        User existingUser = this.userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User not found with user id"+userId));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();

    }
}
