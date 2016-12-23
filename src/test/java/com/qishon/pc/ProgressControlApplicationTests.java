package com.qishon.pc;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.service.ProgressControlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgressControlApplicationTests {

	@Autowired
	private ProgressControlService controlService;
	@Test
	public void contextLoads() {
		Map<String,Object> map = new HashMap();
		map.put("girard","file");
		List<ProgressControl> rows = controlService.findByGirard(map);
		for (ProgressControl progressControl:rows){
			System.out.println(progressControl);
		}
	}

}
