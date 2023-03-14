package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 操作日志(TbLog)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:23
 */
public interface LogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Log queryById(Integer id);

    /**
     * 分页查询
     *
     * @param log 筛选条件
     * @return 查询结果
     */
    IPage<Log> queryByPage(Log log, Integer pageNum, Integer pageSize);
    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    Log insert(Log log);


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
