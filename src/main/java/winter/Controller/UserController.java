package winter.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import winter.model.User;
import winter.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/allUser")
   public String getAllUser(HttpServletRequest request, Model model){
        List<User> userList=userService.findAllUser();
        model.addAttribute("users",userList);
        return "/index";
   }
}
