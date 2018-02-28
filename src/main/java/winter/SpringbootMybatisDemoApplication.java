package winter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import winter.config.StartupListener;

@SpringBootApplication
@MapperScan("winter.mapper")
@EnableScheduling
public class SpringbootMybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication springApp=new SpringApplication(SpringbootMybatisDemoApplication.class);
		springApp.addListeners(new StartupListener());
		springApp.run(args);
	}
}
