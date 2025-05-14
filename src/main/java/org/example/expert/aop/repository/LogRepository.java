package org.example.expert.aop.repository;

import org.example.expert.aop.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

//Lv3-11.Transaction 심화
public interface LogRepository extends JpaRepository<Log, Long> {}
