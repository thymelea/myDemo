package winter.mapper;


import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.formula.functions.T;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import winter.model.User;

import java.util.List;

public interface UserMapper{

    List<User> selectAllUser();
    User selectUserById(String UserId);
    int updateUser(User user);
    int deleteUser(String userId);
    int insertUser(User user);
    User selectByLoginName(String loginName);
}