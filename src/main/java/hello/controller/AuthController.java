package hello.controller;

import hello.entity.Result;
import hello.entity.User;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
public class AuthController {
    private UserDetailsService userDetailsService;

    private UserService userService;

    //管理鉴权Bean
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserDetailsService userDetailsService,
                          AuthenticationManager authenticationManager,
                          UserService userService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public AuthController(UserService mockUserService, AuthenticationManager mockAuthenticationManager) {
    }

    /**
     * 用户是否登录
     */
    @GetMapping("/auth")
    @ResponseBody
    public Result auth() {
        //是否为第一次登陆 如果已登录 则从内存中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser;
        try {
            loginUser = userService.getUserByUserName(authentication == null ? null : authentication.getName());
            return Result.success(true, "用户已登录!", loginUser);
        } catch (NullPointerException e) {
            return Result.success(false, "用户没有登录");
        }

    }

    /**
     * 用户登录
     * @param loginMessage 账号密码
     * @return
     */
    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> loginMessage) {
        System.out.println(loginMessage);

        String username = loginMessage.get("username").toString();
        String password = loginMessage.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            //用户不存在返回正确结果
            return Result.failure("用户不存在");
        }

        //验证用户权限
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        //对权限进行鉴权 / 没有权限 捕获异常
        try {
            //登录成功
            authenticationManager.authenticate(token);
            //保存用户信息在Cookie中
            SecurityContextHolder.getContext().setAuthentication(token);
            User user = userService.getUserByUserName(username);

            return Result.success( true, "登录成功!", user);
        } catch (BadCredentialsException e) {
            return Result.failure("密码不正确");
        }

    }

    /**
     * 用户注册
     * @param registerMessage
     * @return
     */
    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> registerMessage) {
        String username = registerMessage.get("username");
        String password = registerMessage.get("password");

        if (username == null || password == null) {
            return Result.failure("invalid username/password");
        }

        if (username.length() < 1 || username.length() > 15) {
            return Result.failure("invalid username");
        }

        if (password.length() < 6 || password.length() > 16) {
            return Result.failure("invalid password");
        }

        /**
         * 判断用户是否被注册
         * 避免同时间戳并发操作 数据库使用unique给username字段加上唯一约束
         */
        try {
            userService.sign(username, password);
            return Result.success(false, "success");
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return Result.failure("user is registered");
        }

    }

    /**
     * 注销登录
     * @return
     */
    @GetMapping("/auth/logout")
    @ResponseBody
    public Result logOut() {
        /**
         * 从上下文中获取key
         */
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginUser = userService.getUserByUserName(userName);
        if (loginUser == null) {
            return Result.success(false, "用户没有登录");
        }
        /**
         * 清除上下文信息
         */
        SecurityContextHolder.clearContext();
        return Result.success(false, "success");
    }


}
