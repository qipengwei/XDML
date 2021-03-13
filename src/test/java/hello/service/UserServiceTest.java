package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserMapper mockUserMapper;
    @Mock
    BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    public void testSign() {
        //给定预期 验证userService是否将参数转发给了UserMapper

        //将服务加密密码解析成特定字符串
        Mockito.when(mockBCryptPasswordEncoder.encode("testPassword")).thenReturn("testEncodedPassword");
        //调用服务
        userService.sign("testUser", "testPassword");

        //验证预期 比对传入参数是否一致
        Mockito.verify(mockUserMapper).sign("testUser", "testEncodedPassword");
    }

    @Test
    public void testUserByUserName() {
        userService.getUserByUserName("testUser");
        Mockito.verify(mockUserMapper).findUserByUserName("testUser");
    }

    @Test
    public void testUsernameNotFoundException() {
        //断言方法返回当前异常  UsernameNotFoundException
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("myUser"));
    }

    @Test
    public void testUserDetails() {
        //测试预期
        Mockito.when(mockUserMapper.findUserByUserName("myUser"))
                .thenReturn(new User(123, "myUser", "testEncodedPassword"));

        UserDetails userDetails = userService.loadUserByUsername("myUser");

        //断言参数是否正确
        Assertions.assertEquals("myUser", userDetails.getUsername());
        //断言参数是否正确
        Assertions.assertEquals("testEncodedPassword", userDetails.getPassword());

    }
}