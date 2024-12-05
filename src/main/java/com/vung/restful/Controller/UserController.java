package com.vung.restful.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vung.restful.Service.UserService;
import com.vung.restful.domain.DTO.UserDTO;
import com.vung.restful.domain.Entity.User;
import com.vung.restful.util.CustomAnnotition.APImessage;
import com.vung.restful.util.CustomException.EmailExistException;
import com.vung.restful.util.CustomException.IdInvalidException;

@RequestMapping("/api/v1")
@RestController
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    
    public UserController(UserService userService ,  PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/hello/{id}")
    public User sayHelo(@PathVariable long id) throws IdInvalidException
    {
        User user = new User();
        if(id > 100){
            throw new IdInvalidException("day roi");
        }
        return null;
    }
    @APImessage("call API create user")
    @PostMapping("/create")
    public ResponseEntity<UserDTO> CreateUser(@RequestBody User user) 
    {
        boolean checkUser = this.userService.checkExitsEmail(user.getEmail());
        if(checkUser) {
            throw new EmailExistException("Email already exist");
        }

        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User newUser = this.userService.save(user);
    

       UserDTO userDTO =  this.userService.ConvertUserDTO(user);

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
    
    @GetMapping("/")
    public String getHelloWorld() {
        return "helo";
    }
    @APImessage("call API get user")
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> listUsers = this.userService.getAllUser();
        return ResponseEntity.ok().body(listUsers);
    }
}
