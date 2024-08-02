package com.example.travel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.travel.entity.House;
import com.example.travel.repository.HouseRepository;
import com.example.travel.form.HouseRegisterForm;
import com.example.travel.form.HouseEditForm;

@Service
public class HouseService {
  private final HouseRepository houseRepository;

  public HouseService(HouseRepository houseRepository) {
    this.houseRepository = houseRepository;
  }

  @Transactional
  public void create(HouseRegisterForm houseRegisterForm) {
    House house = new House();
    MultipartFile imageFile = houseRegisterForm.getImageFile();

    if (!imageFile.isEmpty()) {
      String imageName = imageFile.getOriginalFilename();
      String hashedImageName = generateNewFileName(imageName);
      Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
      copyImageFile(imageFile, filePath);
      house.setImageName(hashedImageName);
    }

    house.setName(houseRegisterForm.getName());
    house.setDescription(houseRegisterForm.getDescription());
    house.setPrice(houseRegisterForm.getPrice());
    house.setCapacity(houseRegisterForm.getCapacity());
    house.setPostalCode(houseRegisterForm.getPostalCode());
    house.setAddress(houseRegisterForm.getAddress());
    house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
    houseRepository.save(house);
  }

  @Transactional
  public void update(HouseEditForm houseEditForm) {
    House house = houseRepository.getReferenceById(houseEditForm.getId());
    MultipartFile imageFile = houseEditForm.getImageFile();

    if (!imageFile.isEmpty()) {
      String imageName = imageFile.getOriginalFilename();
      String hashedImageName = generateNewFileName(imageName);
      Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
      copyImageFile(imageFile, filePath);
      house.setImageName(hashedImageName);
    }

    house.setName(houseEditForm.getName());
    house.setDescription(houseEditForm.getDescription());
    house.setPrice(houseEditForm.getPrice());
    house.setCapacity(houseEditForm.getCapacity());
    house.setPostalCode(houseEditForm.getPostalCode());
    house.setAddress(houseEditForm.getAddress());
    house.setPhoneNumber(houseEditForm.getPhoneNumber());
    houseRepository.save(house);
  }

  private String generateNewFileName(String fileName) {
    String[] fileNames = fileName.split("\\.");
    for (int i = 0; i < fileNames.length - 1; i++) {
      fileNames[i] = UUID.randomUUID().toString();
    }
    String hashedFileName = String.join(".", fileNames);
    return hashedFileName;
  }

  private void copyImageFile(MultipartFile imageFile, Path filePath) {
    try {
      Files.copy(imageFile.getInputStream(), filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
