package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Company;
import org.sq.zbnss.entity.Train;
import org.sq.zbnss.service.CompanyService;
import org.sq.zbnss.service.TrainService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 培训记录(TbTrain)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:28
 */
@Api(value = "培训管理", tags = "培训管理")
@RestController
@RequestMapping("/api/Train")
public class TrainController {
    /**
     * 服务对象
     */
    @Resource
    private TrainService tbTrainService;

    @Resource
    private CompanyService companyService;

    /**
     * 分页查询
     *
     * @param train 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "分页获取培训列表", tags = "培训管理")
    @GetMapping("/list")
    @ResponseBody
    public PageResultVo queryByPage(Train train, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer gageSize) {
        IPage<Train> pageTrain = this.tbTrainService.queryByPage(train,pageNum,gageSize);
        return ResultUtil.table(pageTrain.getRecords(), pageTrain.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "获取指定id的培训信息", tags = "培训管理")
    @GetMapping("{id}")
    @ResponseBody
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("培训信息获取成功",this.tbTrainService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param train 实体
     * @return 新增结果
     */
    @ApiOperation(value = "新增培训信息", tags = "培训管理")
    @PostMapping("/add")
    public ResponseVo add(Train train) {
        if(train.getCompanyId() == null || train.getCompanyId().getId() == 0 ||
                train.getTrainCompany() == null || "".equals(train.getTrainCompany())||
                train.getPlanStime() == null || train.getPlanEtime() == null|| train.getLacture() == null){
                return  ResultUtil.error("请求参数错误");
        }
        Company company = this.companyService.queryById(train.getCompanyId().getId());
        if(company == null){
            return  ResultUtil.error("受培训单位信息不存在，请先录入培训单位信息");
        }
        ArrayList<Train> trains = this.tbTrainService.queryByPage(train);
        if(trains.size() > 0){
            return  ResultUtil.error("培训记录已存在，请检查培训信息");
        }else {
            Train addResult = this.tbTrainService.insert(train);
            if(addResult == null){
                return  ResultUtil.error("培训信息添加失败");
            }
            return  ResultUtil.success("培训记录添加成功",addResult);
        }
    }

    /**
     * 编辑数据
     *
     * @param train 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改培训信息", tags = "培训管理")
    @PutMapping("/update")
    @ResponseBody
    public ResponseVo edit(Train train) {
        if(train == null || train.getId() == 0 || train.getTrainId() == null || "".equals(train.getTrainId())){
            return  ResultUtil.error("请求参数错误");
        }
        Train oldTrain = this.tbTrainService.queryById(train.getId());
        if(oldTrain == null){
            return  ResultUtil.error("培训记录不存在");
        }
        Train newTrail =this.tbTrainService.update(train);
        if(newTrail == null){
            return  ResultUtil.error("培训信息修改失败");
        }
        return ResultUtil.success("培训信息修改成功", newTrail);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseVo deleteById(Integer id) {
        Train oldTrain = this.tbTrainService.queryById(id);
        if(oldTrain == null){
            return  ResultUtil.error("培训记录不存在");
        }
        boolean  result = this.tbTrainService.deleteById(id);
        if(result){
            return  ResultUtil.success("培训信息删除成功");
        }
        return ResultUtil.success("培训信息删除失败");
    }

}

