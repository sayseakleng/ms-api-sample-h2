package kh.com.foss.sample.service.impl;

import java.util.List;
import kh.com.foss.sample.dto.UserCreationInputDto;
import kh.com.foss.sample.dto.UserResultDto;
import kh.com.foss.sample.dto.UserUpdateInputDto;
import kh.com.foss.sample.entity.User;
import kh.com.foss.sample.mapper.UserMapper;
import kh.com.foss.sample.repository.UserRepository;
import kh.com.foss.sample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResultDto register(UserCreationInputDto userCreationInputDto) {
        User user = userMapper.from(userCreationInputDto);
        User save = userRepository.save(user);
        return userMapper.to(save);
    }

    @Override
    public UserResultDto update(UserUpdateInputDto userUpdateInputDto) {
        User userRecord = userRepository.getReferenceById(userUpdateInputDto.getUserId());
        userMapper.copy(userUpdateInputDto, userRecord);
        User save = userRepository.save(userRecord);
        return userMapper.to(save);
    }

    @Override
    public UserResultDto getUserByUserId(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return userMapper.to(user);
    }

    @Override
    public List<UserResultDto> getList() {
        List<User> findAll = userRepository.findAll();
        return userMapper.to(findAll);
    }
}
