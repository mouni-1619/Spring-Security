package org.tp.SpringSecurity1Application.Services;

import org.tp.SpringSecurity1Application.Dto.LoginDto;
import org.tp.SpringSecurity1Application.Dto.UserDto;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    String loginUser(LoginDto loginDto);

}
