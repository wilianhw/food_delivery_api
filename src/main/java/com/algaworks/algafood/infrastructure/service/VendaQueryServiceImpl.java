package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    public static final String DATA_CRIACAO = "dataCriacao";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        Root<Pedido> root = query.from(Pedido.class);

        Expression<LocalDate> functionDateDataCriacao = builder.function("date", LocalDate.class, root.get(DATA_CRIACAO));

        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getRestauranteId() != null)
            predicates.add(builder.equal(root.get("id"), filtro.getRestauranteId()));

        if (filtro.getDataCriacaoInicio() != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get(DATA_CRIACAO), filtro.getDataCriacaoInicio()));

        if (filtro.getDataCriacaoFim() != null)
            predicates.add(builder.lessThanOrEqualTo(root.get(DATA_CRIACAO), filtro.getDataCriacaoFim()));

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(selection);
        query.where(predicates.toArray(predicates.toArray(new Predicate[0])));
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
