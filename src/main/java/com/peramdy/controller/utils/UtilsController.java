package com.peramdy.controller.utils;

import com.peramdy.utils.SaltUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * Created by peramdy on 2017/9/28.
 */
@Controller
@RequestMapping("/utils")
public class UtilsController {

    @RequestMapping(value = "/download/one", method = RequestMethod.POST)
    public ModelAndView download(HttpServletRequest request) {
        String auth = request.getHeader("X-Auth-Sense");
        String timestamp = request.getHeader("Timestamp");
        System.out.println("Auth:" + auth);
        System.out.println("Timestamp:" + timestamp);
        /**filter添加到header中的字段在请求redirect或forward时没有将请求的字段带过去？？？？？**/
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4");
        return mv;
    }


    @RequestMapping(value = "/download/two", method = RequestMethod.POST)
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String auth = request.getHeader("X-Auth-Sense");
        String timestamp = request.getHeader("Timestamp");
        System.out.println("Auth:" + auth);
        System.out.println("Timestamp:" + timestamp);

        /**
         * sendRedirect 是浏览器端跳转，必然浏览器知道目标地址。
         * dispatcher.forward 是服务器端跳转，但必须得在同一个域中。
         */

        /***forward***/
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/download/three", method = RequestMethod.POST)
    public void downloadThree(HttpServletRequest request, HttpServletResponse response) {
        String auth = request.getHeader("X-Auth-Sense");
        String timestamp = request.getHeader("Timestamp");
        System.out.println("Auth:" + auth);
        System.out.println("Timestamp:" + timestamp);

        /***redirect***/
        try {
            /**
             * sendRedirect 是浏览器端跳转，必然浏览器知道目标地址。
             * dispatcher.forward 是服务器端跳转，但必须得在同一个域中。
             */
            response.sendRedirect("http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/download/four", method = RequestMethod.POST)
    public String download(HttpServletResponse response) {
        String dlUrl = "http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(dlUrl);

        Date now = new Date();
        Long second = now.getTime() / 1000;
        String salt = second.toString();
        SaltUtils saltUtils = new SaltUtils(salt, "MD5");
        String auth = saltUtils.encode("0x570xff");
        httpGet.setHeader("X-Auth-Sense", auth);
        httpGet.setHeader("Timestamp", second.toString());
        try {
            HttpResponse hr = httpClient.execute(httpGet);
            if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = hr.getEntity().getContent();
                ServletOutputStream out = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int readLength = 0;
                while ((readLength = in.read(buffer)) > 0)
                    out.write(buffer, 0, readLength);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/download/five", method = RequestMethod.POST)
    public String downloadSix() {
        String dlUrl = "http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(dlUrl);

        Date now = new Date();
        Long second = now.getTime() / 1000;
        String salt = second.toString();
        SaltUtils saltUtils = new SaltUtils(salt, "MD5");
        String auth = saltUtils.encode("0x570xff");
        httpGet.setHeader("X-Auth-Sense", auth);
        httpGet.setHeader("Timestamp", second.toString());
        try {
            HttpResponse hr = httpClient.execute(httpGet);
            if (hr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = hr.getEntity().getContent();
                File file = new File("D://test.mp4");
                if (!file.exists()) {
                    file.createNewFile();
                }

                OutputStream out = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int readLength = 0;
                while ((readLength = in.read(buffer)) > 0) {
                    byte[] bytes = new byte[readLength];
                    System.arraycopy(buffer, 0, bytes, 0, readLength);
                    out.write(bytes);
                }
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "/download/testOne", method = RequestMethod.GET)
    public void testStart(HttpServletRequest request, HttpServletResponse response) {
        String auth = request.getHeader("X-Auth-Sense");
        String timestamp = request.getHeader("Timestamp");
        System.out.println("Auth:" + auth);
        System.out.println("Timestamp:" + timestamp);


        /***参数带到过去***/
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/utils/testTwo");

        try {
//            requestDispatcher.forward(request,response);
            /***参数未带到过去***/
            response.setHeader("X-Auth-Sense", "11111");
            response.setHeader("Timestamp", "22222");

            response.sendRedirect("/utils/testTwo");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/testTwo", method = RequestMethod.GET)
    public void testEnd(HttpServletRequest request, HttpServletResponse response) {
        String auth = request.getHeader("X-Auth-Sense");
        String timestamp = request.getHeader("Timestamp");
        System.out.println("Auth:" + auth);
        System.out.println("Timestamp:" + timestamp);
        System.out.println("--------------------------------");
        System.out.println(response.getHeader("X-Auth-Sense"));
        System.out.println(response.getHeader("Timestamp"));
    }


}
