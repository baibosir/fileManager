package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

/**
 * 问题(TbIssue)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:22
 */
public interface IssueService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Issue queryById(Integer id);

    IPage<Issue> queryByPage(Issue issue,int pageNumber, int pageSize);

    /**
     * 分页查询
     *
     * @param issue 筛选条件
     * @return 查询结果
     */
    ArrayList<Issue> queryByPage(Issue issue);

    /**
     * 新增数据
     *
     * @param issue 实例对象
     * @return 实例对象
     */
    Issue insert(Issue issue);

    /**
     * 修改数据
     *
     * @param issue 实例对象
     * @return 实例对象
     */
    Issue update(Issue issue);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
