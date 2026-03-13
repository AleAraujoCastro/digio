package com.teste.digio.client;

import com.teste.digio.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "compra", url = "${api.compras}")
public interface ClientesComprasClient {

    @RequestMapping(method = RequestMethod.GET)
    List<ClienteDTO> buscarClientesECompras();

}