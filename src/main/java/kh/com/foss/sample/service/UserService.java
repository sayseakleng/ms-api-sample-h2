package kh.com.foss.sample.service;

import java.util.List;
import kh.com.foss.sample.dto.UserCreationInputDto;
import kh.com.foss.sample.dto.UserResultDto;
import kh.com.foss.sample.dto.UserUpdateInputDto;
import kh.com.foss.sample.exception.BusinessException;
import lombok.NonNull;

public interface UserService {
    UserResultDto create(@NonNull final UserCreationInputDto userCreationInputDto) throws BusinessException;

    UserResultDto update(@NonNull final UserUpdateInputDto userUpdateInputDto) throws BusinessException;

    UserResultDto getById(@NonNull final Long userId) throws BusinessException;

    List<UserResultDto> getList();

    void deleteById(@NonNull final Long userId) throws BusinessException;
}
