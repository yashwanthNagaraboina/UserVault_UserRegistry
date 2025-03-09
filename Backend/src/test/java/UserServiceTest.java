import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.model.User;
import com.example.repo.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserById_UserExists_ReturnsCorrectUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUserOptional = userService.getUserById(1L);
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals("John", foundUser.getFirstName());
    }

    @Test
    void testGetUserById_UserNotFound_ThrowsNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.getUserById(99L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}