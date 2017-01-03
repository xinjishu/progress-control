package com.qishon.pc;

import com.qishon.pc.domain.util.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xm on 2016/12/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ProgressControlApplication.class)
public class HelloApplicationTests {
    @Autowired
    private Sender sender;
    @Test
    public void hello() throws Exception {
        sender.send(1,"ANTA24","渲染");
    }
}