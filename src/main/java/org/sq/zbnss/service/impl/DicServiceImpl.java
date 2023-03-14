package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.sq.zbnss.entity.Dic;
import org.sq.zbnss.dao.DicDao;
import org.sq.zbnss.service.DicService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.Pagination;

import javax.annotation.Resource;

/**
 * 字典(TbDic)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
@Service("tbDicService")
public class DicServiceImpl implements DicService {
    @Resource
    private DicDao tbDicDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Dic queryById(Integer id) {
        return this.tbDicDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param dic 筛选条件
     * @return 查询结果
     */
    @Override
    public IPage<Dic> queryByPage(Dic dic, int pageNumber, int pageSize) {
        IPage<Dic> page = new Pagination<>(pageNumber, pageSize);
        return this.tbDicDao.queryAllByLimit(dic, page);
    }

    /**
     * 新增数据
     *
     * @param dic 实例对象
     * @return 实例对象
     */
    @Override
    public Dic insert(Dic dic) {
        int num = this.tbDicDao.insert(dic);
        if(num == 0){
            return null;
        }
        return this.tbDicDao.queryAllByLimit(dic).get(0);
    }

    /**
     * 修改数据
     *
     * @param dic 实例对象
     * @return 实例对象
     */
    @Override
    public Dic update(Dic dic) {
        int num =this.tbDicDao.update(dic);
        if(num == 0){
            return null;
        }
        return this.queryById(dic.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tbDicDao.deleteById(id) > 0;
    }
}
