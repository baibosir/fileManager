package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.*;
import org.sq.zbnss.service.*;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.JWTUtil;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;

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

    @Resource
    private LogService logService;

    @Resource
    private UserService userService;

    @Resource
    private DicService dicService;

    @Resource
    private CompanyService companyService;

    /**
     * 分页查询
     *
     * @param tbCheck 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取检查列表", tags = "检查管理")
    @ApiOperationSupport(includeParameters = {"systemId.id","checkUser.id","companyId.id","detail","planDate"})
    @GetMapping("/list")
    public PageResultVo queryByPage( Check tbCheck,
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
    public ResponseVo add(@RequestBody Check tbCheck, HttpServletRequest request) {
        if(tbCheck == null || tbCheck.getCompanyId() == null || tbCheck.getPlanDate() == null ||tbCheck.getCompanyId().getId() <= 0 ){
            return ResultUtil.error("检查单位CompanyId()和计划检查时间（PlanDate）不能为空");
        }
        if(tbCheck.getType() == null || tbCheck.getType().getId() <= 0){
            return ResultUtil.error("检查类型不能为空");
        }
        int typeId = tbCheck.getType().getId();
        Dic dic = dicService.queryById(typeId);
        if(dic == null){
            return ResultUtil.error("检查类型错误");
        }
        Company company = companyService.queryById(tbCheck.getCompanyId().getId());
        if(company == null){
            return ResultUtil.error("检查单位信息错误");
        }
        User loginUser = getLoginUser(request);
        ArrayList<Check> old = this.tbCheckService.queryByPage(tbCheck);
        if(old.size() > 0){
            return ResultUtil.error("检查信息已存在");
        }
        tbCheck.setInsertUser(loginUser);
        tbCheck.setUpdateUser(loginUser);
        Check check = this.tbCheckService.insert(tbCheck);

        if(check == null){
            return ResultUtil.error("检查信息添加失败");
        }

        Log log = new Log();
        log.setUserId(loginUser);
        log.setOperate(MessageFormat.format("新增检查信息{0}",check.toString()));
        logService.insert(log);
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
    public ResponseVo edit(@RequestBody  Check tbCheck, HttpServletRequest request) {
        if(tbCheck == null || tbCheck.getId() == 0 || tbCheck.getCheckId() == null || "".equals(tbCheck.getCheckId())){
            return ResultUtil.error("请求参数错误");
        }
        if(tbCheck.getId() <= 0){
            return ResultUtil.error("不存在id<=0的检查信息");
        }
        if(tbCheck.getType() != null){
            int typeId = tbCheck.getType().getId();
            Dic dic1 = dicService.queryById(typeId);
            if(dic1 == null){
                return ResultUtil.error("检查类型错误");
            }
        }
        User loginUser = getLoginUser(request);
        Check check_old = this.tbCheckService.queryById(tbCheck.getId());
        if(check_old == null){
            return ResultUtil.error("检查信息不存在");
        }
        int statusId = check_old.getStatus().getId();
        if(statusId > 0){
            ArrayList<Dic> statuses = dicService.queryByType(5);
            boolean flag = false;
            for(Dic dic : statuses){
                if(statusId == dic.getId()){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                return ResultUtil.error("检查信息状态只能修改为字典中规定的状态");
            }
        }
        if(check_old.getStatus().getId() == 15 || check_old.getStatus().getId() == 16){
            return ResultUtil.error("检查已完成，不能修改");
        }
        tbCheck.setUpdateUser(loginUser);
        Check check= this.tbCheckService.update(tbCheck);
        if(check == null){
            return ResultUtil.error("检查信息修改失败");
        }else{
            Log log = new Log();
            log.setUserId(loginUser);
            log.setOperate(MessageFormat.format("修改检查信息{0}",check.toString()));
            logService.insert(log);
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
    public ResponseVo deleteById(Integer id, HttpServletRequest request) {
        if(id == 0){
            return ResultUtil.error("请求参数错误");
        }
        Check check_old = this.tbCheckService.queryById(id);
        if(check_old == null){
            return ResultUtil.error("检查信息不存在");
        }
        boolean result = this.tbCheckService.deleteById(id);
        if(result){
            User loginUser = getLoginUser(request);
            Log log = new Log();
            log.setUserId(loginUser);
            log.setOperate(MessageFormat.format("修改检查信息{0}",check_old.toString()));
            logService.insert(log);
            return ResultUtil.error("检查信息删除成功");
        }else{
            return ResultUtil.error("检查信息删除失败");
        }
    }

    public User getLoginUser(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String[] tokens = tokenHeader.split(" ");
        String token = tokens[1];
        String userId = JWTUtil.getUserId(token);
        User loginUser = userService.getUserByUserId(userId);
        return loginUser;
    }
}

