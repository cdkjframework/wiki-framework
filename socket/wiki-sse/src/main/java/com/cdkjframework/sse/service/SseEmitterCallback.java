package com.cdkjframework.sse.service;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.sse.service
 * @ClassName: DisconnectCallback
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/11/4 22:07
 * @Version: 1.0
 */
public interface SseEmitterCallback {

    /**
     * 断开连接回调
     *
     * @param sessionId 会话ID
     */
    void onDisconnect(String sessionId);

    /**
     * 连接回调
     *
     * @param sessionId 会话ID
     */
    void onConnect(String sessionId);

    /**
     * 错误回调
     *
     * @param sessionId 会话ID
     * @param throwable 异常信息
     */
    void onError(String sessionId, Throwable throwable);

    /**
     * 超时回调
     *
     * @param sessionId 会话ID
     */
    void onTimeout(String sessionId);

    /**
     * 完成回调
     *
     * @param sessionId 会话ID
     */
    void onCompletion(String sessionId);

    /**
     * 发布成功回调
     *
     * @param sessionId 会话ID
     * @param message   发布的消息内容
     */
    void onPublishSuccess(String sessionId, String message);

    /**
     * 心跳回调
     *
     * @param sessionId 会话ID
     */
    void onHeartbeat(String sessionId);
}
