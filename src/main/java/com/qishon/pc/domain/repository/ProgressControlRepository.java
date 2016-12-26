package com.qishon.pc.domain.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import  com.qishon.pc.domain.model.ProgressControl;
import java.util.List;
import java.util.Map;

/**
 * Created by yuquan.hu on 2016/12/23.
 */

@Mapper
@CacheConfig(cacheNames = "progressControls")
public interface ProgressControlRepository {
    @Cacheable
    public List<ProgressControl> findByGirard(Map<String, Object> params);
    @Cacheable
    public ProgressControl addProgressControl(ProgressControl progressControl);
    @Cacheable
    public ProgressControl updateProgress(ProgressControl progressControl);
    @Cacheable
    public ProgressControl updateVersion(ProgressControl progressControl);
    @Cacheable
    public ProgressControl updateCurNode(ProgressControl progressControl);
}
