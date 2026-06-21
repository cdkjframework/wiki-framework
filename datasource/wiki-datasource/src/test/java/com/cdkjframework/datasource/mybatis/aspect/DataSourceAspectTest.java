package com.cdkjframework.datasource.mybatis.aspect;

import com.cdkjframework.datasource.mybatis.annotation.Slave;
import com.cdkjframework.datasource.mybatis.enums.DataSourceType;
import com.cdkjframework.datasource.mybatis.holder.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DataSourceAspectTest {

  private final DataSourceAspect dataSourceAspect = new DataSourceAspect();

  @AfterEach
  void tearDown() {
    DataSourceContextHolder.clear();
  }

  @Test
  void shouldSwitchToSlaveWhenMethodHasSlaveAnnotation() throws NoSuchMethodException {
    JoinPoint joinPoint = buildJoinPoint(SlaveMapperImpl.class, SlaveMapper.class.getMethod("select"));

    dataSourceAspect.beforeMethod(joinPoint);

    assertEquals(DataSourceType.SLAVE, DataSourceContextHolder.getDataSourceType());
  }

  @Test
  void shouldSwitchToMasterWhenMethodHasNoSlaveAnnotation() throws NoSuchMethodException {
    JoinPoint joinPoint = buildJoinPoint(MasterMapperImpl.class, MasterMapper.class.getMethod("select"));

    dataSourceAspect.beforeMethod(joinPoint);

    assertEquals(DataSourceType.MASTER, DataSourceContextHolder.getDataSourceType());
  }

  @Test
  void shouldClearContextAfterAdvice() {
    DataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE);

    dataSourceAspect.afterMethod();

    assertNull(DataSourceContextHolder.getDataSourceType());
  }

  private JoinPoint buildJoinPoint(Class<?> targetClass, Method interfaceMethod) {
    JoinPoint joinPoint = mock(JoinPoint.class);
    MethodSignature signature = mock(MethodSignature.class);

    when(joinPoint.getSignature()).thenReturn(signature);
    when(joinPoint.getTarget()).thenReturn(createTarget(targetClass));
    when(signature.getMethod()).thenReturn(interfaceMethod);
    when(signature.getName()).thenReturn(interfaceMethod.getName());
    when(signature.getParameterTypes()).thenReturn(interfaceMethod.getParameterTypes());
    when(signature.getDeclaringTypeName()).thenReturn(interfaceMethod.getDeclaringClass().getName());

    return joinPoint;
  }

  private Object createTarget(Class<?> targetClass) {
    try {
      return targetClass.getDeclaredConstructor().newInstance();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  interface MasterMapper {
    void select();
  }

  interface SlaveMapper {
    @Slave
    void select();
  }

  static class MasterMapperImpl implements MasterMapper {
    @Override
    public void select() {
    }
  }

  static class SlaveMapperImpl implements SlaveMapper {
    @Override
    public void select() {
    }
  }
}