package winter.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import winter.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SecurityUser implements UserDetails {

	private static final long serialVersionUID = -2850148622557170441L;

	private User loginUser;

	public SecurityUser(User user){
		this.loginUser=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<GrantedAuthority> builder=new HashSet<>();
//		builder.add(new SimpleGrantedAuthority(this.loginUser.getFrole()));
		return Collections.unmodifiableCollection(builder);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.loginUser.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.loginUser.getLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		if(!(this.loginUser.getIsvalid().intValue()==1)){
			return false;
		}
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.loginUser.getIsvalid().intValue()==1?true:false;
	}

	public User getLoginUser() {
		return loginUser;
	}

//	public String getCADN(){
//		return loginUser.getCa();
//	}
	public String getAllRole(){
		return null;
//		return loginUser.getFallrole();
	}

}
