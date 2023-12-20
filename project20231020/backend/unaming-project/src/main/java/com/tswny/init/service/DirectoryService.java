package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.file.Directory;
import com.tswny.init.domain.file.QDirectory;
import com.tswny.init.handler.exception.BadRequestException;
import com.tswny.init.repository.DirectoryRepository;
import com.tswny.init.web.rest.vm.DirectorVM;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.time.Instant;
import java.util.Optional;

/**
 * (Directory)表服务实现类
 *
 * @author makejava
 * @since 2023-12-18 17:03:09
 */
@Transactional
@Service("directoryService")
public class DirectoryService {
    private final Long rootFolderId = 1L;

    @Resource
    private DirectoryRepository directoryRepository;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public Directory queryById(Long id) {
        return this.directoryRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询
     *

     * @return 查询结果
     */
    public Page<Directory> queryByPage(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QDirectory qDirectory = QDirectory.directory;
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qDirectory.path.like("%" + keyword + "%"));
        }
        return this.directoryRepository.findAll(booleanBuilder, pageable);
    }

    public Page<Directory> queryRootFolderByPage(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QDirectory qDirectory = QDirectory.directory;
        booleanBuilder.and(qDirectory.parent.id.eq(rootFolderId));
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qDirectory.path.like("%" + keyword + "%"));
        }
        return this.directoryRepository.findAll(booleanBuilder, pageable);
    }

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    public Directory insert(DirectorVM directorVM) {
        Directory directory = new Directory();
        directory.setName(directorVM.getName());
        Long parentId = directorVM.getParentId();
        Directory parent = directoryRepository.findById(parentId).orElse(null);
        directory.setParent(parent);
        return this.directoryRepository.save(directory);
    }

    /**
     * 修改数据
     *
     * @return 实例对象
     */
    public Directory update(DirectorVM directorVM) {
        Optional<Directory> directoryOptional = directoryRepository.findById(directorVM.getId());
        if (!directoryOptional.isPresent()) throw new BadRequestException("can not find directory by id: " + directorVM.getId());
        Directory directory =directoryOptional.get();
        directory.setName(directorVM.getName());
        directory.setLastModifiedDate(Instant.now());
        return this.directoryRepository.save(directory);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        this.directoryRepository.deleteById(id);
        return true;
    }
}
