package com.tom.msg.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tom.msg.util.NotRequireAuth;


public class AuthInterceptor extends HandlerInterceptorAdapter{


	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
		String url = req.getRequestURL().toString();
		if (url.indexOf("/cry/")!=-1) {
			return true;
		}
		if(handler instanceof NotRequireAuth){
			return true;
		}else{
			HttpSession session = req.getSession(false);
			if(session!=null&&session.getAttribute("auth")!=null){
				session = null;
				return true;
			}else{
				PrintWriter out = null;
				resp.setContentType("text/html;charset=UTF-8");
				
				try {
					out = resp.getWriter();
					String xRequestedWith = req.getHeader("X-Requested-With");
					if(xRequestedWith!=null&&xRequestedWith.trim().toUpperCase().equals("XMLHTTPREQUEST")){
						out.write("003");
					}else{
						resp.sendRedirect(req.getContextPath()+"/login/index.action");
					}
					resp.setStatus(403);
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					if(out!=null){
						out.close();
						out = null;
					}
				}
				return false;
			}
		}
	}
	
	/**
	 * 对字符串当中的JS代码 进行过滤,全部替换为"非法字符"
	 * 
	 * @param str 要过滤的字符串
	 * @return 过滤后的字符串
	 */
	public static String doFilter(String str) {
		str = Pattern
				.compile("<script.*?>.*?</script>", Pattern.CASE_INSENSITIVE)
				.matcher(str).replaceAll("****");
		return str;
	}
}
