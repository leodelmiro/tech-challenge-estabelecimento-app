package com.leodelmiro.estabelecimento.dataprovider.gateway.pedido;

import com.leodelmiro.estabelecimento.dataprovider.repository.PedidoRepository;
import com.leodelmiro.estabelecimento.dataprovider.repository.entity.ItemPedidoEntity;
import com.leodelmiro.estabelecimento.dataprovider.repository.mapper.ItemPedidoEntityMapper;
import com.leodelmiro.estabelecimento.dataprovider.repository.mapper.PedidoEntityMapper;
import com.leodelmiro.estabelecimento.core.domain.Pedido;
import com.leodelmiro.estabelecimento.core.dataprovider.pedido.RemoveProdutoPedidoGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemoveProdutoPedidoGatewayImpl implements RemoveProdutoPedidoGateway {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoEntityMapper pedidoEntityMapper;

    @Autowired
    private ItemPedidoEntityMapper itemPedidoEntityMapper;

    @Override
    public Pedido remover(Pedido pedido) {
        var pedidoEntity = pedidoEntityMapper.toPedidoEntity(pedido);
        List<ItemPedidoEntity> itemPedidoEntities = pedido.getItens().stream()
                .map(item -> {
                    var itemPedidoEntity = itemPedidoEntityMapper.toItemPedidoEntity(item);
                    itemPedidoEntity.setPedido(pedidoEntity);
                    return itemPedidoEntity;
                })
                .toList();

        pedidoEntity.getItens().clear();
        pedidoEntity.addItens(itemPedidoEntities);
        var pedidoAtualizado = pedidoRepository.save(pedidoEntity);
        return pedidoEntityMapper.toPedido(pedidoAtualizado);
    }
}