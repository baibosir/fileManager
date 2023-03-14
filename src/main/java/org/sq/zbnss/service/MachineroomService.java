package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Machineroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

/**
 * 机房(TbMachineroom)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:25
 */
public interface MachineroomService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Machineroom queryById(Integer id);

    IPage<Machineroom> queryByPage(Machineroom machineroom, Integer pageNumber, Integer pageSize);

    /**
     * 分页查询
     *
     * @param machineroom 筛选条件
     * @return 查询结果
     */
    ArrayList<Machineroom> queryByPage(Machineroom machineroom);

    /**
     * 新增数据
     *
     * @param machineroom 实例对象
     * @return 实例对象
     */
    Machineroom insert(Machineroom machineroom);

    /**
     * 修改数据
     *
     * @param machineroom 实例对象
     * @return 实例对象
     */
    Machineroom update(Machineroom machineroom);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
