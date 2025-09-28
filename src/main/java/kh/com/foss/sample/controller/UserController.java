package kh.com.foss.sample.controller;

import java.util.List;
import kh.com.foss.sample.dto.UserCreationInputDto;
import kh.com.foss.sample.dto.UserResultDto;
import kh.com.foss.sample.dto.UserUpdateInputDto;
import kh.com.foss.sample.exception.BusinessException;
import kh.com.foss.sample.mapper.UserVoMapper;
import kh.com.foss.sample.service.UserService;
import kh.com.foss.sample.vo.UserCreationRequestVo;
import kh.com.foss.sample.vo.UserResponseVo;
import kh.com.foss.sample.vo.UserUpdateRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserVoMapper userVoMapper;

    @PostMapping
    public UserResponseVo create(@Validated @RequestBody UserCreationRequestVo requestVo) throws BusinessException {
        UserCreationInputDto userCreationInputDto = userVoMapper.to(requestVo);
        UserResultDto userResultDto = userService.create(userCreationInputDto);
        return userVoMapper.from(userResultDto);
    }

    @GetMapping
    public List<UserResponseVo> getList() {
        List<UserResultDto> findAll = userService.getList();
        return userVoMapper.from(findAll);
    }

    @GetMapping("/{userId}")
    public UserResponseVo getById(@PathVariable Long userId) throws BusinessException {
        UserResultDto userResultDto = userService.getById(userId);
        return userVoMapper.from(userResultDto);
    }

    @PutMapping("/{userId}")
    public UserResponseVo update(@PathVariable Long userId, @Validated @RequestBody UserUpdateRequestVo requestVo)
            throws BusinessException {
        UserUpdateInputDto userUpdateInputDto = userVoMapper.to(requestVo, userId);
        UserResultDto userResultDto = userService.update(userUpdateInputDto);
        return userVoMapper.from(userResultDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable Long userId) throws BusinessException {
        userService.deleteById(userId);
    }
}
