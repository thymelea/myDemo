package winter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by developer_hyaci on 2017/8/23.
 */
@Configuration
@ConfigurationProperties(prefix="proj.sys.define")
public class SysConfig {
    private String projName;
    private String acountLockedTime;
    private String deleteEnableDeleteLogDate;
    private String deleteAllLogMonth;











    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getAcountLockedTime() {
        return acountLockedTime;
    }

    public void setAcountLockedTime(String acountLockedTime) {
        this.acountLockedTime = acountLockedTime;
    }

    public String getDeleteEnableDeleteLogDate() {
        return deleteEnableDeleteLogDate;
    }

    public void setDeleteEnableDeleteLogDate(String deleteEnableDeleteLogDate) {
        this.deleteEnableDeleteLogDate = deleteEnableDeleteLogDate;
    }

    public String getDeleteAllLogMonth() {
        return deleteAllLogMonth;
    }

    public void setDeleteAllLogMonth(String deleteAllLogMonth) {
        this.deleteAllLogMonth = deleteAllLogMonth;
    }
}
