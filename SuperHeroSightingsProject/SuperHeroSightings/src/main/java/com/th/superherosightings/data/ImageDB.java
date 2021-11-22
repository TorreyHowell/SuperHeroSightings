package com.th.superherosightings.data;

import org.springframework.web.multipart.MultipartFile;

public interface ImageDB {
  public boolean saveImage(MultipartFile file, String fileName);

  public void updateImage(MultipartFile file, String fileName);

  public boolean deleteImage(String oldFile);
}
