package com.leodelmiro.estabelecimento.core.usecase.pedido.impl;

import com.leodelmiro.estabelecimento.core.dataprovider.pedido.PagaPedidoGateway;
import com.leodelmiro.estabelecimento.core.usecase.pedido.BuscaPedidoUseCase;
import com.leodelmiro.estabelecimento.core.usecase.pedido.PagaPedidoUseCase;

import java.time.LocalDateTime;


public class PagaPedidoUseCaseImpl implements PagaPedidoUseCase {

    private final PagaPedidoGateway pagaPedidoGateway;
    private final BuscaPedidoUseCase buscaPedidoUseCase;

    public PagaPedidoUseCaseImpl(PagaPedidoGateway pagaPedidoGateway, BuscaPedidoUseCase buscaPedidoUseCase) {
        this.pagaPedidoGateway = pagaPedidoGateway;
        this.buscaPedidoUseCase = buscaPedidoUseCase;
    }

    @Override
    public void pagar(Long idPedido, LocalDateTime pagoEm) {
        var pedido = buscaPedidoUseCase.buscar(idPedido);
        pedido.setPagoEm(pagoEm);
        pedido.avancarStatus();
        pagaPedidoGateway.pagar(pedido);
    }
}