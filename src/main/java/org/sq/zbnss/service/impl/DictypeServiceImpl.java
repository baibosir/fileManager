package org.sq.zbnss.service.impl;

import org.sq.zbnss.entity.Dictype;
import org.sq.zbnss.dao.DictypeDao;
import org.sq.zbnss.service.DictypeService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.UUIDUtil;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 字典类型(TbDictype)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:21
 */
@Service("tbDictypeService")
public class DictypeServiceImpl implements DictypeService {
    @Resource
    private DictypeDao tbDictypeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Dictype queryById(Integer id) {
        return this.tbDictypeDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param dictype 筛选条件
     * @return 查询结果
     */
    @Override
    public ArrayList<Dictype> queryByPage(Dictype dictype) {
        return this.tbDictypeDao.queryAllByLimit(dictype);
    }

    /**
     * 新增数据
     *
     * @param dictype 实例对象
     * @return 实例对象
     */
    @Override
    public Dictype insert(Dictype dictype) {
        dictype.setTypeId(UUIDUtil.generateShortUuid());
        int num = this.tbDictypeDao.insert(dictype);
        if(num == 0){
            return null;
        }
        return this.tbDictypeDao.queryAllByLimit(dictype).get(0);
    }

    /**
     * 修改数据
     *
     * @param dictype 实例对象
     * @return 实例对象
     */
    @Override
    public Dictype update(Dictype dictype) {
        int num = this.tbDictypeDao.update(dictype);
        if(num == 0){
            return null;
        }
        return this.queryById(dictype.getId());
    }

}
