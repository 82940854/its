package com.dao;

import com.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilterDao extends JpaSpecificationExecutor<Filter>, JpaRepository<Filter, String> {
}
