package com.estefiturin.appgolosinas.controllers;

import com.estefiturin.appgolosinas.models.entities.*;
import com.estefiturin.appgolosinas.services.ClienteService;
import com.estefiturin.appgolosinas.services.DetallePedidoService;
import com.estefiturin.appgolosinas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final PedidoController pedidoController;
    private final PedidoService pedidoService;
    private final DetallePedidoService detallePedidoService;


    @Autowired
    public ClienteController(ClienteService clienteService, PedidoController pedidoController, PedidoService pedidoService,
                             DetallePedidoService detallePedidoService) {
        this.clienteService = clienteService;
        this.pedidoController = pedidoController;
        this.pedidoService = pedidoService;
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping
    public List<Cliente> list() {
        List<Cliente> clientesConPedidos = new ArrayList<>();

        // Obtener todos los clientes
        List<Cliente> clientes = clienteService.findAll();

        // Iterar sobre cada cliente
        for (Cliente cliente : clientes) {
            // Verificar si el cliente tiene pedidos asociados
            if (!cliente.getPedidos().isEmpty()) {
                // Agregar el cliente a la lista de clientes con pedidos
                clientesConPedidos.add(cliente);
            }
        }

        return clientesConPedidos;
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Cliente> userOptional = clienteService.findById(id);

        if(userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente user, BindingResult result,
                                    @PathVariable Long id) {

        if (result.hasErrors()){
            return validation(result);
        }
        Optional<Cliente> o = clienteService.update(user, id);

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/id")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<Cliente> o = clienteService.findById(id);

        if (o.isPresent()) {

            clienteService.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }

        return ResponseEntity.notFound().build();
    }



    @PostMapping("/crearClienteConPedido")
    public ResponseEntity<Cliente> crearClienteConPedido(@RequestBody Map<String, Object> requestBody) {
        // Crear el cliente
        Cliente cliente = new Cliente();
        cliente.setNombre((String) requestBody.get("nombre"));
        cliente.setAddress((String) requestBody.get("address"));
        cliente.setState((String) requestBody.get("state"));
        cliente.setPostCode((String) requestBody.get("postCode"));

        // Guardar el cliente
        Cliente clienteGuardado = clienteService.save(cliente);

        // Crear el pedido asociado al cliente recién creado
        Pedido pedido = new Pedido();
        pedido.setCliente(clienteGuardado);
        pedido.setEstado(EstadoPedido.PENDING);
        pedido.setFechaCreacion(new Date());
        Pedido pedidoGuardado = pedidoService.save(pedido);

        // Preparar la respuesta JSON
        Map<String, Object> response = new HashMap<>();
        response.put("cliente", clienteGuardado);
        response.put("pedido", pedidoGuardado);

        // Verificar si la creación del pedido fue exitosa
        if (pedidoGuardado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
        } else {
            // Manejar el caso en que la creación del pedido falló
            // Devolver un error indicando que la creación del cliente y el pedido falló
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<?> consultarEstadoPedidosCliente(@PathVariable Long clienteId) {
        // Verificar si el cliente con el clienteId existe
        Optional<Cliente> clienteOptional = clienteService.findById(clienteId);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún cliente con el ID proporcionado.");
        }

        // Obtener la lista de pedidos del cliente
        List<Pedido> pedidos = pedidoService.findByClienteId(clienteId);

        // Verificar si el cliente tiene pedidos
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("El cliente no tiene pedidos.");
        }

        // Crear una lista para almacenar los detalles de los pedidos con su estado
        List<Map<String, Object>> detallesPedidos = new ArrayList<>();

        // Iterar sobre cada pedido del cliente y obtener su estado
        for (Pedido pedido : pedidos) {
            Map<String, Object> detallePedido = new HashMap<>();
            detallePedido.put("id", pedido.getId());
            detallePedido.put("estado", pedido.getEstado().toString());
            detallesPedidos.add(detallePedido);
        }

        return ResponseEntity.ok(detallesPedidos);
    }




    private ResponseEntity<?> validation(BindingResult result) {

        // lugar para guardar el mje de error
        Map<String, String> errors = new HashMap<>();

        // errores
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
