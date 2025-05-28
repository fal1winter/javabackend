package com.liang.bbs.article.facade.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class PaperDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    private LocalDateTime updateTime;
    private Integer id;
    private String title;
    private String institution;
    private String conference;
    private Integer citations;
    private Integer favorites;
    private String abstractText;
    private String paperUrl;
    private Integer totalScore;
    private Integer ratingCount;
   
    private List<String> authors;
    private List<String> keywords;
    
}