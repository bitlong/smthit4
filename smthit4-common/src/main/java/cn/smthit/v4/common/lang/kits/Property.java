/**
 * 
 */
package cn.smthit.v4.common.lang.kits;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Bean
 *
 */
public interface Property<T, R> extends Function<T, R>, Serializable {

}
