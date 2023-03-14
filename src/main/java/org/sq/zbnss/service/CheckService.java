package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

/**
 * 检查结果记录(TbCheck)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:17
 */
public interface CheckService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Check queryById(Integer id);

    /**
     * 分页查询
     *
     * @param tbCheck 筛选条件
     * @return 查询结果
     */
    ArrayList<Check> queryByPage(Check tbCheck);


    IPage<Check> queryByPage(Check check, Integer pageNumber, Integer pageSize);

    /**
     * 新增数据
     *
     * @param tbCheck 实例对象
     * @return 实例对象
     */
    Check insert(Check tbCheck);

    /**
     * 修改数据
     *
     * @param tbCheck 实例对象
     * @return 实例对象
     */
    Check update(Check tbCheck);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
