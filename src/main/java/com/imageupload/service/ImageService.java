package com.imageupload.service;

import com.imageupload.entity.Image;
import com.imageupload.repository.ImageRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ImageService {

    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(byte[] imageData, String extension) {
        Image image = new Image();
        image.setImage(imageData);
        image.setExtension(extension);
        System.out.println(extension);
        imageRepository.save(image);
    }

    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }
}

