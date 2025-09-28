package kh.com.foss.sample.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kh.com.foss.sample.constant.GenderType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreationRequestVo {
    @NotBlank
    @Size(min = 9, max = 10)
    @Pattern(regexp = "^[0-9]*$", message = "Must be a valid number")
    String phone;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotNull
    GenderType gender;
}
