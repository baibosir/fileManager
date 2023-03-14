package org.sq.zbnss.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

/**
 * 培训记录(TbTrain)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 20:14:29
 */
public interface TrainService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Train queryById(Integer id);

    IPage<Train> queryByPage(Train train, int pageNum, int pageSize);

    /**
     * 分页查询
     *
     * @param train 筛选条件
     * @return 查询结果
     */
    ArrayList<Train> queryByPage(Train train);

    /**
     * 新增数据
     *
     * @param train 实例对象
     * @return 实例对象
     */
    Train insert(Train train);

    /**
     * 修改数据
     *
     * @param train 实例对象
     * @return 实例对象
     */
    Train update(Train train);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
