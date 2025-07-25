package com.liang.bbs.article.facade.server;

import com.github.pagehelper.PageInfo;
import com.liang.bbs.article.facade.dto.ScolarDTO;
import com.liang.bbs.article.facade.dto.ScolarSearchDTO;
import java.util.List;

import org.springframework.data.domain.PageRequest;

public interface ScolarService {
    ScolarDTO getById(Integer id);
    
    Integer create(ScolarDTO scolarDTO);
    Boolean update(ScolarDTO scolarDTO);
    Boolean delete(Integer id);
    public List<ScolarDTO> getRandom(Integer count);
    public Boolean addrate(Integer score, Integer id);
    public PageInfo<ScolarDTO> searchfuzz(ScolarSearchDTO searchDTO);
}