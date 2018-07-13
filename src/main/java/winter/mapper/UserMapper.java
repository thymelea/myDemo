package winter.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;

import winter.model.User;

import java.util.List;

@Mapper
@CacheConfig(cacheNames = "t_user")
public interface UserMapper{

    List<User> selectAllUser();
    User selectUserById(String UserId);

    int updateUser(User user);
    int deleteUser(String userId);

    int insertUser(User user);
    User selectByLoginName(String loginName);
}