package com.zjq.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import javax.servlet.http.HttpSession;

/**
 * Session测试
 * @author 共饮一杯无
 */
@RestController
@SessionAttributes("testAttr")
@RequestMapping(value = "/session")
public class SessionController {

    @GetMapping("/setSession")
    public String setSession(HttpSession session) {
        session.setAttribute("testAttr", "Hello from Session");
        return "Session data set!";
    }

    @GetMapping("/getSession")
    public String getSession(HttpSession session) {
        String attrValue = (String) session.getAttribute("testAttr");
        return attrValue;
    }
}
