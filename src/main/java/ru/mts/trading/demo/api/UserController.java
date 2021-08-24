package com.phil.demo.api;

import com.phil.demo.model.User;
import com.phil.demo.model.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users")
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return users.stream().map(this::userDto).collect(Collectors.toList());
    }

    private UserDto userDto(User user) {
        return new UserDto(
                user.getName(),
                user.getRole()
        );
    }

    @AllArgsConstructor
    @Getter
    public static class UserDto {
        private final String name;
        private final String role;
    }

}
