package com.vung.restful.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vung.restful.Service.UserService;
import com.vung.restful.domain.DTO.LoginDTO;
import com.vung.restful.domain.DTO.ResLoginDTO;
import com.vung.restful.domain.Entity.User;
import com.vung.restful.util.CustomAnnotition.APImessage;
import com.vung.restful.util.SecurityUtil;

import jakarta.validation.Valid;

@RequestMapping("/api/v1")
@RestController
public class AuthController {
    @Value("${vung.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpairation;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private SecurityUtil sercurityUtil;
    private UserService userService;
    public AuthController (AuthenticationManagerBuilder authenticationManagerBuilder,
    SecurityUtil sercurityUtil,
    UserService userService)
    {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.sercurityUtil = sercurityUtil;
        this.userService =userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody  LoginDTO loginDTO)
    {
        User user = this.userService.getUerByEmail(loginDTO.getUsername());
        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResLoginDTO.UserLogin userdetail = new ResLoginDTO.UserLogin( user.getEmail() , user.getName());

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        
        resLoginDTO.setUser(userdetail);
        String accessToken =  this.sercurityUtil.createAccessToken(authentication, resLoginDTO);
        resLoginDTO.setAccessToken(accessToken);
        
        String refreshToken =  this.sercurityUtil.createRefreshToken(loginDTO.getUsername(), resLoginDTO);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(refreshTokenExpairation)
        .build();

        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(resLoginDTO);
    }
    
    @APImessage("get information userLogin")
    @GetMapping("/account")
    public ResponseEntity<ResLoginDTO.UserLogin> getUser() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        User user = this.userService.getUerByEmail(email);
        ResLoginDTO.UserLogin userLogin =new ResLoginDTO.UserLogin(user.getEmail() , user.getName());

        return ResponseEntity.ok().body(userLogin);
    }
    
}
