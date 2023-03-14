package org.sq.zbnss.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.sq.zbnss.entity.Log;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志(TbLog)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-06 20:14:22
 */
@Mapper
public interface LogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Log queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    IPage<Log> selectLogs(@Param("syslog") Log syslog, @Param("page") IPage<Log> page);

    /**
     * 统计总行数
     *
     * @param log 查询条件
     * @return 总行数
     */
    long count(Log log);

    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 影响行数
     */
    int insert(Log log);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

