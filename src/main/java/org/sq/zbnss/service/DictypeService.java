package org.sq.zbnss.service;

import org.sq.zbnss.entity.Dictype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

/**
 * 字典类型(TbDictype)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:21
 */
public interface DictypeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Dictype queryById(Integer id);

    /**
     * 分页查询
     *
     * @param dictype 筛选条件
     * @return 查询结果
     */
    ArrayList<Dictype> queryByPage(Dictype dictype);

    /**
     * 新增数据
     *
     * @param dictype 实例对象
     * @return 实例对象
     */
    Dictype insert(Dictype dictype);

    /**
     * 修改数据
     *
     * @param dictype 实例对象
     * @return 实例对象
     */
    Dictype update(Dictype dictype);



}
