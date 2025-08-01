package com.liang.bbs.article.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScolarPo implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.avatar_url
     *
     * @mbg.generated
     */
    private String avatarUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.institution
     *
     * @mbg.generated
     */
    private String institution;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.create_time
     *
     * @mbg.generated
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.update_time
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.totalrate
     *
     * @mbg.generated
     */
    private String totalrate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bs_scholar.ratenum
     *
     * @mbg.generated
     */
    private Integer ratenum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;
    private List<HashMap<String,Integer>> authorpos;
}