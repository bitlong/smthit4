/**
 * 
 */
package cn.smthit.v4.eventbus.mq.spi;

import com.sgai.common.eventbus.mq.spi.data.MessagePacket;

/**
 * @author Bean
 * MQ消息通过接口，需要各个MQ中间件实现接口
 */
public interface IMQService {
	public void routeMessage(MessagePacket packet);
}
