package com.qishon.pc.domain.model;

import lombok.Data;

/**
 * Created by yuquan.hu on 2016/12/23.
 */
@Data
public class ProgressSteps {
    private int id;
    private int girardId;
    private String stepNo;
    private String stepName;
    private int allFiles;
    private int completedFiles;

}
