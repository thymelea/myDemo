package winter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import winter.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功之后的一些相关处理
 *
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		System.out.println("zhang2");
		// TODO Auto-generated method stub
		//给用户附加当前登陆时间并更新当前用户（在用户验证过程中，对用户存在信息补全），
		SecurityUser securityUser =(SecurityUser)authentication.getPrincipal();
//		SysUser user=securityUser.getLoginUser();
		/*user.setFlasttime(Timestamp.valueOf(LocalDateTime.now()));
		userService.addUser(user);*/
		this.setDefaultTargetUrl("/loginSuccess.html");
		this.setAlwaysUseDefaultTargetUrl(true);
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
