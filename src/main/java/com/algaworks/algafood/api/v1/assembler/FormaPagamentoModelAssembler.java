package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public FormaPagamentoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(FormaPagamentoController.class, FormaPagamentoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoModel);

        if (algaSecurity.podeConsultarFormasPagamento()) {
            formaPagamentoModel.add(algaLinks.linkToFormaPagamento("formas-pagamento"));
        }

        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarFormasPagamento()) {
            collectionModel.add(algaLinks.linkToFormaPagamento("formas-pagamento"));
        }

        return collectionModel;
    }
}
