package kh.com.foss.sample.vo;

import kh.com.foss.sample.constant.GenderType;
import lombok.*;

@Value
@Builder
public class UserResponseVo {
    Long id;
    String phone;
    String firstName;
    String lastName;
    GenderType gender;
}
