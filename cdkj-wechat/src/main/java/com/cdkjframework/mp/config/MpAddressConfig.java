package com.cdkjframework.mp.config;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.config
 * @ClassName: MpAddressConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/6 20:41
 * @Version: 1.0
 */
public class MpAddressConfig {

  /**
   * token
   */
  public final static String accessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

  /**
   * 创建菜单
   */
  public final static String createMenus = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
  /**
   * 查询菜单
   */
  public final static String queryMenus = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=%s";
  /**
   * 删除菜单
   */
  public final static String deleteMenus = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";
  /**
   * 创建个性化菜单
   */
  public final static String addConditionalMenus = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=%s";
  /**
   * 删除个性化菜单
   */
  public final static String delConditionalMenus = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=%s";
  /**
   * 测试个性化菜单匹配结果
   */
  public final static String tryMatchConditionalMenus = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=%s";
  /**
   * 获取自定义菜单配置
   */
  public final static String queryConditionalMenus = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";
  /**
   * 添加草稿
   */
  public final static String addDraft = "https://api.weixin.qq.com/cgi-bin/draft/add?access_token=%s";
  /**
   * 查询草稿
   */
  public final static String queryDraft = "https://api.weixin.qq.com/cgi-bin/draft/get?access_token=%s";
  /**
   * 删除草稿
   */
  public final static String deleteDraft = "https://api.weixin.qq.com/cgi-bin/draft/delete?access_token=%s";
  /**
   * 修改草稿
   */
  public final static String updateDraft = "https://api.weixin.qq.com/cgi-bin/draft/update?access_token=%s";
  /**
   * 获取草稿总数
   */
  public final static String countDraft = "https://api.weixin.qq.com/cgi-bin/draft/count?access_token=%s";
  /**
   * 获取草稿列表
   */
  public final static String queryListDraft = "https://api.weixin.qq.com/cgi-bin/draft/batchget?access_token=%s";

  /**
   * 发布
   */
  public final static String freePublishDraft = "https://api.weixin.qq.com/cgi-bin/freepublish/submit?access_token=%s";

  /**
   * 删除发布
   */
  public final static String deletePublishDraft = "https://api.weixin.qq.com/cgi-bin/freepublish/delete?access_token=%s";

  /**
   * 上传图片
   */
  public final static String uploadImage = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=%s";

  /**
   * 上传素材
   */
  public final static String addMaterial = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%s&type=%s";

  /**
   * 删除素材
   */
  public final static String delMaterial = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=%s";

  /**
   * 获取素材
   */
  public final static String getMaterial = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";

  /**
   * 批量获取素材
   */
  public final static String batchGetMaterial = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";

  /**
   * 获取素材总量
   */
  public final static String getMaterialCount = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=%s";
}
