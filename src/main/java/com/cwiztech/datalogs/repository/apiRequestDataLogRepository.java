package com.cwiztech.datalogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cwiztech.datalogs.model.APIRequestDataLog;

public interface apiRequestDataLogRepository extends JpaRepository<APIRequestDataLog,Long>{

}
