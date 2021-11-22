package com.th.superherosightings.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class ImageDBImpl implements ImageDB {
  private final String UPLOAD_DIR = "./src/main/resources/Images/";

  @Override
  public boolean saveImage(MultipartFile file, String fileName) {

    try {
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return true;
    } catch (IOException ignored) {
    }

    return false;
  }

  @Override
  public void updateImage(MultipartFile file, String fileName) {
    try {
      Path path = Paths.get(UPLOAD_DIR + fileName);
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ignored) {
    }
  }

  @Override
  public boolean deleteImage(String oldFile) {
    return new File(UPLOAD_DIR + oldFile).delete();
  }
}
