package com.qishon.pc.domain.repository;

import com.qishon.pc.domain.model.ProgressSteps;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * Created by yuquan.hu on 2016/12/23.
 */
@Mapper
@CacheConfig(cacheNames = "progressSteps")
public interface ProgressStepsRepository {

    @Cacheable
    public List<ProgressSteps> findByGirardId(Map<String, Object> params);

    @Cacheable
    public ProgressSteps addProCtrlSteps(ProgressSteps progressSteps);

    @Cacheable
    public ProgressSteps updateCompletedFiles(ProgressSteps progressSteps);
}
