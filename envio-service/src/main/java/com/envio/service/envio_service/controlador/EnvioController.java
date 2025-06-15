package com.envio.service.envio_service.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.envio.service.envio_service.dto.BoletaDTO;
import com.envio.service.envio_service.entidades.Envio;
import com.envio.service.envio_service.servicio.EnvioService;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> listarEnvios(){
        List<Envio> envios = envioService.getAll();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerEnvio(@PathVariable Long id){
        Envio envio = envioService.getById(id);
        if (envio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envio);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envio>> obtenerPorEstado(@PathVariable String estado){
        return ResponseEntity.ok(envioService.getByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Envio> crearEnvio(@RequestBody Envio envio){
        return ResponseEntity.ok(envioService.save(envio));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Envio> actualizarEstado(@PathVariable Long id, @RequestParam String estado){
        Envio actualizado = envioService.actualizarEstado(id, estado);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        envioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/externo/boleta/{id}")
    public BoletaDTO obtenerBoleta(@PathVariable Long id){
        return envioService.obtenerBoletaPorId(id);
    }
}
