package com.liang.bbs.article.persistence.mapper;

import com.liang.bbs.article.persistence.entity.ScolarPo;
import com.liang.bbs.article.persistence.entity.ScolarPoExample;
import com.liang.bbs.article.persistence.entity.ScolarPoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ScolarPoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    long countByExample(ScolarPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int deleteByExample(ScolarPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int insert(ScolarPoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int insertSelective(ScolarPoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    List<ScolarPoWithBLOBs> selectByExampleWithBLOBs(ScolarPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    List<ScolarPo> selectByExample(ScolarPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    ScolarPoWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ScolarPoWithBLOBs record, @Param("example") ScolarPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") ScolarPoWithBLOBs record, @Param("example") ScolarPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ScolarPo record, @Param("example") ScolarPoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ScolarPoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(ScolarPoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bs_scholar
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ScolarPo record);
    
    @Select("SELECT * FROM bs_scholar ORDER BY RAND() LIMIT #{count}")
    List<ScolarPoWithBLOBs> selectRandom(@Param("count") Integer count);
}