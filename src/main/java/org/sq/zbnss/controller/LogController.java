package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Log;
import org.sq.zbnss.service.LogService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;

/**
 * 操作日志(TbLog)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:22
 */
@Api(value = "日志管理" ,tags = "日志管理")
@RestController
@RequestMapping("/api/log")
public class LogController {
    /**
     * 服务对象
     */
    @Resource
    private LogService tbLogService;

    /**
     * 分页查询
     *
     * @param log 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询日志",tags = "日志管理")
    @GetMapping("/list")
    public PageResultVo queryByPage(Log log, Integer pageNum, Integer pageSize) {
        IPage<Log>  pageLog = this.tbLogService.queryByPage(log, pageNum,pageSize);
        return ResultUtil.table(pageLog.getRecords(), pageLog.getTotal());
    }


    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation(value = "删除日志",tags = "日志管理")
    @DeleteMapping("/delete")
    public ResponseVo deleteById(Integer id) {
        boolean  result = this.tbLogService.deleteById(id);
        if(result){
            return ResultUtil.success("日志删除成功");
        }else{
            return ResultUtil.success("日志删除失败");
        }
    }

}

