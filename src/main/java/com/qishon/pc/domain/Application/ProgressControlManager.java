package com.qishon.pc.domain.Application;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.repository.ProgressControlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuquan.hu on 2016/12/23.
 */
@Component
public class ProgressControlManager {
    @Autowired
    ProgressControlRepository progressControlRepository;
    public List<ProgressControl> findByGirard(Map<String,Object> map) {
        map.put("girard","file");
        return progressControlRepository.findByGirard(map);
    }
    public ProgressControl addProgressControl(ProgressControl progressControl){
        return progressControlRepository.addProgressControl(progressControl);
    }
    public ProgressControl updateProgress(ProgressControl progressControl){
        return progressControlRepository.updateProgress(progressControl);
    }

    public ProgressControl updateVersion(ProgressControl progressControl){
        return progressControlRepository.updateVersion(progressControl);
    }

    public ProgressControl updateCurNode(ProgressControl progressControl){
        return progressControlRepository.updateCurNode(progressControl);
    }
}
