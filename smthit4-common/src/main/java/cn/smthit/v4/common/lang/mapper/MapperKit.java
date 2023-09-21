package cn.smthit.v4.common.lang.mapper;

import lombok.Getter;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.mapstruct.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.CustomMatchingStrategy;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MatchingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dinghq
 * @date 2023/8/11
 */
public class MapperKit {
    @Getter
    private static ModelMapper mapper = new ModelMapper();
    private static ModelMapper strictMapper = new ModelMapper();

    public final static MatchingStrategy CUSTOM_STRATEGY = new CustomMatchingStrategy();

    static {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setFullTypeMatchingRequired(true);
        mapper.getConfiguration().setSourceNamingConvention(NamingConventions.NONE);
        mapper.getConfiguration().setDestinationNamingConvention(NamingConventions.NONE);
        mapper.getConfiguration().setMatchingStrategy(CUSTOM_STRATEGY);

        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setFullTypeMatchingRequired(true);
        mapper.getConfiguration().setSourceNamingConvention(NamingConventions.NONE);
        mapper.getConfiguration().setDestinationNamingConvention(NamingConventions.NONE);

        strictMapper.getConfiguration().setAmbiguityIgnored(true);
        strictMapper.getConfiguration().setFullTypeMatchingRequired(true);
        strictMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static void map(Object src, Object desc) {
        mapper.map(src, desc);
    }

    public  static <T> T map(Object src, Class<T> cls) {
        return mapper.map(src, cls);
    }

    public static <T> List<T> map(List<?> src, Class<T> cls) {
        if(src == null || src.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>();
        for(Object item : src) {
            result.add(mapper.map(item, cls));
        }

        return result;
    }

    public static ModelMapper strictMapper() {
        return strictMapper;
    }

}