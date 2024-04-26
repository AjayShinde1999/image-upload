package com.imageupload.service;

import com.imageupload.entity.Image;
import com.imageupload.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImage(byte[] imageData) {
        Image image = new Image();
        image.setImage(imageData);
        // You might want to extract and store the format of the image as well
        // image.setFormat(getImageFormat(imageData));
        imageRepository.save(image);
    }

    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }
}

