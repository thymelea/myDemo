package winter.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class scheduleTest {
    private final static Logger logger = LoggerFactory.getLogger(scheduleTest.class);

    @Scheduled(cron = "0 * * * * ?")
    public void test(){
        System.out.println("aaa");
        logger.error("rrrrrrrrrrrrrr");
    }
}
