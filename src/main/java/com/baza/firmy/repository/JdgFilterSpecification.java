package com.baza.firmy.repository;

import com.baza.firmy.entity.Jdg;
import com.baza.firmy.utils.GreaterThanOrEqualDateIfPresent;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
    @Spec(path = "nazwa", params = "nazwa", spec = LikeIgnoreCase.class),
    @Spec(path = "pkdGlowny", params = "pkd", spec = LikeIgnoreCase.class),
    @Spec(path = "pkd", params = "pkd", paramSeparator = ',', spec = In.class),
    @Spec(path = "dataRozpoczecia", params = "dataRozpoczecia", spec = GreaterThanOrEqualDateIfPresent.class),
    @Spec(path = "status", params = "status", defaultVal = "AKTYWNY", spec = Equal.class),
    @Spec(path = "adresKorespondencyjny.wojewodztwo", params = "wojewodztwo", spec = LikeIgnoreCase.class),
    @Spec(path = "adresKorespondencyjny.wojewodztwo", params = "wojewodztwo", spec = LikeIgnoreCase.class),
    @Spec(path = "adresKorespondencyjny.powiat", params = "powiat", spec = LikeIgnoreCase.class),
    @Spec(path = "adresKorespondencyjny.gmina", params = "gmina", spec = LikeIgnoreCase.class),
  })
public interface JdgFilterSpecification extends Specification<Jdg> {}
