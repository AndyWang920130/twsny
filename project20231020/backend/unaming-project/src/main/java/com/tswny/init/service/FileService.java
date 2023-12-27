package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.file.Directory;
import com.tswny.init.domain.file.File;
import com.tswny.init.domain.file.QFile;
import com.tswny.init.handler.exception.BadRequestException;
import com.tswny.init.repository.DirectoryRepository;
import com.tswny.init.repository.FileRepository;
import com.tswny.init.web.rest.vm.FileVM;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import com.querydsl.core.BooleanBuilder;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.time.Instant;
import java.util.Optional;

/**
 * (File)表服务实现类
 *
 * @author makejava
 * @since 2023-12-18 17:03:10
 */
@Transactional
@Service("fileService")
public class FileService {
    private final Long rootFolderId = 1L;
    @Resource
    private FileRepository fileRepository;

    private final DirectoryRepository directoryRepository;

    private final FileUploadService fileUploadService;

    public FileService(DirectoryRepository directoryRepository, FileUploadService fileUploadService) {
        this.directoryRepository = directoryRepository;
        this.fileUploadService = fileUploadService;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public File queryById(Long id) {
        return this.fileRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    public Page<File> queryByPage(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QFile qFile = QFile.file;
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qFile.name.like("%" + keyword + "%"));
        }
        return this.fileRepository.findAll(booleanBuilder, pageable);
    }

    public Page<File> queryRootByPage(String keyword, Pageable pageable) {
        return queryFileByPage(keyword, rootFolderId, pageable);
    }

    public Page<File> queryFileByPage(String keyword, Long folderId, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QFile qFile = QFile.file;
        booleanBuilder.and(qFile.directory.id.eq(folderId));
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qFile.name.like("%" + keyword + "%"));
        }
        return this.fileRepository.findAll(booleanBuilder, pageable);
    }
    /**
     * 新增数据
     *
     * @return 实例对象
     */
    public File insert(FileVM fileVM) {
        File file = new File();
        file.setName(fileVM.getName());
        file.setFileName(fileVM.getName());
        Long directoryId = fileVM.getDirectoryId();
        Optional<Directory> directoryOptional = directoryRepository.findById(directoryId);
        if (!directoryOptional.isPresent()) throw new BadRequestException("can not find directory by id: " + directoryId);
        file.setDirectory(directoryOptional.get());
        return this.fileRepository.save(file);
    }

    /**
     * 修改数据
     *
     * @return 实例对象
     */
    public File update(FileVM fileVM) {
        Long id = fileVM.getId();
        Optional<File> fileOptional = fileRepository.findById(id);
        if (!fileOptional.isPresent()) throw new BadRequestException("can not find file by id: " + id);
        File file = fileOptional.get();
        file.setName(fileVM.getName());
        file.setLastModifiedDate(Instant.now());
        return this.fileRepository.save(file);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Long id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (!fileOptional.isPresent()) return false;
        File file = fileOptional.get();
        String relativeFilename = file.getFileName();
        fileUploadService.delete(relativeFilename);
        this.fileRepository.deleteById(id);
        return true;
    }
}
