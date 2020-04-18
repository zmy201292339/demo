package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping("/say")
    public ModelAndView say() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "SpringBoot 大爷你好！");
        mav.setViewName("helloWorld");
        return mav;
    }

    @GetMapping("/getIcon")
    @ResponseBody
    public void getImage(HttpServletResponse response) {
        try {
            URL url = new URL("http://10.50.25.67:8888/group1/M00/00/00/CjIZQ16X93aAZ1UXAAADppfWUUA559.jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());

            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            response.reset();
            response.setContentType("image/jpg");

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
