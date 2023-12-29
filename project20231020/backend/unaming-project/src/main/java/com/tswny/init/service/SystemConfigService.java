package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.QSystemConfig;
import com.tswny.init.domain.SystemConfig;
import com.tswny.init.repository.SystemConfigRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;


import javax.annotation.Resource;

/**
 * (SystemConfig)表服务实现类
 *
 * @author makejava
 * @since 2023-12-28 16:12:26
 */
@Transactional
@Service("systemConfigService")
public class SystemConfigService {
    @Resource
    private SystemConfigRepository systemConfigRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public SystemConfig queryById(Long id) {
        return this.systemConfigRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询
     *

     * @return 查询结果
     */
    public Page<SystemConfig> queryByPage(@RequestParam(required = false) String keyword,
                                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QSystemConfig qSystemConfig = QSystemConfig.systemConfig;
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qSystemConfig.itemKey.like("%" + keyword + "%"));
        }

        return this.systemConfigRepository.findAll(booleanBuilder, pageable);
    }

    /**
     * 新增数据
     *
     * @param systemConfig 实例对象
     * @return 实例对象
     */
    public SystemConfig insert(SystemConfig systemConfig) {
        return this.systemConfigRepository.save(systemConfig);
    }

    /**
     * 修改数据
     *
     * @param systemConfig 实例对象
     * @return 实例对象
     */
    public SystemConfig update(SystemConfig systemConfig) {
        return this.systemConfigRepository.save(systemConfig);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        this.systemConfigRepository.deleteById(id);
        return true;
    }
}
