package com.baza.firmy.utils;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
class SpecificationUtil<T> {

  Path<T> getPathFromString(Root<T> root, String path) {
    Path<Object> objectPath = (Path<Object>) root;
    String[] splitedPath = path.split("\\.");

    return createRootPath(objectPath, splitedPath, 0);
  }

  private Path<T> createRootPath(Path<Object> objectPath, String[] partOfPath, int i) {
    if (i > partOfPath.length - 2) {
      return objectPath.get(partOfPath[i]);
    } else {
      return createRootPath(objectPath.get(partOfPath[i]), partOfPath, ++i);
    }
  }
}
