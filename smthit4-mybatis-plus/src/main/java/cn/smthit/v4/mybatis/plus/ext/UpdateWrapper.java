package cn.smthit.v4.mybatis.plus.ext;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/12  19:00
 */
public class UpdateWrapper<T> extends AbstractWrapper<T, String, UpdateWrapper<T>> implements Update<UpdateWrapper<T>, String> {
    private final List<String> sqlSet;

    public UpdateWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
        this.sqlSet = new ArrayList();
    }

    private UpdateWrapper(T entity, List<String> sqlSet, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias, SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        this.sqlSet = sqlSet;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.paramAlias = paramAlias;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    public String getSqlSet() {
        return CollectionUtils.isEmpty(this.sqlSet) ? null : String.join(",", this.sqlSet);
    }

    public UpdateWrapper<T> set(boolean condition, String column, Object val, String mapping) {
        return (UpdateWrapper)this.maybeDo(condition, () -> {
            String sql = this.formatParam(mapping, val);
            this.sqlSet.add(column + "=" + sql);
        });
    }

    public UpdateWrapper<T> setSql(boolean condition, String sql) {
        if (condition && StringUtils.isNotBlank(sql)) {
            this.sqlSet.add(sql);
        }

        return (UpdateWrapper)this.typedThis;
    }

    protected String columnSqlInjectFilter(String column) {
        return StringUtils.sqlInjectionReplaceBlank(column);
    }

    public LambdaUpdateWrapper<T> lambda() {
        return new LambdaUpdateWrapper(this.getEntity(), this.getEntityClass(), this.sqlSet, this.paramNameSeq, this.paramNameValuePairs, this.expression, this.paramAlias, this.lastSql, this.sqlComment, this.sqlFirst);
    }

    protected UpdateWrapper<T> instance() {
        return new UpdateWrapper(this.getEntity(), (List)null, this.paramNameSeq, this.paramNameValuePairs, new MergeSegments(), this.paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    public void clear() {
        super.clear();
        this.sqlSet.clear();
    }
}
