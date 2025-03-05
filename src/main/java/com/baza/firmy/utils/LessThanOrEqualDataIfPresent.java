package com.baza.firmy.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.utils.Converter;
import net.kaczmarzyk.spring.data.jpa.utils.QueryContext;

public class LessThanOrEqualDataIfPresent<T> extends Equal<T> {

  public LessThanOrEqualDataIfPresent(
      QueryContext queryContext, String path, String[] httpParamValues, Converter converter) {
    super(queryContext, path, httpParamValues, converter);
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    LocalDate dateTimeTo;
    if (this.expectedValue.equals(Boolean.TRUE.toString())) {
      dateTimeTo = LocalDate.now();
    } else {
      dateTimeTo = DateUtil.convertStringToLocalDate(this.expectedValue);
    }
    return cb.lessThanOrEqualTo(
        cb.coalesce(new SpecificationUtil().getPathFromString(root, path), cb.literal(DateUtil.MIN_DATE)), dateTimeTo);
  }
}
