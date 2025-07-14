package com.cdkjframework.ai.constant;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.ai.constant
 * @ClassName: AiConstant
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface AiConstant {
  /**
   * 系统角色
   */
  String SYSTEM = "system";

  /**
   * 助手消息
   */
  String ASSISTANT_CONTENT = "You are a helpful assistant";

  /**
   * 用户角色
   */
  String USER = "user";

  /**
   * 模式
   */
  String MODE = "mode";

  /**
   * 模型
   */
  String MODEL = "model";

  /**
   * 文件
   */
  String FILE = "file";

  /**
   * 输入
   */
  String INPUT = "input";

  /**
   * 语音
   */
  String VOICE = "voice";

  /**
   * 模型
   */
  String STREAM = "stream";

  /**
   * 模型
   */
  String PROMPT = "prompt";

  /**
   * 图片
   */
  String IMAGE = "image";

  /**
   * 蒙版
   */
  String MASK = "mask";

  /**
   * 模型
   */
  String MESSAGES = "messages";

  /**
   * 最大 token
   */
  String MAX_TOKENS = "max_tokens";

  /**
   * 上下文ID
   */
  String CONTEXT_ID = "context_id";

  /**
   * 类型
   */
  String TYPE = "type";

  /**
   * 文本
   */
  String TEXT = "text";

  /**
   * 内容
   */
  String CONTENT = "content";

  /**
   * 图片地址
   */
  String IMAGE_URL = "image_url";

  /**
   * 详情
   */
  String DETAIL = "detail";

  /**
   * 地址
   */
  String URL = "url";

  /**
   * 授权头
   */
  String BEARER = "Bearer ";

  /**
   * 授权
   */
  String AUTHORIZATION = "Authorization";

  /**
   * 授权
   */
  String CONTENT_TYPE_VALUE = "application/json";

  /**
   * form-data
   */
  String CONTENT_TYPE_MULTIPART_VALUE = "multipart/form-data";

  /**
   * deepSeek
   */
  interface DeepSeek {

    /**
     * sse对话
     */
    String DEEPSEEK_CHAT_SSE = "deepseek-chat-sse";

    /**
     * sse beta
     */
    String DEEPSEEK_BETA_SSE = "deepseek-beta-sse";

    /**
     * 对话补全
     */
    String CHAT_ENDPOINT = "/chat/completions";
    /**
     * FIM补全（beta）
     */
    String BETA_ENDPOINT = "/beta/completions";
    /**
     * 列出模型
     */
    String MODELS_ENDPOINT = "/models";
    /**
     * 余额查询
     */
    String BALANCE_ENDPOINT = "/user/balance";
  }

  /**
   * 豆包
   */
  interface DouBao {

    /**
     * 对话 SSE
     */
    String DOUBAO_CHAT_SSE = "doubao-chat-sse";

    /**
     * bots 对话 SSE
     */
    String DOUBAO_BOTS_CHAT_SSE = "doubao-botsChat-sse";

    /**
     * 豆包 CHAT_VISION SSE
     */
    String DOUBAO_CHAT_VISION_SSE = "doubao-chatVision-sse";

    /**
     * 豆包 CHAT_CONTEXT SSE
     */
    String DOUBAO_CHAT_CONTEXT_SSE = "doubao-chatContext-sse";

    /**
     * 对话
     */
    String CHAT_ENDPOINT = "/chat/completions";
    /**
     * 文本向量化
     */
    String EMBEDDING_TEXT = "/embeddings";
    /**
     * 图文向量化
     */
    String EMBEDDING_VISION = "/embeddings/multimodal";
    /**
     * 应用bots
     */
    String BOTS_CHAT = "/bots/chat/completions";
    /**
     * 分词
     */
    String TOKENIZATION = "/tokenization";
    /**
     * 批量推理chat
     */
    String BATCH_CHAT = "/batch/chat/completions";
    /**
     * 创建上下文缓存
     */
    String CREATE_CONTEXT = "/context/create";
    /**
     * 上下文缓存对话
     */
    String CHAT_CONTEXT = "/context/chat/completions";
    /**
     * 创建视频生成任务
     */
    String CREATE_VIDEO = "/contents/generations/tasks/";
    /**
     * 文生图
     */
    String IMAGES_GENERATIONS = "/images/generations";
  }

  /**
   * x Grok
   */
  interface Grok {
    /**
     * 对话
     */
    String GROK_CHAT_SSE = "grok-chat-sse";
    /**
     * 消息 sse
     */
    String GROK_MESSAGE_SSE = "grok-message-sse";
    /**
     * chatVision sse
     */
    String GROK_CHAT_VISION_SSE = "grok-chatVision-sse";
    /**
     * 对话补全
     */
    String CHAT_ENDPOINT = "/chat/completions";
    /**
     * 创建消息回复
     */
    String MESSAGES = "/messages";
    /**
     * 列出模型
     */
    String MODELS_ENDPOINT = "/models";
    /**
     * 列出语言模型
     */
    String LANGUAGE_MODELS = "/language-models";
    /**
     * 分词
     */
    String TOKENIZE_TEXT = "/tokenize-text";
    /**
     * 获取延迟对话
     */
    String DEFERRED_COMPLETION = "/chat/deferred-completion";
    /**
     * 文生图
     */
    String IMAGES_GENERATIONS = "/images/generations";
  }

  /**
   * Openai
   */
  interface Openai {

    /**
     * 对话sse
     */
    String OPENAI_CHAT_SSE = "openai-chat-sse";

    /**
     * 对话 VISION sse
     */
    String OPENAI_CHAT_VISION_SSE = "openai-chatVision-sse";

    /**
     * 对话 Reasoning sse
     */
    String OPENAI_CHAT_REASONING_SSE = "openai-chatReasoning-sse";

    /**
     * 对话
     */
    String CHAT_ENDPOINT = "/chat/completions";
    /**
     * 文生图
     */
    String IMAGES_GENERATIONS = "/images/generations";
    /**
     * 图片编辑
     */
    String IMAGES_EDITS = "/images/edits";
    /**
     * 图片变形
     */
    String IMAGES_VARIATIONS = "/images/variations";
    /**
     * 文本转语音
     */
    String TTS = "/audio/speech";
    /**
     * 语音转文本
     */
    String STT = "/audio/transcriptions";
    /**
     * 文本向量化
     */
    String EMBEDDINGS = "/embeddings";
    /**
     * 检查文本或图片
     */
    String MODERATIONS = "/moderations";
  }
}
