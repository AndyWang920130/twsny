package com.tswny.init.service;

import com.tswny.init.domain.file.Directory;
import com.tswny.init.domain.file.File;
import com.tswny.init.service.dto.FileManagementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class FileManagementService {
    private final FileService fileService;
    private final DirectoryService directoryService;

    public FileManagementService(FileService fileService, DirectoryService directoryService) {
        this.fileService = fileService;
        this.directoryService = directoryService;
    }

    // TODO: 2023/12/20 need to complete
    public Page<FileManagementDTO> queryByPage(String keyword, Long folderId, Pageable pageable) {
        Page<Directory> directoryPage = directoryService.queryFolderByPage(keyword, folderId, pageable);
        Page<File> filePage = fileService.queryFileByPage(keyword, folderId, pageable);

        List<FileManagementDTO> folderDTOList = directoryPage.getContent().stream().map(FileManagementDTO::new).collect(Collectors.toList());
        List<FileManagementDTO> fileDTOList = filePage.getContent().stream().map(FileManagementDTO::new).collect(Collectors.toList());

        folderDTOList.addAll(fileDTOList);

        Page<FileManagementDTO> fileManagementDTOPage = new PageImpl<>(folderDTOList, pageable, 100);
        return fileManagementDTOPage;
    }
}
