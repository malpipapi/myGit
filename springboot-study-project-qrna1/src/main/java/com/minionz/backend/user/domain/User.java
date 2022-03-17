package com.minionz.backend.user.domain;

import com.minionz.backend.common.domain.Address;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "user_id")),
        @AttributeOverride(name = "name", column = @Column(name = "user_name"))
})
@Entity
public class User extends UserBaseEntity {

    @Embedded
    private Address address;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Builder
    public User(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String name, String email, String password, String nickName, String telNumber, Address address,String weight, String height, String gender) {
        super(id, createdDate, modifiedDate, name, email, password, telNumber,weight,height,gender);
        this.address = address;
        this.nickName = nickName;
    }
}
