package winter.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utility {

    private static int num = 0;
    private static Date date = null;

    /**
     * 把HTML进行解义转换
     */
    public static String htmlDecode(String str) {
        if (str==null || "".equals(str)) {
            return "";
        }
        str = str.replaceAll("&amp;", "&");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");
        str = str.replaceAll("&quot;", "\"");
        return str;
    }
    /**
     * 对HTML的特定字符进行转义转换；
     */
    public static String htmlEncode(String str){
        String sTemp;
        sTemp = str;
        if (sTemp.equals("")) {
            return "";
        }
        sTemp = sTemp.replaceAll("&", "&amp;");
        sTemp = sTemp.replaceAll("<", "&lt;");
        sTemp = sTemp.replaceAll(">", "&gt;");
        sTemp = sTemp.replaceAll("\"", "&quot;");
        return sTemp;
    }


    /***
     * 生成19位订单编号
     * @return
     */
    public static synchronized String crete19OrderNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(now));
        if(date == null){
            date = now;
        }else{
            //判断是否是同一天
            if(!isSameDay(date,now)){
                //不是同一天 重置下num 并给日期重新赋值
                date = now;
                num = 0;
            }
        }
        num ++;
        String last7No = create7OrderNo();
        sb.append(last7No);
        return sb.toString();
    }

    /***
     * 生成订单后七位
     * @return
     */
    private static String create7OrderNo(){
        if(num>=10){
            return "00000"+String.valueOf(num);
        }else if(num>=100){
            return "0000"+String.valueOf(num);
        }else if(num>=1000){
            return "000"+String.valueOf(num);
        }else if(num>=10000){
            return  "00"+String.valueOf(num);
        }else if(num>=100000){
            return "0"+String.valueOf(num);
        }else if(num>=1000000){
            return String.valueOf(num);
        }else if(num <10){
            return "000000"+String.valueOf(num);
        }else{
            //订单大于10000000 基本不可能
            return "";
        }
    }

    /****
     * 判断是否是同一天
     * @param d1
     * @param d2
     * @return
     */
    private static boolean isSameDay(Date d1,Date d2){
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(d1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(d2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 标准时间格式
     */
    public enum TimeFomat{
        DATEFOMAT("yyyy-MM-dd"),
        DATETIMEFOMAT("yyyy-MM-dd HH:mm:ss");
        private String value;
        TimeFomat(String value){ this.value = value; }
        public String valueOf(){return this.value; }
    }

    public static boolean checkTimeFomat(String time,TimeFomat timeFomat){
        String reg = null;
        if(TimeFomat.DATEFOMAT.equals(timeFomat)){
            reg = "^\\d{4}(\\-)\\d{2}\\1\\d{2}$";
        }else if(TimeFomat.DATETIMEFOMAT.equals(timeFomat)){
            reg = "^\\d{4}(\\-)\\d{2}\\1\\d{2} \\d{2}(\\:)\\d{2}\\2\\d{2}$";
        }
        if(reg != null && time != null){
            return Pattern.compile(reg).matcher(time).matches();
        }
        return false;
    }

    /**
     * 深度克隆
     * @param obj
     * @param <T>
     * @return
     */
    public static <T extends Serializable> T clone(T obj){
        T clonedObj = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clonedObj = (T) ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return clonedObj;
    }
}
