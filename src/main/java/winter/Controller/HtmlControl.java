package winter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import winter.security.DefineAuthItem;
import winter.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HtmlControl{
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home(HttpServletResponse response, HttpServletRequest request, Model model) {
        //系统根目录访问跳转，进入首页
        return "redirect:login.html";
    }


    @RequestMapping(value={"/pub/webindex.html"})
    public String indexWeb(HttpServletResponse response, HttpServletRequest request, Model model) {
        //进入门户访问界面
        return "webindex";
    }

    @RequestMapping(value={"/index.html"})
    public String index(HttpServletResponse response, HttpServletRequest request, Model model) {
        //登录页面
        System.out.println("zhang1");
        return "login";
    }


    @RequestMapping(value={"/back.html"})
    public String backIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
        //访问后台管理页面
        //从当前登陆用户的权限中获取后台的权限
//        List<DefineAuthItem> allAuths=UserService.getUserAuthsTreeFromSession(request);
        List<DefineAuthItem> backAuth=new ArrayList<>();

//        for(int i=0;i<allAuths.size();i++){
//            //url 为0，type为表示后台顶级菜单
//            if(allAuths.get(i).getAuthorityBean().getUrl().equals("0") &&
//                    allAuths.get(i).getAuthorityBean().getType().intValue()==1){
//                backAuth.add(allAuths.get(i));
//            }
//        }
        request.getSession().setAttribute("uathsBack",backAuth.size()!=0?backAuth:null);

        return "home";
    }
    @RequestMapping(value={"/frontBack.html"},method= RequestMethod.POST)
    public String frontBackIndex(HttpServletResponse response, HttpServletRequest request, Model model) {
        //执行具体子页面跳转的导航id
        String jumpId = request.getParameter("target");
        model.addAttribute("target","");
        if(!StringUtils.isEmpty(jumpId)){
            model.addAttribute("target",jumpId);
        }
        //前台管理页面
        return "frontBackIndex";
    }

    @RequestMapping(value = { "/pub/sqlblind.html" })
    public String sqlblind() {
        return "sqlblindMsg";
    }
    @RequestMapping(value = { "/error.html" })
    public String error() {
        return "error";
    }


}
