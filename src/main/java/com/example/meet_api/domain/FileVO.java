package com.example.meet_api.domain;

import lombok.Data;

@Data
public class FileVO {
    private int id;
    private String fileType;
    private String filePath;
    private String originalFileName;
    private String storeFileName;
    private String fileExtension;
    private long fileSize;
    private String fileDescription;
}
