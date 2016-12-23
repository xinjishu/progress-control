package com.qishon.pc.domain.Controller;

import com.qishon.pc.domain.Application.ProgressControlManager;
import com.qishon.pc.domain.model.ProgressControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Created by xm on 2016/12/23.
 */
@Controller
public class ProgressControlController {
    @Autowired
    ProgressControlManager progressControlManager;

    @GetMapping("/progressControl")
    public String welcome(Map<String,Object> map,  Model model) {
        model.addAttribute("progressControls", progressControlManager.findByGirard(map));
        return "progressControl";
    }
}
