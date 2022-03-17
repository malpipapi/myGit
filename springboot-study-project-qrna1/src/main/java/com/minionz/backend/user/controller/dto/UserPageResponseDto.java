package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.dto.AddressDto;
import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPageResponseDto extends MyPageResponseDto{

    private AddressDto address;

    public UserPageResponseDto(User user){
        super(user.getNickName(),user.getTelNumber());
        this.address=new AddressDto();
    }
}
