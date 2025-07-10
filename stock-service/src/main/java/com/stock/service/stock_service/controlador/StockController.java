package com.stock.service.stock_service.controlador;

import com.stock.service.stock_service.entidades.Stock;
import com.stock.service.stock_service.servicio.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/stock")
@Tag(name = "Stock Controller", description = "Controlador para gestionar stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    @Operation(summary = "Obtener todos los registros de stock")
    public ResponseEntity<CollectionModel<EntityModel<Stock>>> getAll() {
        List<EntityModel<Stock>> stocks = stockService.getAll().stream()
                .map(stock -> EntityModel.of(stock,
                        linkTo(methodOn(StockController.class).getById(stock.getId())).withSelfRel(),
                        linkTo(methodOn(StockController.class).getAll()).withRel("stocks")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(stocks,
                        linkTo(methodOn(StockController.class).getAll()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un stock por ID")
    public ResponseEntity<EntityModel<Stock>> getById(@PathVariable int id) {
        Stock stock = stockService.getStockById(id);
        if (stock != null) {
            return ResponseEntity.ok(
                    EntityModel.of(stock,
                            linkTo(methodOn(StockController.class).getById(id)).withSelfRel(),
                            linkTo(methodOn(StockController.class).getAll()).withRel("stocks"))
            );
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo stock")
    public ResponseEntity<EntityModel<Stock>> save(@RequestBody Stock stock) {
        Stock saved = stockService.save(stock);
        return ResponseEntity.ok(
                EntityModel.of(saved,
                        linkTo(methodOn(StockController.class).getById(saved.getId())).withSelfRel(),
                        linkTo(methodOn(StockController.class).getAll()).withRel("stocks"))
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un stock por ID")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        stockService.deleteStockById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener stock por ID de producto")
    public ResponseEntity<CollectionModel<EntityModel<Stock>>> byProductoId(@PathVariable int productoId) {
        List<EntityModel<Stock>> stocks = stockService.byProductoId(productoId).stream()
                .map(stock -> EntityModel.of(stock,
                        linkTo(methodOn(StockController.class).getById(stock.getId())).withSelfRel(),
                        linkTo(methodOn(StockController.class).byProductoId(productoId)).withSelfRel()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(stocks,
                        linkTo(methodOn(StockController.class).byProductoId(productoId)).withSelfRel())
        );
    }
}
