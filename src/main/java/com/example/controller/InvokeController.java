package com.example.controller;

import com.example.service.InvokeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;

import static com.example.service.FileService.getAllFileName;

@RestController
@RequestMapping(value = "/api/")
public class InvokeController {
    @Autowired
    InvokeService service;

    /**
     * 功能描述:
     * 指定fileName读指定文件，否则读dir目录下全部日志
     * fileName > 文件名
     * dirName > log下的目录名
     * Date:2019-02-15 > Author:Chaiyibo
     **/
    @RequestMapping(value = "/invoke/detail", method = RequestMethod.POST)
    public void detail(@RequestParam(value = "fileName", required = false) String fileName, @RequestParam(value = "dirName") String dirName, HttpServletResponse response) {
        String root = System.getProperty("user.dir");
        String baseFilePath = root + File.separator + "src\\main\\resources\\logs\\" + dirName;
        if (Strings.isNotBlank(fileName)) {
            //指定文件名，则读一个文件
            service.read2fileName(fileName, baseFilePath, response);
        } else {
            //未指定文件，则读目录下所有文件
            ArrayList<String> listFileName = new ArrayList();
            getAllFileName(baseFilePath, listFileName);
            service.read2fileNameList(listFileName, baseFilePath, response);
        }

    }

    /**
     * 功能描述:
     * 指定fileName读指定文件，否则读dir目录下全部日志
     * fileName > 文件名
     * dirName > log下的目录名
     * Date:2019-02-15 > Author:Chaiyibo
     **/
    @RequestMapping(value = "/invoke/statis", method = RequestMethod.POST)
    public String statis(@RequestParam(value = "fileName", required = false) String fileName,
                         @RequestParam(value = "dirName") String dirName,
                         @RequestParam(value = "apikey") String apikey,
                         @RequestParam(value = "username") String username) {
        String root = System.getProperty("user.dir");
        String baseFilePath = root + File.separator + "src\\main\\resources\\logs\\" + dirName;
        ArrayList<String> listFileName = new ArrayList();
        getAllFileName(baseFilePath, listFileName);
        Integer singleSize = service.read2fileName4Statis(fileName, listFileName, baseFilePath, apikey, username);
        return "---apikey:"+apikey+",username:"+username+",一台机器有效调用为:"+singleSize.toString()+",6台为:"+singleSize * 6+"条---";
    }
}
