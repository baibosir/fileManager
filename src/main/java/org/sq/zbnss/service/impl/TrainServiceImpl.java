package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Train;
import org.sq.zbnss.dao.TrainDao;
import org.sq.zbnss.service.TrainService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.Pagination;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 培训记录(TbTrain)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:29
 */
@Service("tbTrainService")
public class TrainServiceImpl implements TrainService {
    @Resource
    private TrainDao trainDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Train queryById(Integer id) {
        return this.trainDao.queryById(id);
    }

    @Override
    public IPage<Train> queryByPage(Train train, int pageNum, int pageSize){
        IPage<Train> page =  new Pagination<>(pageNum,pageSize);
        return this.trainDao.selectTrain(page,train);
    }

    /**
     * 分页查询
     *
     * @param train 筛选条件
     * @return 查询结果
     */
    @Override
    public ArrayList<Train> queryByPage(Train train) {
        return this.trainDao.queryAllByLimit(train);
    }

    /**
     * 新增数据
     *
     * @param train 实例对象
     * @return 实例对象
     */
    @Override
    public Train insert(Train train) {
        int num = this.trainDao.insert(train);
        if(num == 0){
            return null;
        }
        return this.trainDao.queryAllByLimit(train).get(0);
    }

    /**
     * 修改数据
     *
     * @param train 实例对象
     * @return 实例对象
     */
    @Override
    public Train update(Train train) {
        int num = this.trainDao.update(train);
        if(num == 0){
            return null;
        }
        return this.queryById(train.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.trainDao.deleteById(id) > 0;
    }
}
