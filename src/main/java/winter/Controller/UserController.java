package winter.Controller;

import com.winter.model.User;
import com.winter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/allUser")
   public String findAllUser(HttpServletRequest request, Model model){
        List<User> userList=userService.findAllUser();
        model.addAttribute("users",userList);
        return "/index";
   }
}
