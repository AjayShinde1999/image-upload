package com.imageupload.payload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private String city;
    private MultipartFile resume;
}
