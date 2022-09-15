/**
 * 
 */
package cn.smthit.v4.eventbus.mq.spi.data;

import lombok.Data;

import java.util.Date;

/**
 * @author Bean
 *
 */
@Data
public class Header {
	private long messageId;
	private Date sendTime;
}
