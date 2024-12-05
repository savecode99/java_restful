package com.vung.restful.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResLoginDTO {

    private String accessToken;
    private UserLogin user;
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserLogin{
        private String email;
        private String username;
    }
    
}
