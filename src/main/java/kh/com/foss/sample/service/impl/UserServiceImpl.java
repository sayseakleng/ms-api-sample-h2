package kh.com.foss.sample.service.impl;

import java.util.List;
import kh.com.foss.sample.dto.UserCreationInputDto;
import kh.com.foss.sample.dto.UserResultDto;
import kh.com.foss.sample.dto.UserUpdateInputDto;
import kh.com.foss.sample.entity.User;
import kh.com.foss.sample.exception.BusinessException;
import kh.com.foss.sample.mapper.UserMapper;
import kh.com.foss.sample.repository.UserRepository;
import kh.com.foss.sample.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResultDto create(@NonNull final UserCreationInputDto userCreationInputDto) throws BusinessException {
        User user = userMapper.from(userCreationInputDto);
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new BusinessException("USER-001", "User already exists");
        }
        User save = userRepository.save(user);
        return userMapper.to(save);
    }

    @Override
    @Transactional
    public UserResultDto update(@NonNull final UserUpdateInputDto userUpdateInputDto) throws BusinessException {
        User userRecord = userRepository
                .findById(userUpdateInputDto.getId())
                .orElseThrow(() -> new BusinessException("USER-002", "User not found"));
        userMapper.copy(userUpdateInputDto, userRecord);
        User save = userRepository.save(userRecord);
        return userMapper.to(save);
    }

    @Override
    public UserResultDto getById(@NonNull final Long userId) throws BusinessException {
        User userRecord =
                userRepository.findById(userId).orElseThrow(() -> new BusinessException("USER-002", "User not found"));
        return userMapper.to(userRecord);
    }

    @Override
    public List<UserResultDto> getList() {
        List<User> findAll = userRepository.findAll();
        return userMapper.to(findAll);
    }

    @Override
    @Transactional
    public void deleteById(@NonNull final Long userId) throws BusinessException {
        User userRecord =
                userRepository.findById(userId).orElseThrow(() -> new BusinessException("USER-002", "User not found"));

        userRepository.delete(userRecord);
    }
}
