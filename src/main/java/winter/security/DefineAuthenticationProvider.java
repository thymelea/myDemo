package winter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 登陆用户的权限验证
 */
@Component
public class DefineAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private LoginUserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        LoginFormParamToken loginFormToken=(LoginFormParamToken)usernamePasswordAuthenticationToken;
        SecurityUser securityUser=(SecurityUser)userDetails;
        if(!userDetailsService.verifyPasd(securityUser.getPassword(),loginFormToken.getLoginPasd())){
            throw new BadCredentialsException("密码校验不能通过。");
        }else if(!userDetailsService.verifyRole(securityUser.getAllRole(),loginFormToken.getSelectedRole(),loginFormToken.getLoginName())){
            throw new BadCredentialsException("所选角色超过界限。");
        }else{
            //通过所有验证更新当前登陆用户的角色；
//            SysUser user=securityUser.getLoginUser();
//            user.setFrole(loginFormToken.getSelectedRole());
            // 因FBSYSUNIQUE[业务系统唯一标识]不在唯一，导致业务逻辑出现异常，而fpbucode为本系统定义的唯一值，不可能会出现重复，因而以其代替FBSYSUNIQUE作为用户所属业务系统唯一键
//            user.setFpbucode(loginFormToken.getSysCode());
//            if(user.getFrole()!=null&&
//                    !user.getFrole().equals("")){
//                List<String> roles=new ArrayList<>();
//                roles.add("ADMIN");
//                roles.add("SUPERADMIN");
//                if(!roles.contains(user.getFrole())){
//                    user.setFrole(loginFormToken.getSelectedRole());
//                }
//            }else{
//                user.setFrole(loginFormToken.getSelectedRole());
//            }
        }

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(s);
    }
}
