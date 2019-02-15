package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiStatisticsForLog {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String transactionTime;
    private String apikey;
    private String username;
    private String status;
    private String code;
    private String logfileName;
}
