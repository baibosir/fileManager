package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Machineroom;
import org.sq.zbnss.service.MachineroomService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 机房(TbMachineroom)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:23
 */
@RestController
@Api(value = "机房管理", tags = "机房管理")
@RequestMapping("/api/Machineroom")
public class MachineroomController {
    /**
     * 服务对象
     */
    @Resource
    private MachineroomService tbMachineroomService;

    /**
     * 分页查询
     *
     * @param machineroom 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页获取机房列表", tags = "机房管理")
    @ApiOperationSupport(includeParameters = {"companyId.id","address","principal.id","regTel","regDate","detail","use","registTime"})
    @ResponseBody
    public PageResultVo queryByPage(Machineroom machineroom,
                                    @RequestParam( value = "pageNumber" ,required = true) Integer pageNumber,
                                    @RequestParam(value = "pageSize" ,required = true)Integer pageSize) {
        IPage<Machineroom> mrPage = tbMachineroomService.queryByPage(machineroom,pageNumber,pageSize);
        return ResultUtil.table(mrPage.getRecords(), mrPage.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取机房", tags = "机房管理")
    @ResponseBody
    @GetMapping("{id}")
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("机房信息获取成功",this.tbMachineroomService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param machineroom 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "录入机房列表", tags = "机房管理")
    @ApiOperationSupport(includeParameters = {"companyId.id","address","principal.id","regTel","regDate","detail","use","registTime"})
    @ResponseBody
    public ResponseVo add(@RequestBody()Machineroom machineroom) {
        if(machineroom.getCompanyId() == null || machineroom.getPrincipal() == null){
            return ResultUtil.error("请关联单位信息");
        }
        if(machineroom.getAddress() == null || "".equals(machineroom.getAddress())){
            return ResultUtil.error("机房信息必须包含机房地址信息");
        }
        ArrayList<Machineroom> existList = this.tbMachineroomService.queryByPage(machineroom);
        if(existList.size() > 0){
            return ResultUtil.error("机房信息已存在，请检查信息");
        }
        Machineroom machineroom1 = this.tbMachineroomService.insert(machineroom);
        if(machineroom1 == null){
            return ResultUtil.error("机房信息添加失败");
        }
        return ResultUtil.success("机房信息添加成功",machineroom1);
    }

    /**
     * 编辑数据
     *
     * @param machineroom 实体
     * @return 编辑结果
     */
    @PutMapping
    @ApiOperation(value = "修改机房列表", tags = "机房管理")
    @ApiOperationSupport(includeParameters = {"id,machineId,companyId.id","address","principal.id","regTel","regDate","detail","use","registTime"})
    @ResponseBody
    public ResponseVo edit(@RequestBody()Machineroom machineroom) {
        if(machineroom.getId() == 0 ){
            return ResultUtil.error("机房信息修改失败");
        }
        return ResultUtil.success("机房信息修改成功",this.tbMachineroomService.update(machineroom));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseVo deleteById(Integer id) {
        boolean result = this.tbMachineroomService.deleteById(id);
        if(result){
            return ResultUtil.success("机房信息删除成功");
        }else{
            return ResultUtil.success("机房信息删除失败");
        }
    }

}

