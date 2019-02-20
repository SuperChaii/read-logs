package com.example.service;

import com.example.model.dto.LogResult;
import com.example.model.entity.ApiStatisticsForLog;
import com.example.model.export.ApiStatisticsForLogExport;
import com.example.model.export.LogResultExport;
import com.example.util.ExportUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Integer read2fileName4Statis(String fileName, ArrayList<String> fileNameList, String baseFilePath, String apikey, String username) {
        ArrayList<String> readList;
        ApiStatisticsForLog apiLog;
        ArrayList<ApiStatisticsForLog> extractList = new ArrayList<>();
        String status;

        String filePath;
        for (String singleFile : fileNameList) {
            readList = new ArrayList<>();
            filePath = baseFilePath.concat("\\").concat(singleFile);
            try {
                FileReader fr = new FileReader(filePath);
                BufferedReader bf = new BufferedReader(fr);
                String str;
                // 按行读取字符串
                while ((str = bf.readLine()) != null) {
                    readList.add(str);
                }
                bf.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String str : readList) {
                apiLog = new ApiStatisticsForLog();
                apiLog.setLogfileName(singleFile);
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
                    extractList.add(apiLog);
                }
            }
        }
        System.out.println(fileNameList.toString() + "有效数据为" + extractList.size() + "条");
        List<ApiStatisticsForLog> resultList = extractList.stream().
            filter(a -> a.getApikey().equals(apikey)).
            filter(a -> a.getUsername().equals(username)).
            filter(a -> a.getStatus().equals("1")).
            filter(a -> a.getCode().equals("00")).
            collect(Collectors.toList());
        return resultList.size();
    }


    public void  read2fileList4Result(ArrayList<String> fileNameList, String baseFilePath, HttpServletResponse response) {
        ArrayList<String> readList;
        ApiStatisticsForLog apiLog;
        ArrayList<ApiStatisticsForLog> extractList = new ArrayList<>();
        String status;

        String filePath;
        for (String singleFile : fileNameList) {
            readList = new ArrayList<>();
            filePath = baseFilePath.concat("\\").concat(singleFile);
            try {
                FileReader fr = new FileReader(filePath);
                BufferedReader bf = new BufferedReader(fr);
                String str;
                // 按行读取字符串
                while ((str = bf.readLine()) != null) {
                    readList.add(str);
                }
                bf.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String str : readList) {
                apiLog = new ApiStatisticsForLog();
                apiLog.setLogfileName(singleFile);
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
                    extractList.add(apiLog);
                }
            }
        }
        System.out.println(fileNameList.toString() + "有效数据为" + extractList.size() + "条");
        Map<String, Map<String, List<ApiStatisticsForLog>>> map = extractList.stream().
            filter(a -> a.getStatus().equals("1")).
            filter(a -> a.getCode().equals("00")).
            collect(Collectors.groupingBy(ApiStatisticsForLog::getApikey, Collectors.groupingBy(ApiStatisticsForLog::getUsername)));

        List<LogResultExport> exportList = new ArrayList<>();
        map.forEach((x, y) -> {
            y.forEach((x2, y2) -> {
                LogResultExport result = new LogResultExport();
                result.setApikey(x);
                result.setUsername(x2);
                result.setValidNum(y2.size());
                exportList.add(result);
            });
        });
        exportList.sort((x,y)-> x.getApikey().compareTo(y.getApikey()));
        try {
            ExportUtil.exportExcel(exportList, "API日志分析", baseFilePath, LogResultExport.class, "API日志分析.xls", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
