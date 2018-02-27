package winter.service.user;



import winter.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUser();
    User findUserById(String userId);
    int changeUser(User user);
    int dropUser(String userId);
    int addUser(User user);
    User findByLoginName(String loginName);
}
