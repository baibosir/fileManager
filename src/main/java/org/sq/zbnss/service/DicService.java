package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Dic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 字典(TbDic)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
public interface DicService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dic queryById(Integer id);

    /**
     * 分页查询
     *
     * @param dic 筛选条件
     * @return 查询结果
     */
    IPage<Dic> queryByPage(Dic dic, int pageNumber, int pageSize);

    /**
     * 新增数据
     *
     * @param dic 实例对象
     * @return 实例对象
     */
    Dic insert(Dic dic);

    /**
     * 修改数据
     *
     * @param dic 实例对象
     * @return 实例对象
     */
    Dic update(Dic dic);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
