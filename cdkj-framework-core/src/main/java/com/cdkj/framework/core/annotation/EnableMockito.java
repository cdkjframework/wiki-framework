package com.cdkj.framework.core.annotation;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.util.mockito
 * @ClassName: EnableMockito
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@RunWith(MockitoJUnitRunner.Silent.class)
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public @interface EnableMockito {

}
