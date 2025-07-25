package com.liang.bbs.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.liang.bbs.article.facade.dto.RateDTO;
import com.liang.bbs.article.facade.server.RateService;
import com.liang.bbs.article.persistence.entity.RatePo;
import com.liang.bbs.article.persistence.mapper.RatePoMapper;
import com.liang.bbs.article.service.mapstruct.RateMS;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.web.exception.BusinessException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.liang.bbs.article.persistence.entity.RatePoExample;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RatePoMapper ratePoMapper;

    @Override
    public RateDTO getById(Integer id) {
        RatePo ratePo = ratePoMapper.selectByPrimaryKey(id);
        if (ratePo == null) {
            throw BusinessException.build(ResponseCode.DATA_ILLEGAL, "评分不存在");
        }
        return RateMS.INSTANCE.toDTO(ratePo);
    }

    @Override
    public List<RateDTO> getByIdandTar(Integer id, String target) {
        RatePoExample example = new RatePoExample();
        example.createCriteria().andRateIdEqualTo(id).andTargetEqualTo(target);
        return ratePoMapper.selectByExampleWithBLOBs(example).stream()
            .map(RateMS.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }


    @Override
    public Boolean create(RateDTO rateDTO, UserSsoDTO currentUser) {
        RatePo ratePo = RateMS.INSTANCE.toPo(rateDTO);
        ratePo.setUserId(Math.toIntExact(currentUser.getUserId())); // 从currentUser获取用户ID
        ratePo.setCreateTime(LocalDateTime.now());
        ratePo.setUpdateTime(LocalDateTime.now());
        if (ratePoMapper.insertSelective(ratePo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "创建评分失败");
        }
        return true;
    }

    @Override
    public Boolean update(RateDTO rateDTO, UserSsoDTO currentUser) {
        RatePo ratePo = RateMS.INSTANCE.toPo(rateDTO);
        ratePo.setUserId(Math.toIntExact(currentUser.getUserId())); // 从currentUser获取用户ID
        ratePo.setUpdateTime(LocalDateTime.now());
        if (ratePoMapper.updateByPrimaryKeySelective(ratePo) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "更新评分失败");
        }
        return true;
    }

    @Override
    public Boolean delete(Integer id, UserSsoDTO currentUser) {
        if (ratePoMapper.deleteByPrimaryKey(id) <= 0) {
            throw BusinessException.build(ResponseCode.OPERATE_FAIL, "删除评分失败");
        }
        return true;
    }

    @Override
    public List<RateDTO> getByRateIdAndTarget(Integer rateId, String target) {
        // 获取父级评分
        RatePoExample parentExample = new RatePoExample();
        parentExample.createCriteria()
            .andRateIdEqualTo(rateId)
            .andTargetEqualTo(target)
            .andParentIdIsNull(); // 添加父级条件
        
        List<RateDTO> parentRates = ratePoMapper.selectByExampleWithBLOBs(parentExample).stream()
            .map(RateMS.INSTANCE::toDTO)
            .peek(dto -> {
                // 递归获取子评分
                dto.setChildRates(getChildRates(dto.getId(), target));
                dto.setRepliesCount(dto.getChildRates().size());
            })
            .collect(Collectors.toList());
        
        return parentRates;
    }
    
    private List<RateDTO> getChildRates(Integer parentId, String target) {
        RatePoExample childExample = new RatePoExample();
        childExample.createCriteria()
            .andParentIdEqualTo(parentId)
            .andTargetEqualTo(target);
        
        return ratePoMapper.selectByExampleWithBLOBs(childExample).stream()
            .map(RateMS.INSTANCE::toDTO)
            .peek(dto -> {
                dto.setDepth(2); // 设置嵌套深度
                dto.setChildRates(getChildRates(dto.getId(), target));
            })
            .collect(Collectors.toList());
    }
}