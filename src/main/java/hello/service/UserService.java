package hello.service;

import hello.entity.User;
import hello.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;


@Service
public class UserService implements UserDetailsService {
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserMapper userMapper, BCryptPasswordEncoder bc) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bc;
    }

    /**
     * 用户注册
     * @param username 用户名称
     * @param password 用户密码
     */
    public void sign(String username, String password) {
         this.userMapper.sign(username, bCryptPasswordEncoder.encode(password));
    }

    /**
     * 根据用户id查询
     * @param id 用户ID
     * @return User
     */
    public User getUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    /**
     * 根据用户名称查询
     * @param username 用户名称
     * @return User
     */
    public User getUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }


    /**
     * 用户登录检索
     * @param username 用户名
     * @return UserDetails 用户信息
     * @throws UsernameNotFoundException 用户不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = this.userMapper.findUserByUserName(username);
        if (user == null) {
            //数据库中不存在用户  则抛出异常
            throw new UsernameNotFoundException(username + "不存在!");
        }
        //存在用户  拿出密码进行比对
        return new org.springframework.security.core.userdetails
                .User(username, user.getEncrypted_password(), Collections.emptyList());
    }
}
