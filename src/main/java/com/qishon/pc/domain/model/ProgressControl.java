package com.qishon.pc.domain.model;

import lombok.Data;

import java.util.List;

/**
 * Created by yuquan.hu on 2016/12/23.
 */
@Data
public class ProgressControl {
    private  int id;
    private  String girard;
    private  String progress;
    private int version;
    private int curNode;
    private List<ProgressSteps> progressStepsList;
}
