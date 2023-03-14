package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Appraisal;
import org.sq.zbnss.service.AppraisalService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 测评信息(TbAppraisal)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:02
 */
@RestController
@RequestMapping("/api/Appraisal")
@Api(value = "测评管理", tags = "测评管理")
public class AppraisalController {
    /**
     * 服务对象
     */
    @Resource
    private AppraisalService tbAppraisalService;

    /**
     * 分页查询
     *
     * @param appraisal 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页获取测评列表", tags = "测评管理")
    @ApiOperationSupport(includeParameters = {"systemId.id","status","insertTime","num"})
    @ResponseBody
    public PageResultVo queryByPage( Appraisal appraisal,
                                    @RequestParam( value = "pageNumber" ,required = true) Integer pageNumber,
                                    @RequestParam(value = "pageSize" ,required = true)Integer pageSize) {
        IPage<Appraisal> userPage = tbAppraisalService.queryByPage(appraisal,pageNumber,pageSize);
        return ResultUtil.table(userPage.getRecords(), userPage.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据id获取测评数据", tags = "测评管理")
    @ResponseBody
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("测评信息获取成功",this.tbAppraisalService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param appraisal 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增测评数据", tags = "测评管理")
    @ResponseBody
    public ResponseVo add(Appraisal appraisal) {
        if(appraisal.getSystemId() == 0 ){
            return ResultUtil.error("新增测评数据时必须关联备案系统");
        }
        ArrayList<Appraisal>  data = this.tbAppraisalService.queryByPage(appraisal);
        if(data.size() > 0){
            return ResultUtil.error("测评数据已存在，请价差数据");
        }
        Appraisal insertData = this.tbAppraisalService.insert(appraisal);
        if(insertData == null){
            return ResultUtil.error("测评数据添加失败");
        }else{
            return ResultUtil.error("测评数据添加成功",insertData);
        }
    }

    /**
     * 编辑数据
     *
     * @param appraisal 实体
     * @return 编辑结果
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改测评数据", tags = "测评管理")
    @ResponseBody
    public ResponseVo edit(Appraisal appraisal) {
        if(appraisal.getId() == 0 || null == appraisal.getAppraisal() || "".equals(appraisal.getAppraisal())){
            return ResultUtil.error("请确认请求参数id和appraisal是否给出");
        }
        ArrayList<Appraisal>  data = this.tbAppraisalService.queryByPage(appraisal);
        if(data.size() == 0){
            return ResultUtil.error("测评数据不存在，请添先添加数据");
        }
        Appraisal updateDate = this.tbAppraisalService.update(appraisal);
        if(updateDate == null){
            return ResultUtil.error("测评数据修改失败");
        }else{
            return ResultUtil.error("测评数据修改成功",updateDate);
        }
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除测评数据", tags = "测评管理")
    @ResponseBody
    public ResponseVo deleteById(Integer id) {
        boolean result = this.tbAppraisalService.deleteById(id);
        if(result){
            return ResultUtil.success("测评数据删除成功");
        }else {
            return ResultUtil.error("测评数据删除失败");
        }
    }

}

