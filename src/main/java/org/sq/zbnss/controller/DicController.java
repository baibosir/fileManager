package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Dic;
import org.sq.zbnss.service.DicService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典(TbDic)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
@Api(value= "字典管理",tags = "字典管理")
@RestController
@RequestMapping("/api/Dic")
public class DicController {
    /**
     * 服务对象
     */
    @Resource
    private DicService tbDicService;

    /**
     * 分页查询
     *
     * @param dic 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取字典列表",tags = "字典管理")
    @GetMapping("/list")
    public PageResultVo queryByPage(Dic dic, int pageNum, int pageSize) {
        IPage<Dic> pageData = this.tbDicService.queryByPage(dic, pageNum,pageSize);
        return ResultUtil.table(pageData.getRecords(),pageData.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取字典列表",tags = "字典管理")
    @GetMapping("{id}")
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("字典数据信息获取成功",this.tbDicService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param dic 实体
     * @return 新增结果
     */
    @ApiOperation(value = "添加字典信息",tags = "字典管理")
    @PostMapping("/add")
    public ResponseVo add(Dic dic) {
        if(dic.getKey() == null || "".equals(dic.getKey()) || dic.getValue() == null || "".equals(dic.getValue())){
            return ResultUtil.error("参数错误");
        }
        List<Dic> oldResult =  this.tbDicService.queryByPage(dic,1,10).getRecords();
        if(oldResult.size() == 0){
            return ResultUtil.error("字典信息已存在");
        }
        Dic newDic =this.tbDicService.insert(dic);
        if(newDic == null){
            return ResultUtil.error("字典信息添加失败");
        }
        return ResultUtil.success("字典信息添加成功", newDic);
    }

    /**
     * 编辑数据
     *
     * @param dic 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改字典信息",tags = "字典管理")
    @PutMapping("/update")
    public ResponseVo edit(Dic dic) {
        if(dic.getId() == 0){
            return ResultUtil.error("参数错误");
        }
        Dic oldDic = this.tbDicService.queryById(dic.getId());
        if(oldDic == null){
            return ResultUtil.error("字典信息不存在");
        }
        Dic newDic = this.tbDicService.update(dic);
        if(newDic == null){
            return ResultUtil.error("字典信息修改失败");
        }
        return ResultUtil.success("字典信息修改成功", newDic);
    }

}

