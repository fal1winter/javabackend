package com.liang.bbs.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.liang.bbs.article.facade.dto.PaperDTO;
import com.liang.bbs.article.facade.server.PaperService;
import com.liang.bbs.article.persistence.entity.PaperPo;
import com.liang.bbs.article.persistence.mapper.PaperPoMapper;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.web.exception.BusinessException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperPoMapper paperPoMapper;

    @Override
    public PaperDTO getById(Integer id) {
        PaperPo paperPo = paperPoMapper.selectByPrimaryKey(id);
        if (paperPo == null) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "论文不存在");
        }
        return convertToDTO(paperPo);
    }

    @Override
    public List<PaperDTO> getList() {
        return paperPoMapper.selectAll();
    }

    @Override
    public Boolean create(PaperDTO paperDTO, UserSsoDTO currentUser) {
        PaperPo paperPo = convertToPo(paperDTO);
        paperPo.setCreateTime(LocalDateTime.now());
        paperPo.setUpdateTime(LocalDateTime.now());
        if (paperPoMapper.insertSelective(paperPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "创建论文失败");
        }
        return true;
    }

    @Override
    public Boolean update(PaperDTO paperDTO, UserSsoDTO currentUser) {
        PaperPo paperPo = convertToPo(paperDTO);
        paperPo.setUpdateTime(LocalDateTime.now());
        if (paperPoMapper.updateByPrimaryKeySelective(paperPo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新论文失败");
        }
        return true;
    }

    @Override
    public Boolean delete(Integer id, UserSsoDTO currentUser) {
        if (paperPoMapper.deleteByPrimaryKey(id) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "删除论文失败");
        }
        return true;
    }

    private PaperDTO convertToDTO(PaperPo po) {
        if (po == null) {
            return null;
        }
        
        PaperDTO dto = new PaperDTO();
        dto.setId(po.getId());
        dto.setTitle(po.getTitle());
        dto.setInstitution(po.getInstitution());
        dto.setConference(po.getConference());
        dto.setCitations(po.getCitations());
        dto.setFavorites(po.getFavorites());
        dto.setAbstractText(po.getAbstracttext());
        dto.setPaperUrl(po.getPaperUrl());
        dto.setTotalScore(po.getTotalScore());
        dto.setRatingCount(po.getRatingCount());
        dto.setCreateTime(po.getCreateTime());
        dto.setUpdateTime(po.getUpdateTime());
        
        dto.setAuthors(JSON.parseArray(po.getAuthors(), String.class));
        dto.setKeywords(JSON.parseArray(po.getKeywords(), String.class));
        
        return dto;
    }

    private PaperPo convertToPo(PaperDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PaperPo po = new PaperPo();
        po.setId(dto.getId());
        po.setTitle(dto.getTitle());
        po.setInstitution(dto.getInstitution());
        po.setConference(dto.getConference());
        po.setCitations(dto.getCitations());
        po.setFavorites(dto.getFavorites());
        po.setAbstracttext(dto.getAbstractText());
        po.setPaperUrl(dto.getPaperUrl());
        po.setTotalScore(dto.getTotalScore());
        po.setRatingCount(dto.getRatingCount());
        po.setCreateTime(dto.getCreateTime());
        po.setUpdateTime(dto.getUpdateTime());
        
        po.setAuthors(JSON.toJSONString(dto.getAuthors()));
        po.setKeywords(JSON.toJSONString(dto.getKeywords()));
        
        return po;
    }
}