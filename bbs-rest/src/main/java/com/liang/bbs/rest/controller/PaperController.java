package com.liang.bbs.rest.controller;

import com.liang.bbs.article.facade.dto.PaperDTO;
import com.liang.bbs.article.facade.server.PaperService;
import com.liang.bbs.rest.config.login.NoNeedLogin;
import com.liang.bbs.rest.config.swagger.ApiVersion;
import com.liang.bbs.rest.config.swagger.ApiVersionConstant;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.web.basic.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bbs/paper/")
@Api(tags = "论文接口")
public class PaperController {
    @Reference
    private PaperService paperService;
    @NoNeedLogin
    @GetMapping("get/{id}")
    @ApiOperation(value = "获取论文详情")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PaperDTO> get(@PathVariable Integer id) {
        return ResponseResult.success(paperService.getById(id));
    }
    @NoNeedLogin
    @PostMapping("create")
    @ApiOperation(value = "创建论文")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> create(@RequestBody PaperDTO paperDTO) {
        return ResponseResult.success(paperService.create(paperDTO, UserContextUtils.currentUser()));
    }
    @NoNeedLogin
    @PostMapping("addrate")
    @ApiOperation(value = "增加评分")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> addrate(@RequestParam Integer score, @RequestParam Integer id) {
        return ResponseResult.success(paperService.addrate(score, id, UserContextUtils.currentUser()));
    }

    @PostMapping("update")
    @ApiOperation(value = "更新论文")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> update(@RequestBody PaperDTO paperDTO) {
        return ResponseResult.success(paperService.update(paperDTO, UserContextUtils.currentUser()));
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "删除论文")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        return ResponseResult.success(paperService.delete(id, UserContextUtils.currentUser()));
    }

    @NoNeedLogin
    @GetMapping("getList")
    @ApiOperation(value = "获取论文列表")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<PaperDTO>> getList() {
        return ResponseResult.success(paperService.getList());
    }
    @NoNeedLogin
    @GetMapping("search")
    @ApiOperation(value = "论文模糊搜索")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<List<PaperDTO>> searchPapers(@RequestParam String keyword) {
        return ResponseResult.success(paperService.searchPapers(keyword));
    }
}