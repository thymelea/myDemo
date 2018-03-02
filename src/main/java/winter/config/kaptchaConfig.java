package winter.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class kaptchaConfig {
    @Bean(name="captchaProducer")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "250,250,210");
        properties.setProperty("kaptcha.textproducer.font.color", "30,144,255");
        properties.setProperty("kaptcha.image.width", "135");
        properties.setProperty("kaptcha.textproducer.char.string","qwertyupasdfghjkxcvbnm3456789");
        properties.setProperty("kaptcha.image.height", "45");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");//验证码长度
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        properties.setProperty("kaptcha.textproducer.font.size","30");//字体大小
        properties.setProperty("kaptcha.noise.color","black");//干扰 颜色，合法值： r,g,b 或者 white,black,blue.
//        properties.setProperty("kaptcha.background.clear.from","light grey");//背景颜色渐变， 开始颜色
//        properties.setProperty("kaptcha.background.clear.to","white");//背景颜色渐变， 结束颜色
//        水纹com.google.code.kaptcha.impl.WaterRipple
//        鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
//        阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.WaterRipple");//图片样式
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
