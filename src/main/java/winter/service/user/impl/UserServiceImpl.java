package winter.service.user.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import winter.mapper.UserMapper;
import winter.model.User;
import winter.service.user.UserService;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Override
    public List<User> findAllUser() {
        return userMapper.selectAllUser();
    }
}
