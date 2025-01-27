package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CustomizedRestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpec;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class RestauranteRepositoryImpl implements CustomizedRestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    private final RestauranteRepository restauranteRepository;

    @Lazy
    public RestauranteRepositoryImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public List<Restaurante> findWithJPQL(String nome,
                                          BigDecimal taxaFreteInicial,
                                          BigDecimal taxaFreteFinal
    ) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");

        HashMap<String, Object> parametros = new HashMap<>();

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaFreteInicial ");
            parametros.put("taxaFreteInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFreteFinal");
            parametros.put("taxaFreteFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach(query::setParameter);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findWithCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasLength(nome))
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));

        if (taxaFreteInicial != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));

        if (taxaFreteFinal != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));

        criteria.where(predicates.toArray(predicates.toArray(new Predicate[0])));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurante> findWithFreeFrete(String nome) {
        return restauranteRepository.findAll(RestauranteSpec.comFreteGratis().and(RestauranteSpec.comNomeSemelhante(nome)));
    }
}
