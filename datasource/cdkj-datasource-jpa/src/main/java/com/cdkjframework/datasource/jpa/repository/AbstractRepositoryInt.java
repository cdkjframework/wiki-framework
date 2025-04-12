package com.cdkjframework.datasource.jpa.repository;

import com.cdkjframework.datasource.jpa.builder.JapCriteriaBuilder;
import com.cdkjframework.entity.PageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.relational.jpa.repository
 * @ClassName: IRepository
 * @Description: Repository 接口类
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractRepositoryInt<T> implements IRepositoryInt<T> {
  /**
   * 查询分页数据
   *
   * @param cb 条件
   * @return 返回结果
   */
  @Override
  public PageEntity<T> listPaginated(JapCriteriaBuilder<T> cb) {
    // 构建查询条件
    Specification<T> spec = cb.build();
    // 查询分页条件
    Pageable pageable = cb.toPageable();

    // 查询分页数据
    Page<T> page = findAll(spec, pageable);

    // 返回结果
    return PageEntity.build(page.getNumber(), page.getTotalElements(), page.getContent());
  }
}
