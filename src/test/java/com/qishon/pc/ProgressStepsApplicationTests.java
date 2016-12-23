package com.qishon.pc;


import com.qishon.pc.domain.model.ProgressSteps;
import com.qishon.pc.domain.service.ProgressStepsSerivce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgressStepsApplicationTests {

	@Autowired
	private ProgressStepsSerivce stepsSerivce;
	@Test
	public void contextLoads() {
		Map<String,Object> map = new HashMap();
		map.put("girardId",1);
		List<ProgressSteps> rows = stepsSerivce.findByGirardId(map);
		for (ProgressSteps progressSteps:rows){
			System.out.println(progressSteps);
		}
	}

}
