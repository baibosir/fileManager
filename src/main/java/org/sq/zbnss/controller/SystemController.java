package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Company;
import org.sq.zbnss.entity.Log;
import org.sq.zbnss.entity.RecordSystem;
import org.sq.zbnss.service.CompanyService;
import org.sq.zbnss.service.LogService;
import org.sq.zbnss.service.SystemService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * 系统信息(TbSystem)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:27
 */

@Api(value = "备案管理", tags = "备案管理")
@RestController
@RequestMapping("/api/System")
@AllArgsConstructor
public class SystemController {
    /**
     * 服务对象
     */
    @Resource
    private SystemService tbSystemService;

    @Resource
    private LogService logService;

    @Resource
    private CompanyService companyService;

    @Resource
    private Common common;

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
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(@RequestBody RecordSystem recordSystem, HttpServletRequest request) {
        System.out.println(recordSystem);
        if(recordSystem.getCompany() == null || recordSystem.getCompany().getId() <= 0){
            return  ResultUtil.error("请给出正确的单位信息");
        }
        if(recordSystem.getName() == null || "".equals(recordSystem.getName()) ||
                recordSystem.getLevel() == null || "".equals(recordSystem.getLevel())||recordSystem.getOnlineDate() == null ){
            return  ResultUtil.error("请求参数错误");
        }
        ArrayList<RecordSystem> recordSystems = this.tbSystemService.queryByPage(recordSystem);
        if(recordSystems.size() > 0){
            return  ResultUtil.error("备案系统已存在，请勿重复备案");
        }
        Company com = companyService.queryById(recordSystem.getCompany().getId());
        if(com == null){
            return  ResultUtil.error("单位信息不存在");
        }
        int num = this.tbSystemService.insert(recordSystem);
        if (num > 0) {
            Log log = new Log();
            log.setUserId(common.getLoginUser(request));
            log.setOperate(MessageFormat.format("新增备案信息：{0}",recordSystem.toString()));
            logService.insert(log);
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
    @PutMapping("/update")
    public ResponseVo edit(@RequestBody RecordSystem recordSystem, HttpServletRequest request) {
        RecordSystem old_sys = this.tbSystemService.queryById(recordSystem.getId());
        if(old_sys == null){
            return ResultUtil.error("备案信息不存在");
        }
        RecordSystem recordSystem1 = this.tbSystemService.update(recordSystem);
        if(recordSystem1 == null){
            return ResultUtil.error("备案信息修改失败");
        }
        Log log = new Log();
        log.setUserId(common.getLoginUser(request));
        log.setOperate(MessageFormat.format("新增备案信息：{0}",recordSystem1.toString()));
        logService.insert(log);
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

