package winter.mapper;


import winter.model.User;

import java.util.List;

public interface UserMapper {

    List<User> selectAllUser();
}