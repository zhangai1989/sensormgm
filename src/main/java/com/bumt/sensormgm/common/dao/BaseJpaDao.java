package com.bumt.sensormgm.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 *
 *@author : zhangai
 *@date : 11:05 2018/5/30
 *@description：
 */
@NoRepositoryBean
public interface BaseJpaDao<T> extends JpaRepository<T, Serializable>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Serializable> {

}
