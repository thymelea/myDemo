package winter.filter;


import org.springframework.boot.web.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import winter.model.User;
import winter.security.SecurityUser;
import winter.utils.Statements;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by developer_hyaci on 2017/7/4.
 */
@Configuration
public class DefineGlobalFilter extends OrderedCharacterEncodingFilter {

    //前台可以根据以下获取公钥模以及公钥指数
    private static final String RSAPUBMOD="RSAPUBMOD";
    private static final String RSAPUBEXPONENT="RSAPUBEXPONENT";

    //前台可以根据以下获取该项目配置的名称
    private static final String PROJNAME="PROJNAME";

//    @Autowired
//    private SysConfig sysConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.setEncoding("UTF-8");
        this.setForceEncoding(true);
        if(request.getRequestURL().indexOf("recvfreeback/CCB.action")!=-1){
            //某些访问需要特定的字符编码要求；可以以此处写法进行调整；
            this.setEncoding("GBK");
        }

        //往所有请求里面植入公钥模以及公钥指数方便前台获取并用于处理敏感信息
        request.setAttribute(RSAPUBMOD, Statements.getRSAModulus());
        request.setAttribute(RSAPUBEXPONENT,Statements.getRSAPubExponent());
        //往所有请求里面植入项目名称
//        request.setAttribute(PROJNAME,sysConfig.getProjName());
        //维护用户登陆状态的有效性
        SecurityContext securityContext= (SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext!=null){
            Authentication auth=securityContext.getAuthentication();
            if(auth!=null){
                User user=((SecurityUser)auth.getPrincipal()).getLoginUser();
                if(user!=null && user.getUserId()!=null){
                    Statements.upLoginSessionMap(user.getUserId());
                }
            }
        }


        super.doFilterInternal(request, response, filterChain);
    }
}
