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
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.CompanyService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.uitl.ResultUtil;

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

    /**
     * 分页查询
     *
     * @param company 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取单位列表", tags = "单位管理")
    @ApiOperationSupport(includeParameters = {"principal.id","name","address","telephone","telephone","description","type"
            ,"numSystem1","numSystem2","numSystem3","pageNumber","pageSize"})
    @PostMapping("/list")
    @ResponseBody
    public PageResultVo queryByPage(@RequestBody()Company company,@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize")Integer pageSize) {
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
    @ApiOperationSupport(includeParameters = {"principal.id","name","address","telephone","telephone","description","type"
            ,"numSystem1","numSystem2","numSystem3"})
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(Company company) {
        Company company1 = tbCompanyService.queryAllByLimit(company);
        if(null != company1){
            return ResultUtil.error("单位已存在");
        }
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        company.setInsertUser(loginUser);
        company.setUpdateUser(loginUser);
        int num = this.tbCompanyService.insert(company);
        if (num > 0) {
            return ResultUtil.success("单位添加成功");
        } else {
            return ResultUtil.error("添单位添加失败");
        }
    }

    /**
     * 编辑数据
     *
     * @param company 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改单位信息", tags = "单位管理")
    @ApiOperationSupport(includeParameters = {"principal.id","name","address","telephone","telephone","description","type"
            ,"numSystem1","numSystem2","numSystem3"})
    @PutMapping("/modify")
    @ResponseBody
    public ResponseVo edit(Company company) {
        Company company1 = tbCompanyService.queryAllByLimit(company);
        if(null == company1){
            return ResultUtil.error("单位不存在");
        }
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        company.setUpdateUser(loginUser);
        Company updateResult = this.tbCompanyService.update(company);
        if(updateResult == null){
            return ResultUtil.error("添单位修改失败");
        }
        return ResultUtil.success("单位修改成功",this.tbCompanyService.update(company));
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
    public ResponseVo deleteById(Integer id) {
       boolean result = this.tbCompanyService.deleteById(id);
        if (result) {
            return ResultUtil.success("删除单位成功");
        } else {
            return ResultUtil.error("删除单位失败");
        }
    }

}

