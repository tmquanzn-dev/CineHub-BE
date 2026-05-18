package com.example.cinehub.service.impl;

import com.example.cinehub.dto.UserDTO;
import com.example.cinehub.entity.User;
import com.example.cinehub.repository.UserRepository;
import com.example.cinehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users =  userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy User với id = " +id));
        return convertToDTO(user);
    }

    @Override
    public UserDTO createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }
        if(userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email đã tồn tại");
        return convertToDTO(userRepository.save(user));
    }


    @Override
    public UserDTO updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy User với id = " + id));
        user.setAvatarUrl(userDetails.getAvatarUrl());
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy User với id = " + id));
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername((user.getUsername()));
        userDTO.setEmail(user.getEmail());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        userDTO.setRole(user.getRole());
        userDTO.setCreateAt(user.getCreateAt());
        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
