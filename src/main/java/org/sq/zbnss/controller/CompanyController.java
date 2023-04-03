package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Company;
import org.sq.zbnss.entity.Log;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.CompanyService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.service.LogService;
import org.sq.zbnss.service.UserService;
import org.sq.zbnss.uitl.JWTUtil;
import org.sq.zbnss.uitl.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;

import static cn.hutool.system.SystemUtil.getUserInfo;

/**
 * 单位信息(TbCompany)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:19
 */
@Api(value = "单位管理", tags = "单位管理")
@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {
    /**
     * 服务对象
     */
    private CompanyService tbCompanyService;

    private  UserService userService;

    private LogService logService;

    /**
     * 分页查询
     *
     * @param company 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取单位列表", tags = "单位管理")
    @GetMapping("/list")
    @ResponseBody
    public PageResultVo queryByPage(@RequestBody(required = false) Company company,@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
        IPage<Company> userPage = tbCompanyService.queryByPage(company,pageNumber,pageSize);
        return ResultUtil.table(userPage.getRecords(), userPage.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取单位列表", tags = "单位管理")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("单位详情获取成功",this.tbCompanyService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param company 实体
     * @return 新增结果
     */
    @ApiOperation(value = "新增单位信息", tags = "单位管理")
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(@RequestBody() Company company, HttpServletRequest request) {
        if(company.getName() == null || "".equals(company.getName()) || company.getAddress() ==null || "".equals(company.getAddress())){
            return ResultUtil.error("请求参数错误");
        }
        ArrayList<Company> company1 = tbCompanyService.queryAllByLimit(company);
        if(company1.size() > 0){
            return ResultUtil.error("单位已存在");
        }
        User loginUser = getLoginUser(request);
        company.setInsertUser(loginUser);
        company.setUpdateUser(loginUser);
        int num = this.tbCompanyService.insert(company);
        if (num > 0) {
            Log log = new Log();
            log.setUserId(loginUser);
            log.setOperate(MessageFormat.format("新增单位{0}",company.getName()));
            logService.insert(log);
            return ResultUtil.success("单位添加成功");
        } else {
            return ResultUtil.error("添单位添加失败");
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
    /**
     * 编辑数据
     *
     * @param company 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改单位信息", tags = "单位管理")
    @PutMapping("/modify")
    @ResponseBody
    public ResponseVo edit(@RequestBody()Company company, HttpServletRequest request) {
        ArrayList<Company>  company1 = tbCompanyService.queryAllByLimit(company);
        if(company1.size() == 0){
            return ResultUtil.error("单位不存在");
        }
        User loginUser = getLoginUser(request);
        company.setUpdateUser(loginUser);
        Company updateResult = this.tbCompanyService.update(company);
        if(updateResult == null){
            return ResultUtil.error("添单位修改失败");
        }
        Company company2 =this. tbCompanyService.update(company);
        Log log = new Log();
        log.setUserId(loginUser);
        log.setOperate(MessageFormat.format("修改单位{0}:{1}",company.getName(),company2.toString()));
        logService.insert(log);
        return ResultUtil.success("单位修改成功",company2);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation(value = "删除单位信息", tags = "单位管理")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseVo deleteById(Integer id, HttpServletRequest request) {
        Company company = this.tbCompanyService.queryById(id);
        if(company == null){
            return ResultUtil.error("删除单位失败(单位不存在)");
        }
       boolean result = this.tbCompanyService.deleteById(id);
        if (result) {
            User loginUser = getLoginUser(request);
            Log log = new Log();
            log.setUserId(loginUser);
            log.setOperate(MessageFormat.format("删除单位{0}:{1}",company.getName()));
            return ResultUtil.success("删除单位成功");
        } else {
            return ResultUtil.error("删除单位失败");
        }
    }

}

