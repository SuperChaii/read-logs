package com.example.service;

import com.example.model.export.ApiStatisticsForLogExport;
import com.example.util.ExportUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class InvokeService {
    public void read2fileName(String fileName, String baseFilePath, HttpServletResponse response) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<ApiStatisticsForLogExport> exportList = new ArrayList<>();
        ApiStatisticsForLogExport apiLog;
        String status;

        String filePath;
        filePath = baseFilePath.concat("\\").concat(fileName);
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String str : arrayList) {
            apiLog = new ApiStatisticsForLogExport();
            apiLog.setLogfileName(fileName);
            if (str.contains("响应")) {
                apiLog.setTransactionTime(str.substring(0, 23));
                apiLog.setApikey(str.substring(str.indexOf("调用API[") + 6, str.indexOf("调用API[") + 12));
                apiLog.setUsername(str.substring(str.indexOf("客户[") + 3, str.indexOf("]调")));
                status = str.substring(str.indexOf("status") + 9, str.indexOf("\"", str.indexOf("status") + 9));
                if (!status.equals("1") && !status.equals("SUCCEED")) {
                    continue;
                } else {
                    apiLog.setStatus(status);
                }
                apiLog.setCode(str.substring(str.indexOf("code") + 7, str.indexOf("\"", str.indexOf("code") + 7)));
                exportList.add(apiLog);
            }
        }
        System.out.println(fileName + "有效数据为" + exportList.size() + "条");
        try {
            ExportUtil.exportExcel(exportList, "API日志分析", baseFilePath, ApiStatisticsForLogExport.class, "API日志分析.xls", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read2fileNameList(ArrayList<String> fileNameList, String baseFilePath, HttpServletResponse response) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<ApiStatisticsForLogExport> exportList = new ArrayList<>();
        ApiStatisticsForLogExport apiLog;
        String status;

        String filePath;
        for (String fileName : fileNameList) {
            filePath = baseFilePath.concat("\\").concat(fileName);
            try {
                FileReader fr = new FileReader(filePath);
                BufferedReader bf = new BufferedReader(fr);
                String str;
                // 按行读取字符串
                while ((str = bf.readLine()) != null) {
                    arrayList.add(str);
                }
                bf.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String str : arrayList) {
                apiLog = new ApiStatisticsForLogExport();
                apiLog.setLogfileName(fileName);
                if (str.contains("响应")) {
                    apiLog.setTransactionTime(str.substring(0, 23));
                    apiLog.setApikey(str.substring(str.indexOf("调用API[") + 6, str.indexOf("调用API[") + 12));
                    apiLog.setUsername(str.substring(str.indexOf("客户[") + 3, str.indexOf("]调")));
                    status = str.substring(str.indexOf("status") + 9, str.indexOf("\"", str.indexOf("status") + 9));
                    if (!status.equals("1") && !status.equals("SUCCEED")) {
                        continue;
                    } else {
                        apiLog.setStatus(status);
                    }
                    apiLog.setCode(str.substring(str.indexOf("code") + 7, str.indexOf("\"", str.indexOf("code") + 7)));
                    exportList.add(apiLog);
                }
            }
        }

        System.out.println(fileNameList.toString() + "有效数据为" + exportList.size() + "条");
        try {
            ExportUtil.exportExcel(exportList, "API日志分析", baseFilePath, ApiStatisticsForLogExport.class, "API日志分析.xls", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
