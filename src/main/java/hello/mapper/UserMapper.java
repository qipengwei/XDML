package hello.mapper;

import hello.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE ID = #{id}")
    User findUserById(@Param("id") Integer id);

    @Select("SELECT * FROM USER WHERE username = #{username}")
    User findUserByUserName(@Param("username") String username);

    @Insert("INSERT INTO USER(username, encrypted_password, avatar, created_at, updated_at) VALUES(#{username}, #{encrypted_password}, #{avatar}, NOW(), NOW())")
    void sign(@Param("username") String username,
              @Param("encrypted_password") String encrypted_password);
}
