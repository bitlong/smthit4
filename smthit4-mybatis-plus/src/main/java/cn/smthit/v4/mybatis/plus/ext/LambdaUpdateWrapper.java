package cn.smthit.v4.mybatis.plus.ext;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/12  19:13
 */
public class LambdaUpdateWrapper<T> extends AbstractLambdaWrapper<T, LambdaUpdateWrapper<T>> implements Update<LambdaUpdateWrapper<T>, SFunction<T, ?>> {
    private final List<String> sqlSet;

    public LambdaUpdateWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
        this.sqlSet = new ArrayList();
    }

    public LambdaUpdateWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.initNeed();
        this.sqlSet = new ArrayList();
    }

    LambdaUpdateWrapper(T entity, Class<T> entityClass, List<String> sqlSet, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias, SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.sqlSet = sqlSet;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    public LambdaUpdateWrapper<T> set(boolean condition, SFunction<T, ?> column, Object val, String mapping) {
        return (LambdaUpdateWrapper)this.maybeDo(condition, () -> {
            String sql = this.formatParam(mapping, val);
            this.sqlSet.add(this.columnToString(column) + "=" + sql);
        });
    }

    public LambdaUpdateWrapper<T> setSql(boolean condition, String sql) {
        if (condition && StringUtils.isNotBlank(sql)) {
            this.sqlSet.add(sql);
        }

        return (LambdaUpdateWrapper)this.typedThis;
    }

    public String getSqlSet() {
        return CollectionUtils.isEmpty(this.sqlSet) ? null : String.join(",", this.sqlSet);
    }

    protected LambdaUpdateWrapper<T> instance() {
        return new LambdaUpdateWrapper(this.getEntity(), this.getEntityClass(), (List)null, this.paramNameSeq, this.paramNameValuePairs, new MergeSegments(), this.paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    public void clear() {
        super.clear();
        this.sqlSet.clear();
    }
}