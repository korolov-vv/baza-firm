package com.baza.firmy.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.utils.Converter;
import net.kaczmarzyk.spring.data.jpa.utils.QueryContext;

public class GreaterThanOrEqualDateIfPresent<T> extends Equal<T> {

  public GreaterThanOrEqualDateIfPresent(
      QueryContext queryContext, String path, String[] httpParamValues, Converter converter) {
    super(queryContext, path, httpParamValues, converter);
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    LocalDate dateFrom = DateUtil.convertStringToLocalDate(this.expectedValue);
    return cb.greaterThanOrEqualTo(
        cb.coalesce(new SpecificationUtil().getPathFromString(root, path), cb.literal(DateUtil.MAX_DATE)), dateFrom);
  }
}
