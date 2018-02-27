package winter.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by developer_hyaci on 2016/2/19.
 * 封装登陆Form中所有的属性；
 */
public class LoginFormParamToken extends UsernamePasswordAuthenticationToken {
    private  String loginName;
    private String loginPasd;
    private String cadn;
    private String selectedRole;
    private String sysCode;

    public LoginFormParamToken(String loginName,String loginPasd,String cadn,String selectedRole,String sysCode){
        super(loginName,loginPasd);
        this.loginName=loginName;
        this.loginPasd=loginPasd;
        this.cadn=cadn;
        this.selectedRole=selectedRole;
        this.sysCode=sysCode;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPasd() {
        return loginPasd;
    }

    public void setLoginPasd(String loginPasd) {
        this.loginPasd = loginPasd;
    }

    public String getCadn() {
        return cadn;
    }

    public void setCadn(String cadn) {
        this.cadn = cadn;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
}
