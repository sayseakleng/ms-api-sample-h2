package kh.com.foss.sample.mapper;

import java.util.List;
import kh.com.foss.sample.dto.UserCreationInputDto;
import kh.com.foss.sample.dto.UserResultDto;
import kh.com.foss.sample.dto.UserUpdateInputDto;
import kh.com.foss.sample.vo.UserCreationRequestVo;
import kh.com.foss.sample.vo.UserResponseVo;
import kh.com.foss.sample.vo.UserUpdateRequestVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserVoMapper {
    UserCreationInputDto to(UserCreationRequestVo requestVo);

    @Mapping(target = "id", source = "userId")
    UserUpdateInputDto to(UserUpdateRequestVo RequestVo, Long userId);

    UserResponseVo from(UserResultDto resultDto);

    List<UserResponseVo> from(List<UserResultDto> resultDtos);
}
