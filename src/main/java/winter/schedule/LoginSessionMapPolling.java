package winter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import winter.config.SysConfig;
import winter.utils.Statements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *轮询服务，定时清除已经过期的登陆session信息；
 */
@Component
public class LoginSessionMapPolling {
    @Autowired
    private SysConfig sysConfig;


    @Scheduled(cron = "0 0/5 * * * *")
    public void analysisLoginSessionMap(){
        String lockedTime = sysConfig.getAcountLockedTime();
        int lockedTimeIntValue = 30;
        if(lockedTime!=null && !lockedTime.trim().equals("") && !lockedTime.trim().equals("0")){
            lockedTimeIntValue=Integer.parseInt(lockedTime);
        }

        List<String> keys=new ArrayList<>();
        LocalDateTime now=null;
        LocalDateTime valid=null;
        for(String key: Statements.loginSessionMap.keySet()){
            now=LocalDateTime.now();
            valid=Statements.getLoginSessionMapTime(key).plusMinutes(lockedTimeIntValue);
            if(valid.compareTo(now) < 0){
                keys.add(key);
            }
        }
        for (String key:keys){
            Statements.getLoginSessionMapSession(key).invalidate();
        }
        keys=null;
    }

}
