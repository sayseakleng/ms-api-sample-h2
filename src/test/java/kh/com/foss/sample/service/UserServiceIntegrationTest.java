package kh.com.foss.sample.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import kh.com.foss.sample.constant.GenderType;
import kh.com.foss.sample.dto.UserCreationInputDto;
import kh.com.foss.sample.dto.UserResultDto;
import kh.com.foss.sample.dto.UserUpdateInputDto;
import kh.com.foss.sample.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Tag("integration")
@TestMethodOrder(OrderAnnotation.class)
class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    void create() throws BusinessException {
        log.info("--> Start create()");
        UserCreationInputDto userCreationInputDto = new UserCreationInputDto();
        userCreationInputDto.setFirstName("Dara");
        userCreationInputDto.setLastName("Sok");
        userCreationInputDto.setGender(GenderType.M);
        userCreationInputDto.setPhone("011111111");
        UserResultDto userResultDto = userService.create(userCreationInputDto);
        assertNotNull(userResultDto);
        log.info("<-- End create() with result: {}", userResultDto);
    }

    @Test
    void update() throws BusinessException {
        log.info("--> Start update()");
        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setId(1L);
        userUpdateInputDto.setLastName("Dara 2");
        UserResultDto update = userService.update(userUpdateInputDto);
        assertNotNull(update);
        log.info("<-- End update() with result: {}", update);
    }

    @Test
    void getUserByUserId() throws BusinessException {
        log.info("--> Start getUserByUserId()");
        UserResultDto userByUserId = userService.getById(1L);
        assertNotNull(userByUserId);
        log.info("<-- End getUserByUserId() with result: {}", userByUserId);
    }
}
