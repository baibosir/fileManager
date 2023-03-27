package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.dao.DicDao;
import org.sq.zbnss.entity.Company;
import org.sq.zbnss.entity.Dic;
import org.sq.zbnss.entity.Issue;
import org.sq.zbnss.entity.Log;
import org.sq.zbnss.service.CompanyService;
import org.sq.zbnss.service.DicService;
import org.sq.zbnss.service.IssueService;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.service.LogService;
import org.sq.zbnss.uitl.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * 问题(TbIssue)表控制层
 *
 * @author makejava
 * @since 2023-03-06 20:14:21
 */
@Api(value = "问题管理", tags = "问题管理")
@RestController
@RequestMapping("/api/issue")
public class IssueController {
    /**
     * 服务对象
     */
    @Resource
    private IssueService tbIssueService;

    @Resource
    private DicService dicService;

    @Resource
    private CompanyService companyService;

    @Resource
    private Common common;

    @Resource
    private LogService logService;

    /**
     * 分页查询
     *
     * @param issue 筛选条件
     * @return 查询结果
     */
    @ApiOperation(value = "获取问题单列表", tags = "问题管理")
    @ApiOperationSupport(includeParameters = {"companyId.id","issueCount","type","description"})
    @GetMapping("/list")
    @ResponseBody
    public PageResultVo queryByPage(@RequestBody() Issue issue,
                                    @RequestParam(value = "pageNumber", required = true) int pageNumber,
                                    @RequestParam(value = "pageSize", required = true) int pageSize) {
        IPage<Issue> pageData = this.tbIssueService.queryByPage(issue,pageNumber,pageSize);
        return ResultUtil.table(pageData.getRecords(), pageData.getTotal());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id获取问题单列表", tags = "问题管理")
    @GetMapping("{id}")
    @ResponseBody
    public ResponseVo queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success("问题点信息获取成功", this.tbIssueService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param issue 实体
     * @return 新增结果
     */
    @ApiOperation(value = "新增问题单", tags = "问题管理")
    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(@RequestBody()Issue issue, HttpServletRequest request) {
        if(issue.getIssueCount() == 0 || issue.getType() == null || issue.getCompanyId() == null || issue.getCompanyId().getId() <= 0){
           return  ResultUtil.error("问题数量（IssueCount）、问题类型（type）和所属单位不能为空");
        }

        Company company = companyService.queryById(issue.getCompanyId().getId());
        if(company == null){
            return  ResultUtil.error("单位不存在");
        }

        ArrayList<Issue>  issues = this.tbIssueService.queryByPage(issue);
        if(issues.size() > 0){
            return  ResultUtil.error("问题已存在，请勿重复提交", issues.get(0));
        }
        Dic dic = new Dic();
        dic.setId(18);
        issue.setStatus(dic);
        Issue result = this.tbIssueService.insert(issue);
        if(null == result){
            return  ResultUtil.error("问题已添加失败");
        }else{
            Log log = new Log();
            log.setUserId(common.getLoginUser(request));
            log.setOperate(MessageFormat.format("新增问题单信息：{0}",result.toString()));
            logService.insert(log);
            return  ResultUtil.success("问题已添加成功", result);
        }
    }

    /**
     * 编辑数据
     *
     * @param issue 实体
     * @return 编辑结果
     */
    @ApiOperation(value = "修改问题单", tags = "问题管理")
    @PutMapping("/update")
    @ResponseBody
    public ResponseVo edit(@RequestBody()Issue issue, HttpServletRequest request) {
        if(issue.getId() == 0 || null == issue.getIssueId() || "".equals(issue.getIssueId()) ){
            return  ResultUtil.error("请求参数错误");
        }
        Issue oldValue = this.tbIssueService.queryById(issue.getId());
        if(oldValue.getStatus().getId() == 19){
            return  ResultUtil.error("问题已解决，不能修改");
        }
        if(issue.getStatus()!= null && issue.getStatus().getId() > 0){
            Dic dic = dicService.queryById(issue.getStatus().getId());
            if(dic == null || dic.getType().getId() != 6){
                return  ResultUtil.error("问题状态参数错误");
            }
        }
        if(oldValue == null){
            return  ResultUtil.error("问题不存在");
        }
        Issue result= this.tbIssueService.update(issue);
        if(null == result){
            return  ResultUtil.error("问题修改失败");
        }else{
            Log log = new Log();
            log.setUserId(common.getLoginUser(request));
            log.setOperate(MessageFormat.format("新增问题单信息：{0}",result.toString()));
            logService.insert(log);
            return  ResultUtil.success("问题修改成功",result);
        }

    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation(value = "删除问题单", tags = "问题管理")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseVo deleteById(Integer id) {
        Issue oldValue = this.tbIssueService.queryById(id);
        if(oldValue == null){
            return  ResultUtil.error("问题不存在");
        }
        boolean result = this.tbIssueService.deleteById(id);
        if(result){
            return   ResultUtil.success("问题删除成功");
        }else{
            return  ResultUtil.error("问题删除失败");
        }
    }

}

