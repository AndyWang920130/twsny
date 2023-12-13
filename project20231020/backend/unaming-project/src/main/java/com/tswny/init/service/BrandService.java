package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.clothes.Brand;
import com.tswny.init.domain.clothes.QBrand;
import com.tswny.init.repository.BrandRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

/**
 * (Brand)表服务实现类
 *
 * @author makejava
 * @since 2023-12-05 11:01:08
 */
@Transactional
@Service("brandService")
public class BrandService {
    @Resource
    private BrandRepository brandRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Brand queryById(Long id) {
        return this.brandRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    public Page<Brand> queryByPage(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBrand qBrand  = QBrand.brand;
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qBrand.name.like("%" + keyword + "%"),
                    qBrand.country.like("%" + keyword + "%"));
        }
        return this.brandRepository.findAll(booleanBuilder, pageable);
    }

    /**
     * 新增数据
     *
     * @param brand 实例对象
     * @return 实例对象
     */
    public Brand insert(Brand brand) {
        return this.brandRepository.save(brand);
    }

    /**
     * 修改数据
     *
     * @param brand 实例对象
     * @return 实例对象
     */
    public Brand update(Brand brand) {
        return this.brandRepository.save(brand);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        this.brandRepository.deleteById(id);
        return true;
    }
}
