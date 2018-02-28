package winter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import winter.config.StartupListener;
import winter.config.SysConfig;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({SysConfig.class})
@MapperScan("winter.mapper")
@EnableScheduling
public class SpringbootMybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication springApp=new SpringApplication(SpringbootMybatisDemoApplication.class);
		springApp.addListeners(new StartupListener());
		springApp.run(args);
	}
}
