package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Log;
import org.sq.zbnss.dao.LogDao;
import org.sq.zbnss.service.LogService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.Pagination;

import javax.annotation.Resource;

/**
 * 操作日志(TbLog)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:23
 */
@Service("tbLogService")
public class LogServiceImpl implements LogService {
    @Resource
    private LogDao tbLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Log queryById(Integer id) {
        return this.tbLogDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param log 筛选条件
     * @return 查询结果
     */
    @Override
    public IPage<Log> queryByPage(Log log, Integer pageNum, Integer pageSize) {
        IPage<Log> page = new Pagination<>(pageNum,pageSize);
        return this.tbLogDao.selectLogs(log,page);
    }

    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    @Override
    public Log insert(Log log) {
        this.tbLogDao.insert(log);
        return log;
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tbLogDao.deleteById(id) > 0;
    }
}
