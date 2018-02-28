package winter.security;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import winter.utils.RSAUtils;
import winter.utils.Statements;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPrivateKey;

/**
 * 过滤登陆请求，获取到所有的登陆时输入的值，封装为其他对象往内部传递
 */
public class LoginFormParamFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //从request中去获取登陆时输入属性项数据封装为LoginFormParamToken对象
        String userName=request.getParameter("username");
        String password=request.getParameter("password");
        RSAPrivateKey rSAPrivateKey= Statements.getRSAPrivateKey();
        try {
            password = RSAUtils.decryptByPrivateKey(password, rSAPrivateKey);
        } catch (Exception e) {
            throw new BadCredentialsException("密码解析出错，请使用正确的方式登录");
        }
        String cadn=request.getParameter("cadn");
        String role=request.getParameter("selectedRole");
        String sysCode=request.getParameter("selectedBusys");
        LoginFormParamToken lfpt=new LoginFormParamToken( userName==null?"":userName, password==null?"":password,
                cadn==null?"":cadn,role==null?"":role,sysCode==null?"":sysCode);
        setDetails(request,lfpt);
        return this.getAuthenticationManager().authenticate(lfpt);
    }
}
