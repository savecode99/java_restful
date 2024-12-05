package com.vung.restful.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.vung.restful.Repository.UserRepository;
import com.vung.restful.domain.DTO.UserDTO;
import com.vung.restful.domain.Entity.User;
@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    public UserService(UserRepository userRepository , ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User save(User user)
    {
        return this.userRepository.save(user);
    }
    public User getUerByEmail(String Email)
    {
        return this.userRepository.findByEmail(Email);
    }
    public List<User> getAllUser()
    {
        return this.userRepository.findAll();
    }
    public boolean checkExitsEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    public UserDTO ConvertUserDTO(User user) {
        // Lấy User entity ra từ DB
        
        // Map thành DTO
        UserDTO userDto = this.modelMapper.map(user, UserDTO.class);
        return userDto;
    }
}
