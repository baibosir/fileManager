package org.sq.zbnss.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sq.zbnss.entity.RecordSystem;
import java.util.List;

/**
 * 系统信息(TbSystem)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-06 20:14:27
 */
@Mapper
public interface SystemDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RecordSystem queryById(Integer id);

    IPage<RecordSystem> selectRecord(@Param("page") IPage<RecordSystem> page, @Param("recordSystem") RecordSystem recordSystem);
    /**
     * 查询指定行数据
     *
     * @param recordSystem 查询条件
     * @return 对象列表
     */
    List<RecordSystem> queryAllByLimit(RecordSystem recordSystem);

    /**
     * 统计总行数
     *
     * @param recordSystem 查询条件
     * @return 总行数
     */
    long count(RecordSystem recordSystem);

    /**
     * 新增数据
     *
     * @param recordSystem 实例对象
     * @return 影响行数
     */
    int insert(RecordSystem recordSystem);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbSystem> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RecordSystem> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbSystem> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<RecordSystem> entities);

    /**
     * 修改数据
     *
     * @param recordSystem 实例对象
     * @return 影响行数
     */
    int update(RecordSystem recordSystem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

