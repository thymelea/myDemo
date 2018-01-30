package winter.mapper;


import winter.model.User;

import java.util.List;

public interface UserMapper {

    List<User> selectAllUser();
    User selectUserById(String UserId);
    int updateUser(User user);
    int deleteUser(String userId);
    int insertUser(User user);
}