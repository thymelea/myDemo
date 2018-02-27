package winter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by developer_hyaci on 2015/9/24.
 */
@Configuration
public class DefineSecrityHandle extends WebMvcConfigurerAdapter {

    /**
     * 不需要登陆就可以访问的路径
     * */
    public static final String [] ALLAUTHS={"/","/login","/pub/**","/index**","/error**", "/ui/**", "/css/**", "/js/**","/fonts/**","/assets/**","/resource/**"};

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private DefineAuthenticationProvider authenticationProvider;

        @Autowired
        private LoginSuccessHandler successHandler;
        @Autowired
        private LoginFailHandler loginFailHandler;

        @Bean
        public BCryptPasswordEncoder creatPsdEncodBean(){
            return new BCryptPasswordEncoder();
        }


        @Bean
        public LoginFormParamFilter defineLoginFilter(){
            LoginFormParamFilter paramFilter =new LoginFormParamFilter();
            AuthenticationManager manager=null;
            try{
                manager=this.authenticationManager();
            }catch (Exception e){
                e.printStackTrace();
            }
            paramFilter.setAuthenticationManager(manager);
            paramFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
            paramFilter.setAuthenticationSuccessHandler(successHandler);
            paramFilter.setAuthenticationFailureHandler(loginFailHandler);
            return paramFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(this.defineLoginFilter(), UsernamePasswordAuthenticationFilter.class);
            http.authorizeRequests().antMatchers("/","/pub/**","/index**", "/ui/**", "/css/**", "/js/**", "/resource/**")
                    .permitAll().anyRequest().authenticated();
            http.headers().disable();
            http.csrf().ignoringAntMatchers("/pub/**")
                    .and().formLogin().loginPage("/login").permitAll();
            http.logout().permitAll().invalidateHttpSession(true);
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws  Exception{
            auth.authenticationProvider(authenticationProvider);
        }

    }
}
