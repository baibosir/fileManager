package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Check;
import org.sq.zbnss.service.CheckService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;

/**
 * 检查结果记录(TbCheck)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:13
 */
@RestController
@Api(value = "检查管理", tags = "检查管理")
@RequestMapping("/api/check")
public class CheckController {
    /**
     * 服务对象
     */
    @Resource
    private CheckService tbCheckService;

    /**
     * 分页查询
     *
     * @param tbCheck 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取检查列表", tags = "检查管理")
    @GetMapping("/list")
    public PageResultVo queryByPage(Check tbCheck,
                                    @RequestParam( value = "pageNumber" ,required = true) Integer pageNumber,
                                    @RequestParam(value = "pageSize" ,required = true)Integer pageSize) {
        IPage<Check> checkPage = tbCheckService.queryByPage(tbCheck,pageNumber,pageSize);
        return ResultUtil.table(checkPage.getRecords(), checkPage.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据检查id获取检查信息", tags = "检查管理")
    @GetMapping("{id}")
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("检查信息获取成功",this.tbCheckService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param tbCheck 实体
     * @return 新增结果
     */
    @ApiOperation(value = "新增检查信息", tags = "检查管理")
    @PostMapping("/add")
    public ResponseVo add(Check tbCheck) {
        if(tbCheck == null || tbCheck.getCompanyId() == null || tbCheck.getPlanDate() == null){
            return ResultUtil.error("检查单位CompanyId()和计划检查时间（PlanDate）不能为空");
        }
            Check check = this.tbCheckService.insert(tbCheck);
        if(check == null){
            return ResultUtil.error("检查信息添加失败");
        }
        return ResultUtil.success("检查信息添加成功",check);
    }

    /**
     * 编辑数据
     *
     * @param tbCheck 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改检查信息", tags = "检查管理")
    @PutMapping("/update")
    public ResponseVo edit(Check tbCheck) {
        if(tbCheck == null || tbCheck.getId() == 0 || tbCheck.getCheckId() == null || "".equals(tbCheck.getCheckId())){
            return ResultUtil.error("请求参数错误");
        }
        Check check= this.tbCheckService.update(tbCheck);
        if(check == null){
            return ResultUtil.error("检查信息修改失败");
        }else{
            return ResultUtil.success("检查信息修改成功",check);
        }
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation(value = "删除检查信息", tags = "检查管理")
    @DeleteMapping("/delete")
    public ResponseVo deleteById(Integer id) {
        if(id == 0){
            return ResultUtil.error("请求参数错误");
        }
        boolean result = this.tbCheckService.deleteById(id);
        if(result){
            return ResultUtil.error("检查信息删除成功");
        }else{
            return ResultUtil.error("检查信息删除失败");
        }
    }

}

