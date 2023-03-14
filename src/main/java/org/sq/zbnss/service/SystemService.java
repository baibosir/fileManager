package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Company;
import org.sq.zbnss.entity.RecordSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

/**
 * 系统信息(TbSystem)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:28
 */
public interface SystemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RecordSystem queryById(Integer id);

    IPage<RecordSystem> queryByPage(RecordSystem company, Integer pageNumber, Integer pageSize);

    /**
     * 分页查询
     *
     * @param recordSystem 筛选条件
     * @return 查询结果
     */
    ArrayList<RecordSystem> queryByPage(RecordSystem recordSystem);

    /**
     * 新增数据
     *
     * @param recordSystem 实例对象
     * @return 实例对象
     */
    int insert(RecordSystem recordSystem);

    /**
     * 修改数据
     *
     * @param recordSystem 实例对象
     * @return 实例对象
     */
    RecordSystem update(RecordSystem recordSystem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
