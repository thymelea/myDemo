package winter.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import winter.security.DefineSecrityHandle;
import winter.security.SecurityUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 */
@Configuration
public class GlobalAuthorityFilter implements Filter {
    /**
     * 需要登陆，但确是共有权限的路径
     */
    public static final String [] NEEDLOGINBUTALLAUTHS={"/loginSuccess.html","/back.html","/webback.html","/ajaxNoAuth"};

    public static  List<String> nofilterAuths=new ArrayList<>();

    static {
        //初始化豁免的权限规则
        List<String> allAuths=Arrays.asList(DefineSecrityHandle.ALLAUTHS);
        List<String> needLoginBugAllAuths=Arrays.asList(GlobalAuthorityFilter.NEEDLOGINBUTALLAUTHS);
        nofilterAuths.addAll(allAuths);
        nofilterAuths.addAll(needLoginBugAllAuths);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestBean=(HttpServletRequest) request;
        HttpSession session=requestBean.getSession();
        String url = requestBean.getServletPath();
        //判断当前请求是否需要进行权限过滤
        boolean needFlag=true; //默认当前请求需要进行权限校验
        boolean visitflag = false;//默认当前不具有权限；

        for(String itemUrl: nofilterAuths){
            if(itemUrl.indexOf("*")!=-1){
                //含有*的部分匹配
                itemUrl=itemUrl.replace("*","");
                if(url.indexOf(itemUrl)!=-1){
                    needFlag=false;
                    break;
                }
            }else{
                //不含有*号的全匹配
                if(url.equals(itemUrl)){
                    needFlag=false;
                    break;
                }
            }
        }
        if(needFlag){
            //获取当前登陆用户的权限
            SecurityContext securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
            Collection<? extends GrantedAuthority> auths=null;
            if(securityContext!=null){
                Authentication auth=securityContext.getAuthentication();
                SecurityUser user=(SecurityUser)auth.getPrincipal();
                auths = user.getAuthorities();
            }
            if(auths!=null && auths.size()!=0 ){
                for(GrantedAuthority item:auths){
                    if(item.getAuthority()!=null && !item.getAuthority().equals("")){
                        if(url.indexOf(item.getAuthority())!=-1){
                            visitflag=true;
                            break;
                        }
                    }
                }
            }
        }else{
            //不需要进行权限过滤,可以访问
            visitflag=true;
        }

        if(visitflag){
            //具有权限
            chain.doFilter(requestBean, response);
        }else{
            //不具有权限
            //不具有权限访问时，跳转到无error页面
            requestBean.setAttribute("error","权限不足！["+url+"]");

            //判断当前请求是否为ajax
            if(requestBean.getHeader("X-Requested-With")!=null){
                //是Ajax请求
                ((HttpServletResponse)response).sendRedirect("/ajaxNoAuth");
            }else{
                //非ajax请求；
                requestBean.getRequestDispatcher("/error.html").forward(request,response);
            }

        }



    }

    @Override
    public void destroy() {}
}
