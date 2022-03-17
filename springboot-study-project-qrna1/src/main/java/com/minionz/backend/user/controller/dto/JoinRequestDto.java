package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequestDto {

    private String name;
    private String email;
    private String nickName;
    private String telNumber;
    private String password;
    private Address address;
    private String weight;
    private String height;
    private String gender;

    @Builder
    public JoinRequestDto(String name, String email, String nickName, String telNumber, String password, Address address,String weight,String height,String gender) {
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.telNumber = telNumber;
        this.password = password;
        this.address = address;
        this.weight = weight;
        this.height = height;
        this.gender =gender;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(this.name)
                .nickName(this.nickName)
                .password(passwordEncoder.encode(this.password))
                .email(this.email)
                .telNumber(this.telNumber)
                .address(this.address)
                .weight(this.weight)
                .height(this.height)
                .gender(this.gender)
                .build();
    }
}
