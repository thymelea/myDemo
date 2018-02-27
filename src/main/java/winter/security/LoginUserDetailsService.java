package winter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import winter.model.User;
import winter.service.user.UserService;

@Component
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userService.findByLoginName(userName);
		if(user ==null){
			throw new UsernameNotFoundException("账户校验不能通过，请核实，并再次输入。");
		}
		return new SecurityUser(user);
	}
	public boolean verifyPasd(String encodePasd,String pasd){
		return passwordEncoder.matches(pasd,encodePasd);
	}

	public boolean verifyCADN(String dbCadn,String formCadn){
		//最初版本没有对CA进行响应的判定，默认成功；
		if(dbCadn!=null&&!dbCadn.trim().equals("")){
			if(formCadn!=null &&!formCadn.trim().equals("")){
				return dbCadn.equals(formCadn);
			}else{
				return false;
			}
		}
		return true;
	}
	public boolean verifyRole(String allRole,String role,String loginName){
		//最初版本没有对角色进行响应的判定，默认成功；
		boolean flag=true;
		if(allRole!= null && !allRole.equals("")){
			if(role!=null&&!role.equals("")){
                /*List<String> list=new ArrayList<>();
                list.add("SUPERADMIN");
				list.add("ADMIN");*/
				flag = false;
//						userService.checkRoleAndLoginName(loginName, role);
				return flag;
			}else {
				return false;
			}
		}
		return flag;
	}


	public void  updateUser(User user){
		userService.addUser(user);
	}
	
	

}
