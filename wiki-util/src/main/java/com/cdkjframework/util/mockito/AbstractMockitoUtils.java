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
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.util.mockito
 * @ClassName: MockitoUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockServletContext.class)
@Ignore
public abstract class AbstractMockitoUtils {

    /**
     * 数据模型
     */
    protected MockMvc mockMvc;

    /**
     * 初始化
     */
    @Before
    public void init() {
        MockitoAnnotations.openMocks(this.getClass().getTypeName());
    }

}
