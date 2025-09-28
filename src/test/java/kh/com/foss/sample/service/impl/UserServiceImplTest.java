package kh.com.foss.sample.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kh.com.foss.sample.constant.GenderType;
import kh.com.foss.sample.dto.UserCreationInputDto;
import kh.com.foss.sample.dto.UserResultDto;
import kh.com.foss.sample.dto.UserUpdateInputDto;
import kh.com.foss.sample.entity.User;
import kh.com.foss.sample.exception.BusinessException;
import kh.com.foss.sample.mapper.UserMapper;
import kh.com.foss.sample.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Implementation Tests")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Common test data
        testUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phone("011111111")
                .gender(GenderType.M)
                .build();
    }

    @Nested
    @DisplayName("Create User Tests")
    class CreateUserTests {
        private UserCreationInputDto createDto;

        @BeforeEach
        void setUp() {
            // Common test data
            createDto = UserCreationInputDto.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .phone("011111111")
                    .gender(GenderType.M)
                    .build();
        }

        @Test
        @DisplayName("Create user with valid data should return created user")
        void givenValidUser_whenCreate_thenReturnCreatedUser() throws BusinessException {
            // Given
            when(userRepository.existsByPhone(anyString())).thenReturn(false);
            when(userRepository.save(any(User.class))).thenReturn(testUser);

            // When
            UserResultDto createdUser = userService.create(createDto);

            // Then
            assertThat(createdUser)
                    .isNotNull()
                    .extracting(UserResultDto::getFirstName, UserResultDto::getLastName)
                    .containsExactly("John", "Doe");

            verify(userRepository).existsByPhone(createDto.getPhone());
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("Create user with existing phone should throw BusinessException")
        void givenExistingPhone_whenCreate_thenThrowBusinessException() {
            // Given
            when(userRepository.existsByPhone(anyString())).thenReturn(true);

            // When
            BusinessException exception = assertThrows(BusinessException.class, () -> userService.create(createDto));

            // Then
            assertEquals("USER-001", exception.getCode());
            verify(userRepository, never()).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("Update User Tests")
    class UpdateUserTests {
        private UserUpdateInputDto updateDto;

        @BeforeEach
        void setUp() {
            updateDto = UserUpdateInputDto.builder()
                    .id(1L)
                    .firstName("John")
                    .lastName("Doe")
                    .phone("011111112")
                    .gender(GenderType.M)
                    .build();
        }

        @Test
        @DisplayName("Update existing user should return updated user")
        void givenExistingUser_whenUpdate_thenReturnUpdatedUser() throws BusinessException {
            // Given
            User updatedUser = User.builder()
                    .id(1L)
                    .firstName("John Updated")
                    .lastName("Doe")
                    .phone("011111112")
                    .gender(GenderType.M)
                    .build();

            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userRepository.save(any(User.class))).thenReturn(updatedUser);

            // When
            UserResultDto result = userService.update(updateDto);

            // Then
            assertNotNull(result);
            assertEquals(updatedUser.getFirstName(), result.getFirstName());
            verify(userRepository).findById(updateDto.getId());
            verify(userMapper).copy(updateDto, testUser);
            verify(userRepository).save(testUser);
        }

        @Test
        @DisplayName("Update non-existing user should throw BusinessException")
        void givenNonExistingUser_whenUpdate_thenThrowBusinessException() {
            // Given
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            // When
            BusinessException exception = assertThrows(BusinessException.class, () -> userService.update(updateDto));

            // Then
            assertEquals("USER-002", exception.getCode());
            verify(userRepository, never()).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("Get User By ID Tests")
    class GetUserByIdTests {

        @Test
        @DisplayName("Get existing user by ID should return user")
        void givenExistingUserId_whenGetById_thenReturnUser() throws BusinessException {
            // Given
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            // When
            UserResultDto foundUser = userService.getById(1L);

            // Then
            assertNotNull(foundUser);
            assertEquals("John", foundUser.getFirstName());
            verify(userRepository).findById(1L);
        }

        @Test
        @DisplayName("Get non-existing user by ID should throw BusinessException")
        void givenNonExistingUserId_whenGetById_thenThrowBusinessException() {
            // Given
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            // When
            BusinessException exception = assertThrows(BusinessException.class, () -> userService.getById(999L));

            // Then
            assertEquals("USER-002", exception.getCode());
        }
    }

    @Nested
    @DisplayName("Get All Users Tests")
    class GetAllUsersTests {

        @Test
        @DisplayName("Get all users should return list of users")
        void givenUsersExist_whenGetList_thenReturnUserList() {
            // Given
            List<User> users = Collections.singletonList(testUser);

            when(userRepository.findAll()).thenReturn(users);

            // When
            List<UserResultDto> result = userService.getList();

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(userRepository).findAll();
        }

        @Test
        @DisplayName("Get all users when no users exist should return empty list")
        void givenNoUsers_whenGetList_thenReturnEmptyList() {
            // Given
            when(userRepository.findAll()).thenReturn(List.of());

            // When
            List<UserResultDto> result = userService.getList();

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(userRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Delete User Tests")
    class DeleteUserTests {

        @Test
        @DisplayName("Delete existing user should succeed")
        void givenExistingUserId_whenDelete_thenSucceed() throws BusinessException {
            // Given
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            doNothing().when(userRepository).delete(testUser);

            // When
            userService.deleteById(1L);

            // Then
            verify(userRepository).findById(1L);
            verify(userRepository).delete(testUser);
        }

        @Test
        @DisplayName("Delete non-existing user should throw BusinessException")
        void givenNonExistingUserId_whenDelete_thenThrowBusinessException() {
            // Given
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            // When
            BusinessException exception = assertThrows(BusinessException.class, () -> userService.deleteById(999L));

            // Then
            assertEquals("USER-002", exception.getCode());
            verify(userRepository, never()).delete(any(User.class));
        }
    }
}
