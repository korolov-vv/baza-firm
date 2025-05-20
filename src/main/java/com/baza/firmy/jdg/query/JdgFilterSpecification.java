package com.baza.firmy.jdg.query;

import jakarta.persistence.criteria.JoinType;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "pkdEntity", alias = "pkdEntity", type = JoinType.LEFT)
@Join(path = "adresEntityKorespondencyjny", alias = "a", type = JoinType.LEFT)
@Conjunction(value = {
    @Or ({
        @Spec (path = "pkdEntityGlowny.kod", params = "pkdEntity", paramSeparator = ',', spec = In.class),
        @Spec (path = "pkdEntity.kod", params = "pkdEntity", paramSeparator = ',', spec = In.class)
    })
}, and = {
    @Spec (path = "nazwa", params = "nazwa", spec = LikeIgnoreCase.class),
    @Spec (path = "dataRozpoczecia", params = "dataRozpoczeciaOd", spec = GreaterThanOrEqual.class),
    @Spec (path = "dataRozpoczecia", params = "dataRozpoczeciaDo", spec = LessThanOrEqual.class),
    @Spec (path = "createDate", params = "createDate", spec = GreaterThanOrEqual.class),
    @Spec (path = "status", params = "status", defaultVal = "AKTYWNY", spec = Equal.class),
    @Spec (path = "a.wojewodztwo", params = "wojewodztwo", spec = LikeIgnoreCase.class),
    @Spec (path = "a.powiat", params = "powiat", spec = LikeIgnoreCase.class),
    @Spec (path = "a.gmina", params = "gmina", spec = LikeIgnoreCase.class)
})
public interface JdgFilterSpecification extends Specification<JdgViewEntity> { }
