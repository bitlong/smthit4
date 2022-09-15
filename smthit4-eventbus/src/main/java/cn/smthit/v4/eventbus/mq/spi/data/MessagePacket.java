/**
 * 
 */
package cn.smthit.v4.eventbus.mq.spi.data;

import lombok.Builder;
import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
@Builder
public class MessagePacket {
	private Header header;
	private Payload payload;
}
