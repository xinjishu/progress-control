package com.qishon.pc;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.util.RenderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * Created by xm on 2016/12/26.
 */
@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(classes = ProgressControlApplication.class)
public class FileColunmTest {

    @Test
    public void test()throws Exception{
        ProgressControl progressControl = new ProgressControl();
        progressControl.setGirard("ANTA24");
        RenderUtil renderUtil = new RenderUtil();
        renderUtil.writeScriptFile(progressControl);
    }
}
