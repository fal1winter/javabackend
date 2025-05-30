package com.liang.bbs.article.facade.dto;

import lombok.Data;

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
    private Integer rateId;
    private Integer rating;
    private String comment;
    private Integer parentId;
    
    
}