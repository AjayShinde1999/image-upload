package com.imageupload.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] image;
    private String extension;
}
