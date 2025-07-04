package com.liang.bbs.article.facade.server;

import com.liang.bbs.article.facade.dto.ScolarDTO;

import java.util.List;

public interface ScolarService {
    ScolarDTO getById(Integer id);
    
    Integer create(ScolarDTO scolarDTO);
    Boolean update(ScolarDTO scolarDTO);
    Boolean delete(Integer id);
    public List<ScolarDTO> getRandom(Integer count);
}