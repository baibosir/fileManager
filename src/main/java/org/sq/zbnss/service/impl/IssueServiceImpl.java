package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.SecurityUtils;
import org.sq.zbnss.entity.Issue;
import org.sq.zbnss.dao.IssueDao;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.IssueService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.Pagination;
import org.sq.zbnss.uitl.UUIDUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 问题(TbIssue)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:22
 */
@Service("tbIssueService")
public class IssueServiceImpl implements IssueService {
    @Resource
    private IssueDao issueDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Issue queryById(Integer id) {
        return this.issueDao.queryById(id);
    }

    @Override
    public IPage<Issue> queryByPage(Issue issue,int pageNumber, int pageSize) {
        IPage<Issue> page =  new Pagination<>(pageNumber, pageSize);
        return issueDao.selectIssues(page, issue);
    }

    /**
     * 分页查询
     *
     * @param issue 筛选条件
     * @return 查询结果
     */
    @Override
    public ArrayList<Issue> queryByPage(Issue issue) {
        return this.issueDao.queryAllByLimit(issue);
    }

    /**
     * 新增数据
     *
     * @param issue 实例对象
     * @return 实例对象
     */
    @Override
    public Issue insert(Issue issue) {
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        issue.setIssueId(UUIDUtil.getUniqueIdByUUId());
        int num = this.issueDao.insert(issue);
        if(num == 0){
            return null;
        }
        return this.issueDao.queryAllByLimit(issue).get(0);
    }

    /**
     * 修改数据
     *
     * @param issue 实例对象
     * @return 实例对象
     */
    @Override
    public Issue update(Issue issue) {
        int num = this.issueDao.update(issue);
        if(num == 0){
            return null;
        }
        return this.queryById(issue.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.issueDao.deleteById(id) > 0;
    }
}
