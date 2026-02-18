package com.baza.firmy.firmycrm.query;

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

@Join(path = "firmaCrm", alias = "firmaCrm", type = JoinType.LEFT)
@Join(path = "firmaCrm.pkd", alias = "pkd", type = JoinType.LEFT)
@Join(path = "firmaCrm.adresKorespondencyjny", alias = "a", type = JoinType.LEFT)
@Conjunction(value = {
    @Or ({
        @Spec (path = "firmaCrm.pkdGlowny.kod", params = "pkd", paramSeparator = ',', spec = In.class),
        @Spec (path = "pkd.kod", params = "pkd", paramSeparator = ',', spec = In.class)
    })
}, and = {
    @Spec (path = "firmaCrm.nazwa", params = "nazwa", spec = LikeIgnoreCase.class),
    @Spec (path = "firmaCrm.dataRozpoczecia", params = "dataRozpoczeciaOd", spec = GreaterThanOrEqual.class),
    @Spec (path = "firmaCrm.dataRozpoczecia", params = "dataRozpoczeciaDo", spec = LessThanOrEqual.class),
    @Spec (path = "firmaCrm.createDate", params = "createDate", spec = GreaterThanOrEqual.class),
    @Spec (path = "firmaCrm.status", params = "status", defaultVal = "AKTYWNY", spec = Equal.class),
    @Spec (path = "a.wojewodztwo", params = "wojewodztwo", spec = LikeIgnoreCase.class),
    @Spec (path = "a.powiat", params = "powiat", spec = LikeIgnoreCase.class),
    @Spec (path = "a.gmina", params = "gmina", spec = LikeIgnoreCase.class)
})
public interface FirmyCrmFilterSpecification extends Specification<FirmaCrmViewEntity> { }
