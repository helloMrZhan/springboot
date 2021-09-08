package com.zjq.controller;

import com.zjq.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * 描述
 *
 * zjq
 * @version 1.0
 * @package com.zjq.controller *
 * @since 1.0
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /**
     * 默认的情况下  模板的目录在/resources/templates  类似于jsp中的前缀目录
     * 默认的情况下  后缀:就是.html  类似于jsp中配置的后缀.jsp
     *
     *
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model){
        //qurest.setAttriubte(key,value);
        model.addAttribute("hello","hello world");
        //设置数据 为list
        List<User> users = new ArrayList<>();
        users.add(new User(1001,"张三",20));
        users.add(new User(1002,"李四",21));
        users.add(new User(1003,"王五",88));
        model.addAttribute("mylist",users);

        //设置数据 为map

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("name","zhangsan");

        model.addAttribute("dataMap",dataMap);
        String[] names = new String[]{"zhangsan","lisi","wangwu"};
        model.addAttribute("names",names);

        //时间

        model.addAttribute("date",new Date());

        model.addAttribute("age",18);
        model.addAttribute("class1","a");
        model.addAttribute("class2","b");


        return "demo";
    }
}
