package winter.utils;


import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by developer_hyaci on 2017/8/18.
 * 项目中常规性静态描述
 */
public class Statements {

    /**
     * 系统启动之后存放与系统中的RSA公私钥相关
     * 该一对公私钥主要用于前台与后台交互时敏感数据的加密
     * key  : modulus|public_exponent|publicKey|privateKey
     * value:模|公钥指数|公钥对象|私钥对象
     * rule :模与公钥指数可以生成公钥对象,加密与解密使用的都是钥对象；
     * 维护机制：项目启动的时候就会生成此信息,每次启动生成的值不一样；
     * */
    public static HashMap<String,Object> rsaPairs=new HashMap<>();
    public static String getRSAModulus(){
        if(!rsaPairs.isEmpty() && rsaPairs.get("modulus")!=null){
            return rsaPairs.get("modulus").toString();
        }
        return null;
    }
    public static String getRSAPubExponent(){
        if(!rsaPairs.isEmpty() && rsaPairs.get("public_exponent")!=null){
            return rsaPairs.get("public_exponent").toString();
        }
        return null;
    }
    public static RSAPublicKey getRSAPubKey(){
        if(!rsaPairs.isEmpty() && rsaPairs.get("publicKey")!=null){
            return (RSAPublicKey)rsaPairs.get("publicKey");
        }
        return null;
    }
    public static RSAPrivateKey getRSAPrivateKey(){
        if(!rsaPairs.isEmpty() && rsaPairs.get("privateKey")!=null){
            return (RSAPrivateKey)rsaPairs.get("privateKey");
        }
        return null;
    }

    /**
     *用户维护当前在线的用户；
     * 0.当登陆成功则在线，但用户失效却存在3种方式
     *1.登出时，需要将用户的登陆状态置为登出
     *2.用户没有主动登出，session已失效，以失效session发起请求，也需要将用户的登陆状态置为登出
     *3.用户没有主动登出，不论session失效未失效，用户都没有再发起请求，此时也需要将用户的登陆状态置为登出
     *4.应用程序不管合理以及不合理的宕机，此时用户的状态状态也要设置为登出；
     * 第1点以及第2点可以通过session过期进行处理；
     * 第3点必须实时监听当前登陆用户是否已经不在线，监听到不在线时强制session过期
     * 第4点只能默认宕机之后所有在线用户都登出
     * rule:对应0，需要往loginSessionMap中写入当前用户对应的session对象以及操作的时间；
     *      对应1，loginSessionMap需要剔除掉登陆时已写入相关；
     *      对应2，loginSessionMap需要剔除掉登陆时已写入相关；
     *      对应3，需要在DefineGlobalFilter中更新当前登陆用户最新操作时间,并通过轮询任务剔除
     *      对应4，loginSessionMap自动会销毁，如果有实际维护登陆状态的字段，需要在应用启动之初重置为未登陆；
     * key:用户ID,
     * value:key:session;value:session对象；key:time;value:最新访问时间；
     */
    public static HashMap<String,HashMap<String,Object>> loginSessionMap= new HashMap<>();
    public static void addLoginSessionMap(String userId,HttpSession sesssion){
        HashMap item=new HashMap<String,Object>();
        item.put("session",sesssion);
        item.put("time", LocalDateTime.now());
        loginSessionMap.put(userId,item);
    }
    public static void upLoginSessionMap(String userId){
        if(loginSessionMap.containsKey(userId)){
            loginSessionMap.get(userId).put("time", LocalDateTime.now());
        }
    }
    public static void removeLoginSessionMap(String userId){
        if(loginSessionMap.containsKey(userId)){
            loginSessionMap.remove(userId);
        }
    }
    public static LocalDateTime getLoginSessionMapTime(String userId){
        if(loginSessionMap.containsKey(userId)){
            return (LocalDateTime)loginSessionMap.get(userId).get("time");
        }
        return null;
    }
    public static HttpSession getLoginSessionMapSession(String userId){
        if(loginSessionMap.containsKey(userId)){
            return (HttpSession)loginSessionMap.get(userId).get("session");
        }
        return null;
    }

    /**
     * 缓存menu主体XML结构
     * map->
     * key:menuCode(即元素name)  value:对应的menu主体 map->
     *               key:label , name , xml , category->List(Map) -> map
     *                                                               key: name ,label ,field
     */
    public static final List<HashMap<String,Object>> menusMap = new ArrayList<>();
    /**
     * 缓存menu主体的所有分类
     * key:menuCode value:list<map>
     *                      map->key:name、label
     */
    public static final HashMap<String,Object> categorysMap = new HashMap<>();


    /**
     * 2018.2.13 梅宇飞
     * 缓存stopWatch.xml结构
     * map->
     * key:一级节点的name（如：noticeNature） value:对应的子节点  >List(Map) -> map
     *                                       key:name、label、value     value:对应的值（如：field、正常公告、1）
     */
    public static final Map<String,Object> stopWatchMap = new HashMap<>();

    /**
     * 根据typeCode获取List（fields）
     * @param typeCode
     * @return
     */
    public static List<Map<String,Object>> getStopWatchMapList(String typeCode){
        if(typeCode != null && stopWatchMap.containsKey(typeCode)){
            return (List<Map<String,Object>>)stopWatchMap.get(typeCode);
        }
        return null;
    }

    /**
     * 根据typeCode及value值获取label描述
     * @param typeCode
     * @param value
     * @return
     */
    public static String getStopWatchLabel(String typeCode,String value){
        if(typeCode != null && stopWatchMap.containsKey(typeCode)){
            List<Map<String,Object>> list = (List<Map<String,Object>>)stopWatchMap.get(typeCode);
            for(Map<String,Object> map : list){
                if(map.get("value").toString().equals(value)){
                    return map.get("label").toString();
                }
            }
        }
        return null;
    }

}
