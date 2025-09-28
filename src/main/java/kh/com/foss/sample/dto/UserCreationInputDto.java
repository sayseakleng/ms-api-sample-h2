package kh.com.foss.sample.dto;

import kh.com.foss.sample.constant.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationInputDto {
    private String phone;
    private String firstName;
    private String lastName;
    private GenderType gender;
}
