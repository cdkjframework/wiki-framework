package com.cdkjframework.mp.service;

import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.mp.dto.MpDraftDto;
import com.cdkjframework.mp.dto.MpMenusDto;
import com.cdkjframework.mp.dto.MpResultDto;

import java.io.InputStream;
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
  void addMenusList(List<MpMenusDto> mpMenusDtos);

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

  /**
   * 上传图片
   *
   * @param inputStream 文件数据
   * @param fileName    文件名称
   * @return 返回结果
   */
  MpResultDto uploadImage(InputStream inputStream, String fileName);

  /**
   * 上传素材
   *
   * @param inputStream 文件数据
   * @param fileName    文件名称
   * @param draftDto    参数
   * @return 返回结果
   */
  MpResultDto addMaterial(InputStream inputStream, String fileName, MpDraftDto draftDto);

  /**
   * 删除素材
   *
   * @param draftDto 删除条件
   */
  void deleteMaterial(MpDraftDto draftDto);

  /**
   * 查询素材
   *
   * @return 返回结果
   */
  MpResultDto findMaterialCount();

  /**
   * 查询素材
   *
   * @param materialId 素材ID
   * @return 返回结果
   */
  MpResultDto findMaterial(String materialId);

  /**
   * 查询分页素材数据
   *
   * @param draftDto 查询条件
   * @return 返回结果
   */
  PageEntity<MpDraftDto> listMaterialPage(MpDraftDto draftDto);

  /**
   * 添加菜单
   *
   * @param mpMenusDto 菜单信息
   */
  void addMenus(MpMenusDto mpMenusDto);

  /**
   * 查询菜单
   *
   * @param mpMenusDto 菜单信息
   */
  MpMenusDto findMenus(MpMenusDto mpMenusDto);
}
