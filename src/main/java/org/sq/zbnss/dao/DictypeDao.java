package org.sq.zbnss.dao;

import org.apache.ibatis.annotations.Mapper;
import org.sq.zbnss.entity.Dictype;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型(TbDictype)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
@Mapper
public interface DictypeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dictype queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param dictype 查询条件
     * @return 对象列表
     */
    ArrayList<Dictype> queryAllByLimit(Dictype dictype);

    /**
     * 统计总行数
     *
     * @param dictype 查询条件
     * @return 总行数
     */
    long count(Dictype dictype);

    /**
     * 新增数据
     *
     * @param dictype 实例对象
     * @return 影响行数
     */
    int insert(Dictype dictype);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbDictype> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Dictype> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbDictype> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Dictype> entities);

    /**
     * 修改数据
     *
     * @param dictype 实例对象
     * @return 影响行数
     */
    int update(Dictype dictype);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

