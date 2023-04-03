package org.sq.zbnss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.service.impl.StatisticsServiceImpl;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import java.util.Date;

@Api(value = "业务驾驶舱", tags = "业务驾驶舱")
@RestController
@RequestMapping("/api/Statistics")
@AllArgsConstructor
public class ServiceStatisticsController {
    @Resource
    private StatisticsServiceImpl service;

    @ApiOperation(value = "获取统计信息",tags = "业务驾驶舱")
    @GetMapping("/list")
    public ResponseVo queryStatisticsInfo() {
        return ResultUtil.success("统计信息获取成功", service.getStatisticsInfo());
    }

    @ApiOperation(value = "获取分布统计信息 type可选值system、record、appraisal",tags = "业务驾驶舱")
    @GetMapping("/distributeInfo")
    @ResponseBody
    public ResponseVo queryDistributeInfoInfo(@RequestParam(value = "type", required = true,defaultValue = "system") String type,
                                              @RequestParam(value = "startTime", required = false) Date startTime,
                                              @RequestParam(value = "endTime", required = false) Date endTime) {
        System.out.println(type);
        return ResultUtil.success("统计信息获取成功", service.getDistributeInfo(type,startTime,endTime));
    }
}
