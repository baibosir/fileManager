package org.sq.zbnss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Check;
import org.sq.zbnss.entity.RecordSystem;
import org.sq.zbnss.service.CheckService;
import org.sq.zbnss.service.CompanyService;
import org.sq.zbnss.service.SystemService;
import org.sq.zbnss.uitl.ResultUtil;

import java.text.ParseException;
import java.util.ArrayList;

@RequestMapping("/api/workSpase")
@AllArgsConstructor
@RestController
@Api(value = "工作台", tags = "工作台")
public class WorkSpaseController {

    private CheckService checkService;
    private SystemService systemService;
    private CompanyService tbCompanyService;

    @ApiOperation(value = "获取近一个月的检查信息", tags = "工作台")
    @GetMapping("/checks")
    public ResponseVo getMonthCheckData(){
        try {
            ArrayList<Check> checks = checkService.queryByPage();
            return ResultUtil.success("当前月检查信息获取成功", checks);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResultUtil.error("当前月检查信息获取失败");
    }

    @ApiOperation(value = "获取近一个月的备案信息", tags = "工作台")
    @GetMapping("/systemes")
    public ResponseVo getMonthSystemData(){
        ArrayList<RecordSystem> syses = systemService.getMonthDate();
        if(syses.size() > 0){
            return ResultUtil.success("当前月备案信息获取成功", syses);
        }
        return ResultUtil.error("暂无本月检查数据");
    }

    @ApiOperation(value = "获取单位负责人信息", tags = "工作台")
    @GetMapping("/company")
    public ResponseVo getCompanInfo(){
        return ResultUtil.success("单位列表获取成功", tbCompanyService.queryAllByLimit(null));
    }

}
