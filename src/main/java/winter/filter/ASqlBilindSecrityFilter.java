package winter.filter;


import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import winter.utils.Utility;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by developer_hyaci on 2015/9/24.
 */

@Configuration
public class ASqlBilindSecrityFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(ASqlBilindSecrityFilter.class);
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	/**
	 * 哪些不参加sql盲注过滤在此进行判定
	 * @param request
	 * @return boolean false[不参加]，true[参加]
	 */
	private boolean isEnterSqlFilter(HttpServletRequest request){
		String url=request.getServletPath();
		if(ServletFileUpload.isMultipartContent(request)){
			//当为文件上传时不进行SQL盲注过滤
			return false;
		}else if(url.equals("/pub/registerAndRoles")){
			//CA DN存在获取存在逗号，此处不需要进行过滤
			return false;
		}else if(url.indexOf("/recvfreeback")!=-1){
			//银行回馈不进行数据防SQL注入
			return false;
		}else if(url.equals("/addUser")){
			//银行回馈不进行数据防SQL注入
			return false;
		}else if(url.equals("/mcontent/saveMcontent")||url.equals("/pub/pushMcontent")){
			//添加栏目内容暂时不进行防SQL注入
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest requestBean=(HttpServletRequest) request;

		//判定是否需要对SQL盲注进行处理
		Map<String, String[]> filterMap = requestBean.getParameterMap();
		boolean filterFlag=false;//SQL注入标识
		if(isEnterSqlFilter(requestBean)){
			String filterValue="";
			for(String key:filterMap.keySet()){
				for(String value:filterMap.get(key)){
					value= Utility.htmlEncode(value);
					//filterValue=value.replaceAll("(^|\\&)|(\\|)|(\\;)|(\\$)|(\\%)|(\\@)|(\\')|(\\\")|(\\>)|(\\<)|(\\))|(\\()|(\\+)|(\\,)|(\\\\)|(\\#|$)","");
					filterValue=value.replaceAll("(^|\\&)|(\\|)|(\\;)|(\\$)|(\\%)|(\\')|(\\\")|(\\+)|(\\,)|(\\\\)|(\\#|$)","");
					if(!value.equals(filterValue)){
						filterFlag=true;
						break;
					}
				}
				if(filterFlag){
					break;
				}
			}
			filterValue=null;
		}
		boolean isRedirect=false;
		if(filterFlag){
			//当出现SQL注入安全问题时
			//解决方案：前台UI进行特殊字符判定，后台进行特殊字符替换（假如跳过前台后台已经进行拦截处理）；
			if(requestBean.getHeader("X-Requested-With")!=null){
				//Ajax请求时
				requestBean=new SqlBlindFilterRequestWrapper(requestBean);
			}else{
				isRedirect=true;
			}

		}
		if(isRedirect&&filterFlag){
			//出现SQL注入且非Ajax请求时
			/*String url=requestBean.getServletPath();
			logger.info("========================>sqlbind:"+url);
			logger.info("========================>sqlbind paraMap:"+requestBean.getParameterMap());*/
			HttpServletResponse responseBean = (HttpServletResponse)response;
			responseBean.sendRedirect("/pub/sqlblind.html");
		}else{
			chain.doFilter(requestBean, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	private class SqlBlindEntry<K,V> implements Entry<K, V>{
		private Entry me;

		public SqlBlindEntry(Entry me) {
			if (me == null) {
				throw new IllegalArgumentException("Map.Entiry argument not null.");
			}
			if(me.getValue()!=null&&!me.getValue().toString().equals("")){
				if(me.getValue() instanceof String ){
					if(!me.getKey().toString().equals("email")){
						me.setValue(Utility.htmlEncode(me.getValue().toString()).replaceAll("(^|\\&)|(\\|)|(\\;)|(\\$)|(\\%)|(\\@)|(\\')|(\\\")|(\\>)|(\\<)|(\\))|(\\()|(\\+)|(\\,)|(\\\\)|(\\#|$)", ""));
					}
				}else if(me.getValue() instanceof String []){
					String [] values=(String[]) me.getValue();
					for(int i=0;i<values.length;i++){
						values[i]=Utility.htmlEncode(values[i]).replaceAll("(^|\\&)|(\\|)|(\\;)|(\\$)|(\\%)|(\\@)|(\\')|(\\\")|(\\>)|(\\<)|(\\))|(\\()|(\\+)|(\\,)|(\\\\)|(\\#|$)","");
					}
//					me.setValue(values);
				}else{

				}
			}
			this.me = me;
		}

		@Override
		public K getKey() {
			// TODO Auto-generated method stub
			return (K) me.getKey();
		}

		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return (V) me.getValue();
		}

		@Override
		public V setValue(V value) {
			// TODO Auto-generated method stub
			return (V) me.setValue(value);
		}

	}

	private class SqlBlindFilterRequestWrapper extends HttpServletRequestWrapper {

		private Map paramap;
		public SqlBlindFilterRequestWrapper(HttpServletRequest request) {
			super(request);
			Map tempMap=request.getParameterMap();
			Map buildMap = new HashMap();
			Iterator iterator = tempMap.entrySet().iterator();
			while (iterator.hasNext()) {
				SqlBlindEntry entry = new SqlBlindEntry<String,Object>((Entry) iterator.next());
				buildMap.put(entry.getKey(),entry.getValue());
			}
			paramap=buildMap;
		}

		@Override
		public String getParameter(String name) {
			// TODO Auto-generated method stub
			if (paramap.get(name) instanceof String) {
				return (String) paramap.get(name);
			}else if(paramap.get(name) instanceof String []){
				return ((String [])paramap.get(name))[0];
			}else{
				return super.getParameter(name);
			}
		}
		@Override
		public Map<String, String[]> getParameterMap() {
			// TODO Auto-generated method stub
			return paramap;
		}
		@Override
		public String[] getParameterValues(String name) {
			// TODO Auto-generated method stub
			if (paramap.get(name) instanceof String) {
				ArrayList<String> arr=new ArrayList<String>();
				arr.add((String) paramap.get(name));
				return arr.toArray(new String [1]);
			}else if(paramap.get(name) instanceof String []){
				return (String [])paramap.get(name);
			}else{
				return super.getParameterValues(name);
			}
		}

	}


}
