package com.lawu.eshop.merchant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leach
 * @date 2017/3/7
 * <p>
 * 商家系统启动入口 
 */
@RestController
@EnableAutoConfiguration
public class Bootstrap {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Bootstrap.class, args);
    }
}
