package winter.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import winter.utils.RSAUtils;
import winter.utils.Statements;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import java.util.HashMap;

public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("===================>>>Start some system codes that can be executing 。");
        //项目启动之后初始化一对RSA公私钥，用于处理前台UI往后台传输敏感数据时进行密文传输；
        try{
            HashMap mapRsa= RSAUtils.getKeys();
//          keys:modulus|public_exponent|publicKey|privateKey
            Statements.rsaPairs.put("publicKey",(RSAPublicKey) mapRsa.get("public"));
            Statements.rsaPairs.put("privateKey",(RSAPrivateKey) mapRsa.get("private"));
            Statements.rsaPairs.put("modulus", Statements.getRSAPubKey().getModulus().toString(16));
            Statements.rsaPairs.put("public_exponent",Statements.getRSAPubKey().getPublicExponent().toString(16));
            mapRsa=null;
        }catch (Exception e){
            logger.error("project creating！but the rsa pairs build a exception:"+e.getMessage());
            logger.error("system exit!!");
            System.exit(0);
        }
        logger.info("===================>>>End some system codes that can be executing 。");
    }
}
