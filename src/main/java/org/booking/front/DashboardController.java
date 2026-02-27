package org.booking.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class DashboardController {

    @GetMapping("/dashboard")
    public String index() {
        return "layout/main";
    }
}

