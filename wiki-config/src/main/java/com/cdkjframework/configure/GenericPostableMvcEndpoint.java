package com.cdkjframework.configure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.configure
 * @ClassName: GenericPostableMvcEndpoint
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
//public class GenericPostableMvcEndpoint extends EndpointMvcAdapter {
//    /**
//     * 代理类为RefreshEndpoint
//     *
//     * @param delegate 代理
//     */
//    public GenericPostableMvcEndpoint(Endpoint<?> delegate) {
//        super(delegate);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseBody
//    @Override
//    public Object invoke() {
//        if (!getDelegate().isEnabled()) {
//            return new ResponseEntity<>(Collections.singletonMap(
//                    "message", "This endpoint is disabled"), HttpStatus.NOT_FOUND);
//        }
//        return super.invoke();
//    }
//}
