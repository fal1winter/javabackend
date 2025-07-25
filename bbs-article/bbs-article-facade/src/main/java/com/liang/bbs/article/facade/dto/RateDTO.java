package com.liang.bbs.article.facade.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class RateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    private LocalDateTime updateTime;
    private Integer userId;
    private Integer id;
    private Integer target;
    @Getter
    private Integer rateId;
    private Integer rating;
    private String comment;
    private Integer parentId;
    // 新增嵌套关系字段
    private List<RateDTO> childRates;
    private Integer depth; // 嵌套深度
    private Integer repliesCount; // 子评分数量
    
}