package winter.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;


public class DefineAuthItem implements GrantedAuthority {
    //自己
//    private Authority auth;

    private String urlPrefix;
    //子节点；
    private ArrayList<DefineAuthItem> subAuth = new ArrayList<>();

//    public DefineAuthItem(Authority auth){
//        this.auth=auth;
//        if(auth.getUrl()!=null && !auth.getUrl().equals("0") && auth.getUrl().indexOf("/")!=-1){
//            urlPrefix=auth.getUrl().substring(0,auth.getUrl().lastIndexOf("/"));
//            //该urlPrefix是使用在前台，且是用于栏目的id值，由于前台Jquery无法识别/符号，所以此处对于该值进行特殊转义；
//            //截取之后的urlPrefix中符号/转移为 "urlPrefix_"
//            // 前台转义涉及页面 templates/backFragments/nav-col.html;
//            urlPrefix=urlPrefix.replace("/","urlPrefix_");
//        }
//    }

//    @Override
//    public String getAuthority() {
//        return this.auth.getUrl();
//    }
//
//    public Authority getAuthorityBean(){
//        return this.auth;
//    }
//
//    public ArrayList<DefineAuthItem> getSubAuth() {
//        return subAuth;
//    }
//    public Authority getAuth() {
//        return auth;
//    }
//
//    public void setAuth(Authority auth) {
//        this.auth = auth;
//    }

    public void setSubAuth(ArrayList<DefineAuthItem> subAuth) {
        this.subAuth = subAuth;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
