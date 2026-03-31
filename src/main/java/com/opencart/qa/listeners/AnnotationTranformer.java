package com.opencart.qa.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.ITestAnnotation;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;

public class AnnotationTranformer implements IAnnotationTransformer {

	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		annotation.setRetryAnalyzer((Class<? extends IRetryAnalyzer>) (Class<?>) Retry.class);

	}

}
