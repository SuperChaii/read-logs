package com.example.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiStatisticsForLogExport {
    @Excel(name = "apikey")
    private String apikey;
    @Excel(name = "客户名")
    private String username;
    @Excel(name = "status")
    private String status;
    @Excel(name = "code")
    private String code;
    @Excel(name = "交易时间")
    private String transactionTime;
    @Excel(name = "文件名")
    private String logfileName;
}
