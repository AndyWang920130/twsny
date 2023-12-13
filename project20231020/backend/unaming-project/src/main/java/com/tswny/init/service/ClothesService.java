package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.clothes.Clothes;
import com.tswny.init.domain.clothes.QClothes;
import com.tswny.init.repository.BrandRepository;
import com.tswny.init.repository.ClothesRepository;
import com.tswny.init.web.rest.BrandResource;
import com.tswny.init.web.rest.vm.ClothesVM;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

/**
 * (Clothes)表服务实现类
 *
 * @author makejava
 * @since 2023-12-05 11:40:22
 */
@Transactional
@Service("clothesService")
public class ClothesService {
    @Resource
    private ClothesRepository clothesRepository;

    @Resource
    private BrandRepository brandRepository;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Clothes queryById(Long id) {
        return this.clothesRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    public Page<Clothes> queryByPage(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QClothes qClothes = QClothes.clothes;
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qClothes.name.like("%" + keyword + "%"));
        }

        return this.clothesRepository.findAll(booleanBuilder, pageable);
    }

    /**
     * 新增数据
     *
     * @param clothesVM 实例对象
     * @return 实例对象
     */
    public Clothes insert(ClothesVM clothesVM) {
        Clothes clothes = new Clothes();
        clothes.setId(clothesVM.getId());
        clothes.setName(clothesVM.getName());
        if (clothesVM.getBrandId() != null) clothes.setBrand(brandRepository.findById(clothesVM.getBrandId()).orElse(null));
        clothes.setPrice(clothesVM.getPrice());
        clothes.setClothesTypeEnum(clothesVM.getClothesType());
        clothes.setPurchaseDate(clothesVM.getPurchaseDate());
        clothes.setImagePaths(String.join(";", clothesVM.getImagePaths()));
        return this.clothesRepository.save(clothes);
    }

    /**
     * 修改数据
     *
     * @param clothes 实例对象
     * @return 实例对象
     */
    public Clothes update(Clothes clothes) {
        return this.clothesRepository.save(clothes);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        this.clothesRepository.deleteById(id);
        return true;
    }
}
