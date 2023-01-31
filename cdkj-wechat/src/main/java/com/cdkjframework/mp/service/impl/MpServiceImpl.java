package com.cdkjframework.mp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.http.HttpRequestEntity;
import com.cdkjframework.enums.HttpMethodEnums;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.mp.config.MpAddressConfig;
import com.cdkjframework.mp.config.MpConfig;
import com.cdkjframework.mp.dto.MpDraftDto;
import com.cdkjframework.mp.dto.MpMenusDto;
import com.cdkjframework.mp.dto.MpResultDto;
import com.cdkjframework.mp.enums.MpEnum;
import com.cdkjframework.mp.service.MpService;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.util.files.FileUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpRequestUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.service
 * @ClassName: MpServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/6 20:47
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class MpServiceImpl implements MpService {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(MpServiceImpl.class);

  /**
   * 配置文件
   */
  private final MpConfig mpConfig;

  /**
   * 访问令牌
   */
  @Override
  public void accessToken() {
    String url = String.format(MpAddressConfig.accessToken, mpConfig.getAppId(), mpConfig.getAppSecret());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setMethod(HttpMethodEnums.GET);
    request.setRequestAddress(url);

    // 返回结果
    MpResultDto resultDto = HttpRequest(request);
    resultDto.setTime(System.currentTimeMillis() / IntegerConsts.ONE_THOUSAND);
    String key = String.format(CacheConsts.USER_WECHAT_MP, CurrentUser.getOrganizationId());
    RedisUtils.syncEntitySet(key, resultDto);
  }

  /**
   * 读取访问令牌
   *
   * @return 返回结果
   */
  @Override
  public MpResultDto readyAccessToken() {
    MpResultDto token = readyAccessToken(IntegerConsts.ZERO);
    if (token == null) {
      throw new GlobalRuntimeException("获取受权凭证失败！");
    }
    // 返回结果
    return token;
  }

  /**
   * 创建菜单
   *
   * @param mpMenusDtos 参数
   */
  @Override
  public void addMenus(List<MpMenusDto> mpMenusDtos) {
    MpResultDto token = readyAccessToken();
    JSONArray json = JsonUtils.listToJsonArray(mpMenusDtos);
    JSONObject button = new JSONObject();
    button.put("button", json);
    String url = String.format(MpAddressConfig.addConditionalMenus, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(button);
    // 请求数据
    HttpRequest(request);
  }

  /**
   * 创建菜单
   */
  @Override
  public List<MpMenusDto> findMenus() {
    MpResultDto token = readyAccessToken();
    String url = String.format(MpAddressConfig.queryMenus, token.getAccessToken());

    // HTTP请求
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    // 请求数据
    StringBuilder builder = HttpRequestUtils.httpRequest(request);
    JSONObject json = JsonUtils.parseObject(builder.toString());
    Integer isMenuOpen = json.getInteger("is_menu_open");
    if (isMenuOpen == null || isMenuOpen.equals(IntegerConsts.ZERO)) {
      throw new GlobalRuntimeException("菜单是未开启");
    }
    JSONObject selfmenu = json.getJSONObject("selfmenu_info");
    if (selfmenu.isEmpty()) {
      return new ArrayList<>();
    }
    JSONArray button = selfmenu.getJSONArray("button");
    if (button.isEmpty()) {
      return new ArrayList<>();
    }
    // 返回结果
    return JsonUtils.jsonArrayToList(button, MpMenusDto.class);
  }

  /**
   * 删除菜单
   */
  @Override
  public void deleteMenus() {
    MpResultDto token = readyAccessToken();
    String url = String.format(MpAddressConfig.deleteMenus, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    // 请求数据
    HttpRequest(request);
  }

  /**
   * 添加草稿
   *
   * @param draftDto 内容
   */
  @Override
  public void addDraft(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    String url = String.format(MpAddressConfig.addDraft, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto resultDto = HttpRequest(request);
  }

  /**
   * 修改草稿
   *
   * @param draftDto 内容
   */
  @Override
  public void modifyDraft(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    String url = String.format(MpAddressConfig.updateDraft, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto resultDto = HttpRequest(request);
  }

  /**
   * 修改草稿
   *
   * @param draftDto 内容
   */
  @Override
  public void deleteDraft(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    String url = String.format(MpAddressConfig.updateDraft, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto resultDto = HttpRequest(request);
  }

  /**
   * 查询草稿
   *
   * @param draftDto 查询条件
   * @return 返回草稿
   */
  @Override
  public List<MpDraftDto> findDraft(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    String url = String.format(MpAddressConfig.queryDraft, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto resultDto = HttpRequest(request);

    // 返回结果
    return resultDto.getNewsItem();
  }

  /**
   * 查询分页数据
   *
   * @param draftDto 查询条件
   * @return 返回草稿列表
   */
  @Override
  public PageEntity<MpDraftDto> listDraft(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    // 查询条数
    String url = String.format(MpAddressConfig.countDraft, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    StringBuilder builder = HttpRequestUtils.httpRequest(request);
    MpResultDto resultDto = JsonUtils.jsonStringToBean(builder.toString(), MpResultDto.class);
    // 请求数据
    url = String.format(MpAddressConfig.queryListDraft, token.getAccessToken());
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto result = HttpRequest(request);

    // 返回结果
    return PageEntity.build(draftDto.getOffset(), resultDto.getTotalCount(), result.getNewsItem());
  }

  /**
   * 发布草稿
   *
   * @param draftDto 发布内容
   */
  @Override
  public void publishDraft(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    // 发布草稿
    String url = String.format(MpAddressConfig.freePublishDraft, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto resultDto = HttpRequest(request);
  }

  /**
   * 删除发布
   *
   * @param draftDto 发布内容
   */
  @Override
  public void deletePublishDraft(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    // 发布草稿
    String url = String.format(MpAddressConfig.deletePublishDraft, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto resultDto = HttpRequest(request);
  }

  /**
   * 上传图片
   *
   * @param filePath 文件路径
   */
  @Override
  public MpResultDto uploadImage(String filePath) {
    // 请求数据
    return uploadMaterial(filePath, StringUtils.Empty);
  }

  /**
   * 上传素材
   *
   * @param filePath 文件路径
   * @param type     文件类型
   */
  @Override
  public MpResultDto addMaterial(String filePath, String type) {
    // 请求数据
    return uploadMaterial(filePath, type);
  }

  /**
   * 删除素材
   *
   * @param draftDto 删除条件
   */
  @Override
  public void deleteMaterial(MpDraftDto draftDto) {
    MpResultDto token = readyAccessToken();
    // 发布草稿
    String url = String.format(MpAddressConfig.delMaterial, token.getAccessToken());
    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setData(draftDto);
    // 请求数据
    MpResultDto resultDto = HttpRequest(request);
  }

  /**
   * 上传文件
   *
   * @param filePath 文件路径
   * @param type     类型
   * @return 返回结果
   */
  private MpResultDto uploadMaterial(String filePath, String type) {
    MpResultDto token = readyAccessToken();
    String url;
    if (StringUtils.isNullAndSpaceOrEmpty(type)) {
      url = String.format(MpAddressConfig.uploadImage, token.getAccessToken());
    } else {
      url = String.format(MpAddressConfig.addMaterial, token.getAccessToken(), type);
    }
    // 获取文件名称
    String fileName = FileUtils.getFileName(filePath);

    HttpRequestEntity request = new HttpRequestEntity();
    request.setRequestAddress(url);
    request.setName(fileName);
    // 读取文件
    File file = new File(filePath);
    try {
      FileInputStream inputStream = new FileInputStream(file);
      request.setData(inputStream);
    } catch (FileNotFoundException e) {
      logUtils.error(e);
    }
    // 请求数据
    return HttpRequest(request);
  }

  /**
   * http 请求
   *
   * @param request 参数
   * @return 返回枚举
   */
  private MpResultDto HttpRequest(HttpRequestEntity request) {
    // 请求数据
    StringBuilder builder = HttpRequestUtils.httpRequest(request);
    MpResultDto resultDto = JsonUtils.jsonStringToBean(builder.toString(), MpResultDto.class);
    MpEnum mpEnum = MpEnum.getInstance(String.valueOf(resultDto.getErrorCode()));
    if (mpEnum != MpEnum.E0) {
      throw new GlobalRuntimeException(mpEnum.getText() + StringUtils.COMMA + resultDto.getErrorMsg());
    }
    // 返回结果
    return resultDto;
  }

  /**
   * 读取访问令牌
   *
   * @return 返回结果
   */
  private MpResultDto readyAccessToken(Integer frequency) {
    if (frequency > IntegerConsts.TWO) {
      return null;
    }
    String key = String.format(CacheConsts.USER_WECHAT_MP, CurrentUser.getOrganizationId());
    MpResultDto resultDto = RedisUtils.syncGetEntity(key, MpResultDto.class);
    if (resultDto == null) {
      accessToken();
      return readyAccessToken(++frequency);
    }
    long time = System.currentTimeMillis() / IntegerConsts.ONE_THOUSAND;
    if (time - resultDto.getTime() < resultDto.getExpiresIn()) {
      return resultDto;
    } else {
      accessToken();
      return readyAccessToken(++frequency);
    }
  }
}
