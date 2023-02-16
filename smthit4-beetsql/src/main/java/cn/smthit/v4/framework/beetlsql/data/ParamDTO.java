/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data;

import java.util.HashMap;
import java.util.Map;

import cn.smthit.v4.common.lang.kits.ClassKit;
import cn.smthit.v4.common.lang.kits.Property;
import cn.smthit.v4.framework.beetlsql.exception.ParamException;
import org.beetl.sql.clazz.kit.BeanKit;
import org.modelmapper.ModelMapper;


/**
 * @author Bean
 *
 */
public class ParamDTO implements IParamDTO {

	/**
	 * 构造器
	 * 
	 * @author Bean
	 *
	 * @param <C>
	 */
	public static class Builder<C extends ParamDTO> {
		private Class<C> cls;
		
		private Map<String, Object> prop1 = new HashMap<>();
		
		private Map<Property<?, ?>, Object> prop2 = new HashMap<>();
		
		private ModelMapper mapper;
		
		private Object mapObject;
		
		private Builder(Class<C> cls) {
			this.cls = cls;
		}
		
		public static <C extends ParamDTO>  Builder<C> builder(Class<C> cls) {
			Builder<C> builder = new Builder<>(cls);
			builder.mapper = new ModelMapper();
			return builder;
		}
		
		public static <C extends ParamDTO>  Builder<C> builder(Class<C> cls, ModelMapper mapper) {
			Builder<C> builder = new Builder<>(cls);
			builder.mapper = mapper;
			return builder;
		}
		
		public Builder<C> setMapper(ModelMapper mapper) {
			this.mapper = mapper;
			return this;
		} 
		
		public Builder<C> property(Property<C, ?> property, Object value) {
			prop2.put(property, value);
			return this;
		}
		
		public Builder<C> property(String propertyName, Object value) {
			prop1.put(propertyName, value);
			return this;
		}
		
		public Builder<C> map(Object source) {
			this.mapObject = source;
			return this;
		}

		public C build() {
			try {
				C thisObj = internalBuild();
				thisObj.validate();
				return thisObj;
			} catch (Exception e) {
				throw new ParamException(e.getMessage(), "");
			}
		}
		
		public C build(Class<?> group) {
			try {
				C thisObj = internalBuild();
				thisObj.validate(group);
				return thisObj;
			} catch (Exception exp) {
				throw new ParamException("构建DTO出错, 原因：" + exp.getMessage(), exp);
			}
		}
		
		private C internalBuild() {
			try {
				C thisObj = cls.newInstance();
				
				if(mapper != null && mapObject != null) {
					mapper.map(mapObject, thisObj);
				}
				
				prop1.entrySet().forEach(entry-> {
					BeanKit.setBeanProperty(thisObj, entry.getValue(), entry.getKey());
				});
				
				prop2.entrySet().forEach(entry-> {
					Property<?, ?> property = entry.getKey();
					String name = ClassKit.getFieldName(property);
					if(name == null)
						throw new IllegalArgumentException("属性" + name + "不存在");
					BeanKit.setBeanProperty(thisObj, entry.getValue(), name);
				});					
				return thisObj;
			} catch (Exception exp) {
				throw new ParamException("构建DTO出错, 原因：" + exp.getMessage(), exp);
			}
			
			
		}
	}
}
