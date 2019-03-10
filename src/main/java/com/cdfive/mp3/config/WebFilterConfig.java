package com.cdfive.mp3.config;

import com.cdfive.core.filter.PageFilter;
import com.cdfive.core.filter.RequestBodyFilter;
import com.cdfive.core.filter.RequestLogFilter;
import com.cdfive.mp3.filter.Mp3LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public RequestLogFilter requestLogFilter() {
        return new RequestLogFilter();
    }

    @Bean
    public RequestBodyFilter requestBodyFilter() {
        return new RequestBodyFilter();
    }

    @Bean
    public Mp3LoginFilter mp3LoginFilter() {
        return new Mp3LoginFilter();
    }

    @Bean
    public PageFilter pageFilter() {
        return new PageFilter();
    }

    @Bean
    public FilterRegistrationBean requestLogFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestLogFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestLogFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean requestBodyFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestBodyFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestBodyFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean mp3LoginFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(mp3LoginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("mp3LoginFilter");
        registration.addInitParameter("ignorePath", "/static,/v1/login,/v1/logout,/v1/mp3/all,/v1/mp3/random_list,/v1/mp3/play,/v1/init2017,/login2017,/mp3/index,/mp3/random,/browser_not_support,/2017/mp3");
        registration.addInitParameter("sessionStatusKey","session_status");
        registration.addInitParameter("sessionStatusTimeout","timeout");
        registration.addInitParameter("sessionStatusTimeoutMsg","登录超时，请重新登录");
        registration.addInitParameter("sessionStatusOtherLogin","other_login");
        registration.addInitParameter("sessionStatusOtherLoginMsg","您的账号已在其它地点登录");
        registration.addInitParameter("errorMsgKey","errorMsg");
        registration.addInitParameter("errorPage","/WEB-INF/view/login.jsp");
        registration.setOrder(3);
        return registration;
    }

    @Bean
    public FilterRegistrationBean pageFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(pageFilter());
        registration.addUrlPatterns("/*");
        registration.setName("pageFilter");
        registration.addInitParameter("prefix","/WEB-INF/view");
        registration.addInitParameter("suffix",".jsp");
        registration.addInitParameter("ignorePath","/static/,/v1,/2017/mp3");
        registration.setOrder(4);
        return registration;
    }
}
