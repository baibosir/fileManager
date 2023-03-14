package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.sq.zbnss.entity.Company;
import org.sq.zbnss.dao.CompanyDao;
import org.sq.zbnss.service.CompanyService;
import org.springframework.stereotype.Service;
import org.sq.zbnss.uitl.Pagination;
import org.sq.zbnss.uitl.UUIDUtil;

import javax.annotation.Resource;

/**
 * 单位信息(TbCompany)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 20:14:20
 */
@Service
@AllArgsConstructor
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, Company> implements  CompanyService {

    @Resource
    private CompanyDao tbCompanyDao;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Company queryById(Integer id) {
        return this.tbCompanyDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param company 筛选条件
     * @return 查询结果
     */
    @Override
    public IPage<Company> queryByPage(Company company, Integer pageNumber, Integer pageSize) {
        IPage<Company> page =  new Pagination<>(pageNumber, pageSize);
        return tbCompanyDao.selectCompany(page, company);
    }

    public  Company queryAllByLimit(Company company){
        return tbCompanyDao.queryAllByLimit(company);
    }

    /**
     * 新增数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(Company company) {
        company.setCompanyId(UUIDUtil.getUniqueIdByUUId());
        return this.tbCompanyDao.insert(company);
    }

    /**
     * 修改数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    @Override
    public Company update(Company company) {
        int rowCount = this.tbCompanyDao.update(company);
        if(rowCount > 1){
            return this.queryById(company.getId());
        }else{
            return this.queryById(company.getId());
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tbCompanyDao.deleteById(id) > 0;
    }

}
