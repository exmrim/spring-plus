package org.example.expert.aop.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.expert.aop.entity.Log;
import org.example.expert.aop.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Log log) {
        logRepository.save(log);
    }

}
