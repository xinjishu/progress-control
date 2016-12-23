package com.qishon.pc.domain.service;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.repository.ProgressControlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xm on 2016/12/23.
 */
@Service
public class ProgressControlService {
    @Autowired
    ProgressControlRepository progressControlRepository;

    public List<ProgressControl> findByGirard(Map<String, Object> params){
        return progressControlRepository.findByGirard(params);
    }
}
