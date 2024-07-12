package com.leodelmiro.estabelecimento.adapters.in.controller.request;

import jakarta.validation.constraints.NotBlank;

public record CadastraProdutoImagemRequest(
        @NotBlank String nome,
        @NotBlank String url
) {
}