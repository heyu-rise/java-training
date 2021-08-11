package org.heyu.homework0709.database;

import java.lang.reflect.Method;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.heyu.homework0709.dao.db1.OrderMapper;
import org.heyu.homework0709.dao.db2.OrderMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author heyu
 * @date 2021/8/10
 */
@Aspect
@Component
public class SqlTypeAop {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderMapper2 orderMapper2;

	@Pointcut("@annotation(org.heyu.homework0709.database.SqlType)")
	public void sqlType() {
	}

	@Before("sqlType()")
	public void aroundLog(JoinPoint point) {
		MethodSignature sign = (MethodSignature) point.getSignature();
		Method method = sign.getMethod();
		SqlType sqlType = method.getAnnotation(SqlType.class);
		SqlTypeEnum sqlTypeEnum = sqlType.value();
		if (Objects.equals(sqlTypeEnum, SqlTypeEnum.MODIFY)) {
			SqlTypeUtils.setValue(orderMapper);
		} else {
			SqlTypeUtils.setValue(orderMapper2);
		}
	}

}
