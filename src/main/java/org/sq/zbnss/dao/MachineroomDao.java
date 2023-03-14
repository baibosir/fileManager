package org.sq.zbnss.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.sq.zbnss.entity.Machineroom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 机房(TbMachineroom)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-06 20:14:23
 */
@Mapper
public interface MachineroomDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Machineroom queryById(Integer id);


    IPage<Machineroom> queryMRoom(@Param("page") IPage<Machineroom> page, @Param("machine") Machineroom machine);

    /**
     * 查询指定行数据
     *
     * @param machineroom 查询条件
     * @return 对象列表
     */
    ArrayList<Machineroom> queryAllByLimit(Machineroom machineroom);

    /**
     * 统计总行数
     *
     * @param machineroom 查询条件
     * @return 总行数
     */
    long count(Machineroom machineroom);

    /**
     * 新增数据
     *
     * @param machineroom 实例对象
     * @return 影响行数
     */
    int insert(Machineroom machineroom);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMachineroom> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Machineroom> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbMachineroom> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Machineroom> entities);

    /**
     * 修改数据
     *
     * @param machineroom 实例对象
     * @return 影响行数
     */
    int update(Machineroom machineroom);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

