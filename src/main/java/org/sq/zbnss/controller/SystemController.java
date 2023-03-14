package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.RecordSystem;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.SystemService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 系统信息(TbSystem)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:27
 */

@Api(value = "备案管理", tags = "备案管理")
@RestController
@RequestMapping("/api/tbSystem")
@AllArgsConstructor
public class SystemController {
    /**
     * 服务对象
     */
    @Resource
    private SystemService tbSystemService;

    /**
     * 分页查询
     *
     * @param recordSystem 筛选条件
     * @return 查询结果
     */
    @ApiOperationSupport(includeParameters = {"company.id","name","level","onlineDate","testStatus","pageNumber","pageSize"})
    @ApiOperation(value = "获取备案列表", tags = "备案管理")
    @GetMapping("/list")
    @ResponseBody
    public PageResultVo queryByPage(@RequestBody(required = false) RecordSystem recordSystem,
                                    @RequestParam( value = "pageNumber" ,required = true) Integer pageNumber,
                                    @RequestParam(value = "pageSize" ,required = true)Integer pageSize) {
        IPage<RecordSystem> userPage = tbSystemService.queryByPage(recordSystem,pageNumber,pageSize);
        return ResultUtil.table(userPage.getRecords(), userPage.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取备案信息", tags = "备案管理")
    @GetMapping("{id}")
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("备案查询成功",this.tbSystemService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param recordSystem 实体
     * @return 新增结果
     */
    @ApiOperation(value = "新增备案信息", tags = "备案管理")
    @ApiOperationSupport(includeParameters = {"company.id","name","level","onlineDate","testStatus"})
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(RecordSystem recordSystem) {
        ArrayList<RecordSystem> recordSystems = this.tbSystemService.queryByPage(recordSystem);
        if(recordSystems.size() > 0){
            return  ResultUtil.error("备案系统已存在，请勿重复备案");
        }
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        int num = this.tbSystemService.insert(recordSystem);
        if (num > 0) {
            return ResultUtil.success("备案信息添加成功");
        } else {
            return ResultUtil.error("备案信息添加失败");
        }
    }

    /**
     * 编辑数据
     *
     * @param recordSystem 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改备案信息", tags = "备案管理")
    @ApiOperationSupport(includeParameters = {"company.id","name","level","onlineDate","testStatus"})
    @PutMapping("/update")
    public ResponseVo edit(RecordSystem recordSystem) {
        RecordSystem recordSystem1 = this.tbSystemService.update(recordSystem);
        if(recordSystem1 == null){
            return ResultUtil.error("备案信息修改失败");
        }
        return ResultUtil.success("备案信息修改成功",recordSystem1);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseVo deleteById(Integer id) {
        boolean result = this.tbSystemService.deleteById(id);
        if (result) {
            return ResultUtil.success("删除备案信息成功");
        } else {
            return ResultUtil.error("删除备案信息失败");
        }
    }

}

