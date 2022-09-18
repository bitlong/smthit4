package cn.smthit.v4.mybatis.plus.ext;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/8/12  19:30
 */
public interface EntityMapper<T> extends BaseMapper<T> {
    Class<?> getMapperClass();

    default QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        return queryWrapper;
    }

    default LambdaQueryWrapper<T> lambdaQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }


    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean saveOrUpdate(T entity) {
        if (null == entity) {
            return false;
        } else {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
            Object idVal = tableInfo.getPropertyValue(entity, tableInfo.getKeyProperty());
            return !StringUtils.checkValNull(idVal) && !Objects.isNull(selectById((Serializable)idVal)) ? SqlHelper.retBool(updateById(entity)) : SqlHelper.retBool(this.insert(entity));
        }
    }
    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = this.getSqlStatement(SqlMethod.INSERT_ONE);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);
        });
    }

    default String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(getMapperClass(), sqlMethod);
    }


    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(currentModelClass());
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!", new Object[0]);
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!", new Object[0]);
        Log log = LogFactory.getLog(this.getClass());

        return SqlHelper.saveOrUpdateBatch(this.currentModelClass(), this.getClass(), log, entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = tableInfo.getPropertyValue(entity, keyProperty);
            return StringUtils.checkValNull(idVal) || CollectionUtils.isEmpty(sqlSession.selectList(this.getSqlStatement(SqlMethod.SELECT_BY_ID), entity));
        }, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
            param.put("et", entity);
            sqlSession.update(this.getSqlStatement(SqlMethod.UPDATE_BY_ID), param);
        });
    }


    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE_BY_ID);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
            param.put("et", entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    default T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        Log log = LogFactory.getLog(this.getClass());
        return throwEx ? this.selectOne(queryWrapper) : SqlHelper.getObject(log, selectList(queryWrapper));
    }

    default Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        Log log = LogFactory.getLog(this.getClass());
        return (Map)SqlHelper.getObject(log, selectMaps(queryWrapper));
    }

    default  <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        Log log = LogFactory.getLog(this.getClass());
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }

    default  <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        Log log = LogFactory.getLog(this.getClass());
        return SqlHelper.executeBatch(this.currentModelClass(), log, list, batchSize, consumer);
    }

    default  <E> boolean executeBatch(Collection<E> list, BiConsumer<SqlSession, E> consumer) {
        return this.executeBatch(list, 1000, consumer);
    }

    default boolean removeById(Serializable id) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(currentModelClass());
        return tableInfo.isWithLogicDelete() && tableInfo.isWithUpdateFill() ? this.removeById(id, true) : SqlHelper.retBool(deleteById(id));
    }


    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean removeByIds(Collection<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        } else {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(currentModelClass());
            return tableInfo.isWithLogicDelete() && tableInfo.isWithUpdateFill() ? this.removeBatchByIds(list, true) : SqlHelper.retBool(deleteBatchIds(list));
        }
    }

    default boolean removeById(Serializable id, boolean useFill) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(currentModelClass());
        if (useFill && tableInfo.isWithLogicDelete() && !currentModelClass().isAssignableFrom(id.getClass())) {
            T instance = tableInfo.newInstance();
            tableInfo.setPropertyValue(instance, tableInfo.getKeyProperty(), new Object[]{id});
            return this.removeById((Serializable) instance);
        } else {
            return SqlHelper.retBool(deleteById(id));
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean removeBatchByIds(Collection<?> list, boolean useFill) {
        return this.removeBatchByIds(list, 1000, useFill);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean removeBatchByIds(Collection<?> list, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(currentModelClass());
        return this.removeBatchByIds(list, batchSize, tableInfo.isWithLogicDelete() && tableInfo.isWithUpdateFill());
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    default boolean removeBatchByIds(Collection<?> list, int batchSize, boolean useFill) {
        Class<?> entityClass = currentModelClass();

        String sqlStatement = this.getSqlStatement(SqlMethod.DELETE_BY_ID);
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        return this.executeBatch(list, batchSize, (sqlSession, e) -> {
            if (useFill && tableInfo.isWithLogicDelete()) {
                if (entityClass.isAssignableFrom(e.getClass())) {
                    sqlSession.update(sqlStatement, e);
                } else {
                    T instance = tableInfo.newInstance();
                    tableInfo.setPropertyValue(instance, tableInfo.getKeyProperty(), new Object[]{e});
                    sqlSession.update(sqlStatement, instance);
                }
            } else {
                sqlSession.update(sqlStatement, e);
            }

        });
    }

    default List<Object> listObjs() {
        return this.listObjs(Function.identity());
    }

    default <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return this.listObjs(Wrappers.emptyWrapper(), mapper);
    }

    default List<Object> listObjs(Wrapper<T> queryWrapper) {
        return this.listObjs(queryWrapper, Function.identity());
    }

    default <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return (List)selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

}