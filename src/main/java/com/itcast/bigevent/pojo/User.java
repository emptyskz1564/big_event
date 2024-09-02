package com.itcast.bigevent.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String username;
    @JsonIgnore //让springMVC将其转为Json时忽略这个字段
    private String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String nickname;
    @URL
    private String userPic;

    @NotEmpty
    @Email
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
