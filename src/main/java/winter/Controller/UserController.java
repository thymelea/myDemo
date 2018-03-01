package winter.Controller;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import winter.model.User;
import winter.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/loginSuccess.html")
    public String loginSuccess(HttpServletRequest request) {
        //登陆成功，可以执行一些处理；
        //User user=UserService.getUserFromSession(request);

        return "forward:/pub/allUser";
    }

    @RequestMapping(value = "pub/allUser")
   public String getAllUser(HttpServletRequest request, Model model){
        List<User> userList=userService.findAllUser();
        model.addAttribute("users",userList);
        return "/index";
   }
    @RequestMapping(value = "/findOneUser")
    public @ResponseBody Map<String,Object> getOneUser(HttpServletRequest request, @Param("fid")String id){
        Map<String,Object> messageMap =new HashMap<>();
       User user=userService.findUserById(id);
        messageMap.put("user",user);
        return messageMap;
    }
   @RequestMapping("/updateUser")
    public @ResponseBody Map<String,Object> upUser(@Param("fid")String id,@Param("name")String name,@Param("password")String password,@Param("phone")String phone){
//    User user=userService.findUserById(id);
       Map<String,Object> messageMap =new HashMap<>();
       User user=new User();
       user.setUserId(id);
       user.setUserName(name);
       user.setPassword(password);
       user.setPhone(phone);
       try {
           userService.changeUser(user);
       }catch (Exception e){
           messageMap.put("code",1);
           messageMap.put("msg","更新失败！");
       }
       messageMap.put("code",0);
       messageMap.put("msg","更新成功！");
       return messageMap;
   }
    @RequestMapping("/deleteUser")
    public @ResponseBody Map<String,Object> deUser(@Param("fid")String fid){

        Map<String,Object> messageMap =new HashMap<>();
        try {
            userService.dropUser(fid);
        }catch (Exception e){
            messageMap.put("code",1);
            messageMap.put("msg","删除失败！");
        }
        messageMap.put("code",0);
        messageMap.put("msg","删除成功！");
        return messageMap;
    }
    @RequestMapping("/addUser")
    public @ResponseBody Map<String,Object> adUser(@Param("name")String name,@Param("password")String password,@Param("phone")String phone){
        Map<String,Object> messageMap =new HashMap<>();
        User user=new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserName(name);
        user.setPassword(password);
        user.setPhone(phone);
        try {
            userService.addUser(user);
        }catch (Exception e){
            messageMap.put("code",1);
            messageMap.put("msg","添加失败！");
        }
        messageMap.put("code",0);
        messageMap.put("msg","添加成功！");
        return messageMap;
    }
}
