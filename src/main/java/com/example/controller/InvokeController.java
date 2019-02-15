package com.example.controller;

import com.example.service.InvokeService;
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

    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    public void invoke(@RequestParam(value = "fileName") String fileName, @RequestParam(value = "dirName") String dirName, HttpServletResponse response) {
        String root = System.getProperty("user.dir");
        String baseFilePath = root + File.separator + "src\\main\\resources\\logs\\" + dirName;
        //ArrayList<String> listFileName = new ArrayList();
//        getAllFileName(baseFilePath, listFileName);
//        for (String name : listFileName) {
//            System.out.println(name);
//        }
        //String filePath = root + File.separator + "src\\main\\resources\\logs\\" + num + File.separator + fileName;
        //service.read2fileNameList(listFileName, baseFilePath, response);
        service.read2fileName(fileName, baseFilePath, response);
    }
}
