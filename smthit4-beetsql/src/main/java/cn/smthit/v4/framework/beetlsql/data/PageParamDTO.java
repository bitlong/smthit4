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
import org.beetl.sql.core.page.PageRequest;

import lombok.Data;
import lombok.Getter;
import org.modelmapper.ModelMapper;

/**
 * @author Bean
 *
 */
@Data
public class PageParamDTO implements IParamDTO {
	/**
	 * 当前页
	 */
	private int pageNumber;

	/**
	 * 每页大小
	 */
	private int pageSize;
	
	/**
	  *  创建分页请求参数对象
	 * @return
	 */
	public PageRequest<?> createPageRequest() {
		PageRequest<?> pr = PageKit.of(pageNumber, pageSize);
		return pr;
	}


	/**
	 * 构造器
	 * 
	 * @author Bean
	 *
	 * @param <C>
	 */
	public static class Builder<C extends PageParamDTO> {
		private Class<C> cls;
		
		private Map<String, Object> prop1 = new HashMap<>();
		
		private Map<Property<?, ?>, Object> prop2 = new HashMap<>();

		private ModelMapper mapper;

		private Object mapObject;

		@Getter
		private int pageNumber;

		@Getter
		private int pageSize;
		
		private Builder(Class<C> cls) {
			this.cls = cls;
		}
		
		public static <C extends PageParamDTO>  Builder<C> builder(Class<C> cls) {
			Builder<C> builder = new Builder<>(cls);
			builder.mapper = new ModelMapper();
			return builder;
		}

		public static <C extends PageParamDTO> Builder<C> builder(Class<C> cls, ModelMapper mapper) {
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
		
		public Builder<C> pageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}
		
		public Builder<C> pageSize(Integer pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public Builder<C> map(Object source) {
			this.mapObject = source;
			return this;
		}
		
		public C build() {
			try {
				C param = cls.newInstance();

				if(mapper != null && mapObject != null) {
					mapper.map(mapObject, param);
				}

				prop1.entrySet().forEach(entry-> {
					BeanKit.setBeanProperty(param, entry.getValue(), entry.getKey());
				});
				
				prop2.entrySet().forEach(entry-> {
					Property<?, ?> property = entry.getKey();
					String name = ClassKit.getFieldName(property);
					if(name == null)
						throw new IllegalArgumentException("属性" + name + "不存在");
					BeanKit.setBeanProperty(param, entry.getValue(), name);
				});

				param.setPageNumber(this.pageNumber);
				param.setPageSize(this.pageSize);

				param.validate();
				return param;
			} catch (Exception e) {
				throw new ParamException(e.getMessage(), "");
			}
		}
		
		public C build(Class<?> group) {
			try {
				C param = cls.newInstance();

				if(mapper != null && mapObject != null) {
					mapper.map(mapObject, param);
				}

				prop1.entrySet().forEach(entry-> {
					BeanKit.setBeanProperty(param, entry.getValue(), entry.getKey());
				});
				
				prop2.entrySet().forEach(entry-> {
					Property<?, ?> property = entry.getKey();
					String name = ClassKit.getFieldName(property);
					if(name == null)
						throw new IllegalArgumentException("属性" + name + "不存在");
					BeanKit.setBeanProperty(param, entry.getValue(), name);
				});

				param.setPageNumber(this.pageNumber);
				param.setPageSize(this.pageSize);

				param.validate(group);
				
				return param;
			} catch (Exception exp) {
				throw new ParamException("构建DTO出错, 原因：" + exp.getMessage(), exp);
			}
		}
	}
}
