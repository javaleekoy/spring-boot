package com.peramdy.controller.student;

import com.peramdy.common.Enum.ResponseCodeEnum;
import com.peramdy.entity.Student;
import com.peramdy.service.StudentService;
import com.peramdy.utils.ResponseSimpleUtil;
import com.peramdy.utils.ResponseUtil;
import com.peramdy.utils.SaltUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by peramdy on 2017/9/16.
 */
@RestController
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String queryStuInfo(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById(stuId);
        if (student == null)
            return null;
        else
            return student.toString();
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseSimpleUtil StuInfo(@RequestParam("stuId") Integer stuId) {
        Student student = studentService.queryStudentInfoById(stuId);
        if (student == null)
            return new ResponseSimpleUtil(ResponseCodeEnum.OK);
        else
            return new ResponseUtil<Student>(ResponseCodeEnum.OK, student);
    }

    @ResponseBody
    @RequestMapping("/add")
    public ResponseSimpleUtil addStuInfo(@RequestBody Student stu) {
        Student student = studentService.addStuInfo(stu);
        if (student == null)
            return new ResponseUtil<Object>(ResponseCodeEnum.FAIL);
        else
            return new ResponseUtil<Object>(ResponseCodeEnum.OK);
    }


    @RequestMapping(value = "/slaver/info", method = RequestMethod.GET)
    public String StuInfo_slaver(@RequestParam("stuId") Integer stuId, HttpEntity<String> httpEntity, HttpServletRequest request) {

        HttpHeaders headers = httpEntity.getHeaders();

        String ip = request.getRemoteAddr();

        System.out.println(ip);

        Student student = studentService.queryStudentInfoById_slaver(stuId);
        if (student == null)
            return null;
        else
            return student.toString();
    }

    @ResponseBody
    @RequestMapping("/slaver/add")
    public String addStuInfo_slaver(@RequestBody Student stu) {
        int student = studentService.addStuInfo_slaver(stu);
        if (student > 0)
            return "success";
        else
            return "fail";
    }


    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public ModelAndView download(HttpServletRequest request) {
        String auth = request.getHeader("X-Auth-Sense");
        String timestamp = request.getHeader("Timestamp");

//        request.setHeader("X-Auth-Sense","d6f2d37c2f518ce5b2d2d299b3542e82");
//        request.setHeader("Timestamp","1506147081");
        System.out.println("Auth:" + auth);
        System.out.println("Timestamp:" + timestamp);
        ModelAndView mv = new ModelAndView();
//        request.setHeader("X-Auth-Sense", "d6f2d37c2f518ce5b2d2d299b3542e82");
//        request.setHeader("Timestamp", "1506147081");
        mv.setViewName("redirect:http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4");

//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4");
//        try {
//            requestDispatcher.forward(request, response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return mv;

//        try {
//            response.sendRedirect("http://192.168.10.241/sensepublic/GameRes/gamevideo/game_of_thrones.mp4");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
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
//                File file = new File("D://test.mp4");
//                if (!file.exists()) {
//                    file.createNewFile();
//                }

                ServletOutputStream out = response.getOutputStream();
//                OutputStream out = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int readLength = 0;
//                while ((readLength = in.read(buffer)) > 0) {
//                    byte[] bytes = new byte[readLength];
//                    System.arraycopy(buffer, 0, bytes, 0, readLength);
//                    out.write(bytes);
//                }

                while ((readLength = in.read(buffer)) > 0)
                    out.write(buffer, 0, readLength);
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }


}
