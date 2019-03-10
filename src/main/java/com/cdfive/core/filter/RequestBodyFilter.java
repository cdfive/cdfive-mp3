package com.cdfive.core.filter;

import com.cdfive.core.util.StreamUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 过滤器,使用AppHttpServletRequestWrapper封装request
 */
public class RequestBodyFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
		
//		ServletRequest requestWrapper = null;
//		if (request instanceof HttpServletRequest) {
//			requestWrapper = new RequestBodyWrapper((HttpServletRequest) request);
//		}
//		if (requestWrapper == null) {
//			chain.doFilter(request, response);
//		} else {
//			chain.doFilter(requestWrapper, response);
//		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}

class RequestBodyWrapper extends HttpServletRequestWrapper {

	private final byte[] body;

	public RequestBodyWrapper(HttpServletRequest request) throws IOException {
		super(request);
		body = StreamUtil.getByteFromStream(request.getInputStream());
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}
		};
	}

}