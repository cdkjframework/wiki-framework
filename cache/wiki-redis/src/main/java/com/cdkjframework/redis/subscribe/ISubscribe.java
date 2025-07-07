package com.cdkjframework.redis.subscribe;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.redis.subscribe
 * @ClassName: ISubscribe
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/7/1 13:33
 * @Version: 1.0
 */
public interface ISubscribe {

	/**
	 * 订阅消息回调
	 *
	 * @param channel 渠道.
	 * @param message 消息.
	 */
	void subscribe(String channel, String message);

	/**
	 * 从模式订阅接收的消息
	 *
	 * @param pattern 模式
	 * @param channel 渠道.
	 * @param message 消息.
	 */
	void subscribe(String pattern, String channel, String message);
}
