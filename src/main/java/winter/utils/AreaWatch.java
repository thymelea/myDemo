package winter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AreaWatch {
    /**
     * 获取配置文件中的地区编码
     * @param key
     * @return
     * @throws Exception
     */
    public static String getArea(String key) throws Exception{
        Properties configFileProp = new Properties();

        String path = AreaWatch.class.getResource("/areaWatch.properties").getFile();

        File file = new File(path);
        Object is = null;
        try{
            if(file.exists() && !file.isDirectory()){
                is = new FileInputStream(file);
            }else{
                //file不存在
                if (!file.exists() || file.isDirectory()) {
                    is = AreaWatch.class.getResourceAsStream(path);
                }
                if (is == null) {
                    throw new Exception("没有找到配置文件");
                }
            }
            configFileProp.load((InputStream)is);
        }catch (Exception e){
            throw new Exception("读取配置文件出错!");
        }
        return configFileProp.getProperty(key);
    }

}
