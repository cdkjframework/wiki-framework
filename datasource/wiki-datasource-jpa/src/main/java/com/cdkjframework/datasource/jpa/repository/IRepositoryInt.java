package com.cdkjframework.datasource.jpa.repository;

import com.cdkjframework.datasource.jpa.builder.JpaCriteriaBuilder;
import com.cdkjframework.entity.PageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ProjectName: com.cdkjframework
 * @Package: com.cdkjframework.datasource.jpa.repository
 * @ClassName: IRepositoryInt
 * @Description: Repository 接口类
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface IRepositoryInt <T> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {

    /**
     * 保存数据
     *
     * @param s   实体
     * @param <S> 实体集
     * @return 返回结果
     */
    @Override
    <S extends T> S save(S s);

    /**
     * 查询分页数据
     *
     * @param pageable 分页集
     * @return 返回结果
     */
    @Override
    Page<T> findAll(Pageable pageable);

    /**
     * 删除指定数据
     *
     * @param entity 实体
     */
    @Override
    void delete(T entity);

    /**
     * 批量删除
     *
     * @param iterable
     */
    @Override
    void deleteAll(Iterable<? extends T> iterable);


    /**
     * 查询分页数据
     *
     * @param cb 条件
     * @return 返回结果
     */
    PageEntity<T> listPaginated(JpaCriteriaBuilder<T> cb);
}
