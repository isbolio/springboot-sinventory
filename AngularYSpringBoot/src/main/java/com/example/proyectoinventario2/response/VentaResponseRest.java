package com.example.proyectoinventario2.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaResponseRest extends ResponseRest {
    private VentaResponse ventaResponse = new VentaResponse();
}