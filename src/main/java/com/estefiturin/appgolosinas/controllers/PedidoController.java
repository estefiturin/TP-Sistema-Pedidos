package com.estefiturin.appgolosinas.controllers;

import com.estefiturin.appgolosinas.models.entities.*;
import com.estefiturin.appgolosinas.services.ClienteService;
import com.estefiturin.appgolosinas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;

    @Autowired
    public PedidoController(PedidoService pedidoService, ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
    }

    @GetMapping("/verPedidos")
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(pedidos);
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @PostMapping("/crearPedido")
    public ResponseEntity<String> crearPedidoConDetalle(@RequestBody Map<String, Object> requestBody) {
        // Verificar si los datos del cliente están presentes en el requestBody
        if (!requestBody.containsKey("nombre") || !requestBody.containsKey("state") || !requestBody.containsKey("address")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre, estado (state) y dirección (address) del cliente son necesarios para crear un pedido.");
        }

        // Obtener los datos del cliente del requestBody
        String nombre = (String) requestBody.get("nombre");
        String state = (String) requestBody.get("state");
        String address = (String) requestBody.get("address");

        // Crear el cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setState(state);
        cliente.setAddress(address);

        // Guardar el cliente en la base de datos
        Cliente clienteGuardado = clienteService.save(cliente);

        // Crear el pedido
        Pedido pedido = new Pedido();

        // Establecer el estado del pedido como PENDING
        pedido.setEstado(EstadoPedido.PENDING);

        // Establecer la fecha de creación como la fecha actual
        pedido.setFechaCreacion(new Date());

        // Asignar el cliente al pedido
        pedido.setCliente(clienteGuardado);

        // Generar un ID de pedido automático (puedes usar cualquier lógica para generar el ID)
        Long pedidoId = generarIdPedidoAutomatico();

        // Asignar el ID de pedido
        pedido.setId(pedidoId);

        Integer cantidad = (Integer) requestBody.get("cantidad");
        Double precioUnitario = (Double) requestBody.get("precioUnitario");
        // Obtener y establecer el detalle del pedido del requestBody
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setCantidad(cantidad);
        detallePedido.setPrecioUnitario(precioUnitario);
        Double montoTotal = cantidad * precioUnitario;
        pedido.setDetalle(detallePedido);


        // Guardar el pedido en la base de datos
        Pedido pedidoGuardado = pedidoService.save(pedido);

        // Crear un mapa para la respuesta JSON
        Map<String, Object> response = new HashMap<>();
        response.put("id", pedidoGuardado.getId());
        response.put("estado", pedidoGuardado.getEstado());
        response.put("montoTotal", montoTotal);
        response.put("mensaje", "Pedido creado exitosamente");


        return ResponseEntity.status(HttpStatus.CREATED).body(response.toString());
    }

    private Long generarIdPedidoAutomatico() {
        // Obtener el último ID de pedido de la base de datos
        Long ultimoIdPedido = pedidoService.obtenerUltimoIdPedido();

        // Si no hay pedidos en la base de datos, asignar el ID 1
        if (ultimoIdPedido == null) {
            return 1L;
        }

        // Sumar 1 al último ID de pedido para obtener el nuevo ID
        return ultimoIdPedido + 1;
    }

    @PutMapping("/{pedidoId}/estado")
    public ResponseEntity<String> cambiarEstadoPedido(@PathVariable Long pedidoId, @RequestParam String nuevoEstado) {
        // Verificar si el pedido con el pedidoId existe
        if (!pedidoService.existsById(pedidoId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún pedido con el ID proporcionado.");
        }
        EstadoPedido estadoActual = pedidoService.obtenerEstadoPedido(pedidoId);

        // Cambiar el estado del pedido
        pedidoService.actualizarEstadoPedido(pedidoId, nuevoEstado);

         // Mensaje de respuesta
        String mensaje = String.format("El estado del pedido con ID %d ha sido cambiado de %s a %s.", pedidoId, estadoActual, nuevoEstado);

        return ResponseEntity.ok(mensaje);
    }



}

