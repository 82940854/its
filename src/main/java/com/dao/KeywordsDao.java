package com.dao;

import com.entity.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KeywordsDao extends JpaSpecificationExecutor<Keywords>, JpaRepository<Keywords, String> {
}
