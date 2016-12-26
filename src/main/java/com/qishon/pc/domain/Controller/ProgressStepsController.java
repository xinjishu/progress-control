package com.qishon.pc.domain.Controller;

import com.qishon.pc.domain.Application.ProgressStepsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Created by xm on 2016/12/26.
 */
@Controller
public class ProgressStepsController {
    @Autowired
    ProgressStepsManager progressStepsManager;

    @GetMapping("/progressSteps")
    public String welcome(Map<String,Object> map, Model model) {
        model.addAttribute("progressStepss",progressStepsManager.findByGirardId(map));
        return "progressSteps";
    }
}
