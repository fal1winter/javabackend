package com.liang.bbs.article.facade.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Data
public class ScolarDTO implements Serializable{
    private Integer id;
    private String name;
    private String avatarUrl;
    private String institution;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String totalrate;
    private Integer ratenum;
    // 如果需要BLOB字段可以添加以下字段
    private String bio;
    private String collaboratorIds;
    private List<HashMap<String,Integer>> authorpos;
}