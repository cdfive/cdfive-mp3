package com.cdfive.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {
	private static Log LOG = LogFactory.getLog(PropUtil.class);
	
	public static String readProp(String key) {
		InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("system.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			LOG.error("读取系统配置文件异常", e);
			return "";
		}
		return prop.getProperty(key);
	}
	
	public static int readPropInt(String key, int defaultVal) {
		int val = defaultVal;
		String valStr;
		valStr = readProp(key);
		if (!StringUtils.isEmpty(valStr)) {
			val = Integer.parseInt(valStr);
		}
		return val;
	}
	
	public static String readDBProp(String key) {
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("db.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            LOG.error("读取系统配置文件异常", e);
            return "";
        }
        return prop.getProperty(key);
    }
	
	public static String readDevDBProp(String key) {
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("profile/dev/db.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            LOG.error("读取系统配置文件异常", e);
            return "";
        }
        return prop.getProperty(key);
    }
	
	public static String readDevSysProp(String key) {
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("profile/dev/system.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            LOG.error("读取系统配置文件异常", e);
            return "";
        }
        return prop.getProperty(key);
    }
	
	public static String readTestDBProp(String key) {
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("profile/test/db.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            LOG.error("读取系统配置文件异常", e);
            return "";
        }
        return prop.getProperty(key);
    }
	
	public static String readTestSysProp(String key) {
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("profile/test/system.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            LOG.error("读取系统配置文件异常", e);
            return "";
        }
        return prop.getProperty(key);
    }
	
	public static String readProduDBProp(String key) {
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("profile/prod/db.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            LOG.error("读取系统配置文件异常", e);
            return "";
        }
        return prop.getProperty(key);
    }
	
	public static String readProduSysProp(String key) {
        InputStream in = PropUtil.class.getClassLoader().getResourceAsStream("profile/prod/system.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            LOG.error("读取系统配置文件异常", e);
            return "";
        }
        return prop.getProperty(key);
    }
	
}
