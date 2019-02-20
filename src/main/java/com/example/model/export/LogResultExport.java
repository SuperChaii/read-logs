package com.example.model.export;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogResultExport {
    @Excel(name="Apikey")
    private String apikey;
    @Excel(name="用户名")
    private String username;
    @Excel(name="有效调用数量")
    private Integer validNum;
}
