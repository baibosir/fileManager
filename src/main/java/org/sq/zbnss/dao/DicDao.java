package org.sq.zbnss.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.sq.zbnss.entity.Dic;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典(TbDic)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
@Mapper
public interface DicDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dic queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param dic 查询条件
     * @return 对象列表
     */
    IPage<Dic> queryAllByLimit(@Param("dic") Dic dic, @Param("page") IPage<Dic> page);

    ArrayList<Dic> queryAllByLimit(@Param("dic") Dic dic);

    /**
     * 统计总行数
     *
     * @param dic 查询条件
     * @return 总行数
     */
    long count(Dic dic);

    /**
     * 新增数据
     *
     * @param dic 实例对象
     * @return 影响行数
     */
    int insert(Dic dic);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbDic> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Dic> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TbDic> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Dic> entities);

    /**
     * 修改数据
     *
     * @param dic 实例对象
     * @return 影响行数
     */
    int update(Dic dic);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

