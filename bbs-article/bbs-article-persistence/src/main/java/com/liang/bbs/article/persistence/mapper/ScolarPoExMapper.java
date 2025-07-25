package com.liang.bbs.article.persistence.mapper;

import com.liang.bbs.article.facade.dto.ScolarDTO;
import com.liang.bbs.article.persistence.entity.ScolarPo;
import com.liang.bbs.article.persistence.entity.ScolarPoExample;
import com.liang.bbs.article.persistence.entity.ScolarPoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ScolarPoExMapper {

    @Select("SELECT * FROM bs_scholar ORDER BY RAND() LIMIT #{count}")
    List<ScolarPoWithBLOBs> selectRandom(@Param("count") Integer count);
    List<ScolarDTO> searchfuzz(@Param("name") String name, 
                          @Param("institution") String institution,
                          @Param("bio") String bio);
}