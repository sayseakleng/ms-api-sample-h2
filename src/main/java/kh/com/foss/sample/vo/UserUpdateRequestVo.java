package kh.com.foss.sample.vo;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kh.com.foss.sample.constant.GenderType;
import lombok.*;

@Value
@Builder
public class UserUpdateRequestVo {
    @Size(min = 9, max = 10)
    @Pattern(regexp = "^[0-9]*$", message = "Must be a valid number")
    String phone;

    String firstName;

    String lastName;

    GenderType gender;
}
