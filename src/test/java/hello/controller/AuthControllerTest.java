package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Mock
    UserService UserService;
    @Mock
    AuthenticationManager mockAuthenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private MockMvc mvc;

    /**
     * 避免测试时使用到相同的实例 导致测试失败的问题  使用BeforeEach钩子进行重新注入依赖操作
     * @BeforeEach 注解: 实例化对象之前 钩子 进行特殊操作
     */
    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(UserService, mockAuthenticationManager)).build();
    }


    @Test
    void notLoginByDefault() throws Exception {
        //验证状态码是否正确
        mvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            mvcResult.getResponse().setCharacterEncoding("utf-8");
            //验证返回结果是否符合预期
            Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("用户没有登录"));
        });
    }

//    @Test
//    void testLogin() throws Exception {
//        /**
//         * 没有登录时, /auth接口返回未登录状态
//         * 使用 /auth/login 登录
//         * 检查 /auth的返回值， 处于登录状态
//         */
//
//        //未登录
//        //验证状态码是否正确
//        notLoginByDefault();
//
//        //使用 /auth/login 登录
//        Map<String, String> usernamePassword = new HashMap<>();
//        usernamePassword.put("username", "wizard");
//        usernamePassword.put("password", "123456");
//
//        String loginRequest = new ObjectMapper().writeValueAsString(usernamePassword);
//        System.out.println(loginRequest);
//
//        //获取UserDetail
//        Mockito.when(UserService.loadUserByUsername("wizard"))
//                .thenReturn(new User("wizard", bCryptPasswordEncoder.encode("123456"), Collections.emptyList()));
//
//        MvcResult mvcResult = mvc.perform(post("/auth/login")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(loginRequest))
//                .andExpect(status().isOk()).andReturn();
//
//
//        HttpSession session = mvcResult.getRequest().getSession();
//
//
//    }

}