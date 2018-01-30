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
    private UserMapper userMapper;

    @Override
    public List<User> findAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public User findUserById(String userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public int changeUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int dropUser(String userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }
}
