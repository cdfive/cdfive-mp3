package com.cdfive.core.util;

import com.cdfive.mp3.controller.Mp3Controller;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class ClassUtil {
	public static String[] getParamterNames(Class<?> clazz, String clazzName, String methodName) {
		try {
			ClassPool pool = ClassPool.getDefault();
			ClassClassPath classPath = new ClassClassPath(clazz);
			pool.insertClassPath(classPath);

			CtClass cc = pool.get(clazzName);
			CtMethod cm = cc.getDeclaredMethod(methodName);
			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			if (attr == null) {
				// exception
			}
			String[] paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++) {
				paramNames[i] = attr.variableName(i + pos);
			}
			return paramNames;
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		getParamterNames(ClassUtil.class, Mp3Controller.class.getName(), "update");
	}
}
