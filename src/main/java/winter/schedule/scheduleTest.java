package winter.schedule;

import org.springframework.scheduling.annotation.Scheduled;

public class scheduleTest {

    @Scheduled(cron = "0 0 2 * * ?")
    public void test(){
        System.out.println("aaa");
    }
}
