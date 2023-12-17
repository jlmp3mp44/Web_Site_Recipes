package com.kpi.recipes.service;

import com.kpi.recipes.model.Image;
import com.kpi.recipes.model.dao.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageDAO imageDAO;

    @Autowired
    public ImageService(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }
    public Image saveImage(byte[] imageData) {
        Image image = new Image();
        image.setImageData(imageData);
        return imageDAO.save(image);
    }

}
