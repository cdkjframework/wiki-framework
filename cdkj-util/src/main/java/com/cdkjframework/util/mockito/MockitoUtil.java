package com.cdkjframework.util.mockito;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.util.mockito
 * @ClassName: MockitoUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)
//@WebAppConfiguration
@Ignore
public abstract class MockitoUtil {

    /**
     * 数据模型
     */
    protected MockMvc mockMvc;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this.getClass().getTypeName());
    }

}
