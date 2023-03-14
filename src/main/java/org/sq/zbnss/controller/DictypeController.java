package org.sq.zbnss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Dictype;
import org.sq.zbnss.service.DictypeService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 字典类型(TbDictype)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
@Api(value = "字典类型管理",tags = "字典类型管理")
@RestController
@RequestMapping("/api/Dictype")
public class DictypeController {
    /**
     * 服务对象
     */
    @Resource
    private DictypeService dictypeService;

    /**
     * 分页查询
     *
     * @param dictype 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取所有字典",tags = "字典类型管理")
    @GetMapping("/list")
    @ResponseBody
    public ResponseVo queryByPage(Dictype dictype) {
        return ResultUtil.success("字典类型获取成功",this.dictypeService.queryByPage(dictype));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取所有字典",tags = "字典类型管理")
    @GetMapping("{id}")
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("字典类型信息获取成功",this.dictypeService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param dictype 实体
     * @return 新增结果
     */
    @ApiOperation(value = "添加字典类型信息",tags = "字典类型管理")
    @PostMapping("/add")
    public ResponseVo add(Dictype dictype) {
        if(dictype.getKey() == null || "".equals(dictype.getKey().trim())){
            return ResultUtil.error("参数错误");
        }
        ArrayList<Dictype> dictypes = this.dictypeService.queryByPage(dictype);
        if(dictypes.size() > 0){
            return ResultUtil.error("字典类型已存在");
        }
        Dictype result = this.dictypeService.insert(dictype);
        if(result == null){
            return ResultUtil.error("字典类型添加失败");
        }
        return ResultUtil.success("字典类型添加成功",result );
    }

    /**
     * 编辑数据
     *
     * @param dictype 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改字典类型信息",tags = "字典类型管理")
    @PutMapping
    public ResponseVo edit(Dictype dictype) {
        if(dictype.getId() == 0 || dictype.getTypeId() == null ){
            return ResultUtil.error("参数错误");
        }
        Dictype  oldData = this.dictypeService.queryById(dictype.getId());
        if(oldData == null){
            return ResultUtil.error("字典类型信息不存在");
        }
        Dictype  newData = this.dictypeService.update(dictype);
        if(newData == null){
            return ResultUtil.error("字典类型信息修改失败");
        }
        return ResultUtil.success("字典类型信息修改成功", newData);
    }

}

