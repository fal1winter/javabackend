package com.liang.bbs.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.liang.bbs.article.facade.dto.ScolarDTO;
import com.liang.bbs.article.facade.server.ScolarService;
import com.liang.bbs.article.persistence.entity.ScolarPo;
import com.liang.bbs.article.persistence.entity.ScolarPoWithBLOBs;
import com.liang.bbs.article.persistence.mapper.ScolarPoMapper;
import com.liang.bbs.article.service.mapstruct.ScolarMS;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.web.exception.BusinessException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScolarServiceImpl implements ScolarService {
    @Autowired
    private ScolarPoMapper scolarPoMapper;

    @Override
    public ScolarDTO getById(Integer id) {
        ScolarPoWithBLOBs po = scolarPoMapper.selectByPrimaryKey(id);
        if (po == null) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "学者不存在");
        }
        return ScolarMS.INSTANCE.toDTO(po);
    }

 

    @Override
    public Integer create(ScolarDTO dto) {
        ScolarPoWithBLOBs po = ScolarMS.INSTANCE.toPo(dto);
        
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(LocalDateTime.now());
        if (scolarPoMapper.insertSelective(po) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "创建学者失败");
        }
        return po.getId();
    }

    @Override
    public Boolean update(ScolarDTO dto) {
        ScolarPoWithBLOBs po = ScolarMS.INSTANCE.toPo(dto);
        
        po.setUpdateTime(LocalDateTime.now());
        if (scolarPoMapper.updateByPrimaryKeySelective(po) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新学者失败");
        }
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        if (scolarPoMapper.deleteByPrimaryKey(id) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "删除学者失败");
        }
        return true;
    }
    
    @Override
    public List<ScolarDTO> getRandom(Integer count) {
        return scolarPoMapper.selectRandom(count).stream()
                .map(ScolarMS.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}