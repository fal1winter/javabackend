package com.liang.bbs.article.facade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author maliangnansheng
 * @date 2022/4/6 14:30
 */
@Data
public class ScolarSearchDTO implements Serializable {

    private String name;
    private String institution;
    private String bio;
    private static final long serialVersionUID = 1L;

}
