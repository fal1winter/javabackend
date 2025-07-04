package com.liang.bbs.rest.controller;

import com.liang.bbs.article.facade.dto.ScolarDTO;
import com.liang.bbs.article.facade.server.ScolarService;
import com.liang.bbs.rest.config.login.NoNeedLogin;
import com.liang.bbs.rest.config.swagger.ApiVersion;
import com.liang.bbs.rest.config.swagger.ApiVersionConstant;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.web.basic.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bbs/scholar/")
@Api(tags = "学者管理接口")
public class ScolarController {
    @Reference
    private ScolarService scolarService;

    @NoNeedLogin
    @GetMapping("get/{id}")
    @ApiOperation(value = "获取学者详情")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<ScolarDTO> get(@PathVariable Integer id) {
        return ResponseResult.success(scolarService.getById(id));
    }

    @NoNeedLogin
    @GetMapping("getran")
    @ApiOperation(value = "获取学者列表")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<ScolarDTO>> getran(Integer numran) {
        return ResponseResult.success(scolarService.getRandom(numran));
    }

    @PostMapping("create")
    @ApiOperation(value = "创建学者")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Integer> create(@RequestBody ScolarDTO dto) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(scolarService.create(dto));
    }

    @PostMapping("update")
    @ApiOperation(value = "更新学者")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> update(@RequestBody ScolarDTO dto) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(scolarService.update(dto));
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "删除学者")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        return ResponseResult.success(scolarService.delete(id));
    }
}