package com.cdkjframework.mp.service;

import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.mp.dto.MpDraftDto;
import com.cdkjframework.mp.dto.MpMenusDto;
import com.cdkjframework.mp.dto.MpResultDto;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.service
 * @ClassName: MpService
 * @Description: 公众号服务接口
 * @Author: xiaLin
 * @Date: 2023/1/6 20:47
 * @Version: 1.0
 */
public interface MpService {

  /**
   * 访问令牌
   */
  void accessToken();

  /**
   * 读取访问令牌
   *
   * @return 返回结果
   */
  MpResultDto readyAccessToken();

  /**
   * 创建菜单
   *
   * @param mpMenusDtos 参数
   */
  void addMenus(List<MpMenusDto> mpMenusDtos);

  /**
   * 创建菜单
   *
   * @return 返回自定义菜单
   */
  List<MpMenusDto> findMenus();

  /**
   * 删除菜单
   */
  void deleteMenus();

  /**
   * 添加草稿
   *
   * @param draftDto 内容
   */
  void addDraft(MpDraftDto draftDto);

  /**
   * 修改草稿
   *
   * @param draftDto 内容
   */
  void modifyDraft(MpDraftDto draftDto);

  /**
   * 修改草稿
   *
   * @param draftDto 内容
   */
  void deleteDraft(MpDraftDto draftDto);

  /**
   * 查询草稿
   *
   * @param draftDto 查询条件
   * @return 返回草稿
   */
  List<MpDraftDto> findDraft(MpDraftDto draftDto);

  /**
   * 查询分页数据
   *
   * @param draftDto 查询条件
   * @return 返回草稿列表
   */
  PageEntity<MpDraftDto> listDraft(MpDraftDto draftDto);

  /**
   * 发布草稿
   *
   * @param draftDto 发布内容
   */
  void publishDraft(MpDraftDto draftDto);

  /**
   * 删除发布
   *
   * @param draftDto 发布内容
   */
  void deletePublishDraft(MpDraftDto draftDto);
}
