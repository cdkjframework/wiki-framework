package com.cdkjframework.util.network.http;

import com.cdkjframework.constant.IntegerConsts;
import lombok.Getter;
import lombok.Setter;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.network.http
 * @ClassName: ClientLoginInterceptor
 * @Author: xiaLin
 * @Description: Java 类说明
 * @Date: 2024/6/9 12:22
 * @Version: 1.0
 */
@Setter
@Getter
public class ClientLoginInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

  /**
   * 用户名
   */

  private String userName;

  /**
   * 密码
   */
  private String userPassword;

  /**
   * 构建函数
   *
   * @param userName     用户名
   * @param userPassword 密码
   */
  public ClientLoginInterceptor(String userName, String userPassword) {
    super(Phase.PREPARE_SEND);
    this.userName = userName;
    this.userPassword = userPassword;
  }


  /**
   * 处理消息
   *
   * @param soap 消息
   * @throws Fault 错误异常
   */
  @Override
  public void handleMessage(SoapMessage soap) throws Fault {
    List<Header> headers = soap.getHeaders();
    Document doc = DOMUtils.createDocument();
    Element auth = doc.createElementNS("http://tempuri.org/", "SecurityHeader");
    Element userName = doc.createElement("UserName");
    userName.setTextContent(this.userName);

    Element userPass = doc.createElement("UserPass");
    userPass.setTextContent(this.userPassword);

    auth.appendChild(userName);
    auth.appendChild(userPass);

    headers.add(IntegerConsts.ZERO, new Header(new QName("SecurityHeader"), auth));
  }


}