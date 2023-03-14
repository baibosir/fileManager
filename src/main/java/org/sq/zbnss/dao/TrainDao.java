package org.sq.zbnss.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.sq.zbnss.entity.Train;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 培训记录(TbTrain)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-06 20:14:28
 */
@Mapper
public interface TrainDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Train queryById(Integer id);

    IPage<Train> selectTrain(@Param("page") IPage<Train> page, @Param("train")Train train);
    /**
     * 查询指定行数据
     *
     * @param train 查询条件
     * @return 对象列表
     */
    ArrayList<Train> queryAllByLimit(Train train);

    /**
     * 统计总行数
     *
     * @param train 查询条件
     * @return 总行数
     */
    long count(Train train);

    /**
     * 新增数据
     *
     * @param train 实例对象
     * @return 影响行数
     */
    int insert(Train train);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbTrain> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Train> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbTrain> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Train> entities);

    /**
     * 修改数据
     *
     * @param train 实例对象
     * @return 影响行数
     */
    int update(Train train);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

