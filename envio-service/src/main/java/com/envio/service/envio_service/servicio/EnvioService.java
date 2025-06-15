package com.envio.service.envio_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.envio.service.envio_service.dto.BoletaDTO;
import com.envio.service.envio_service.entidades.Envio;
import com.envio.service.envio_service.repositorio.EnvioRepository;

@Service
public class EnvioService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> getAll(){
        return envioRepository.findAll();
    }

    public Envio getById(Long id){
        return envioRepository.findById(id).orElse(null);
    }

    public List<Envio> getByEstado(String estado){
        return envioRepository.findByEstado(estado);
    }

    public Envio save(Envio envio){
        return envioRepository.save(envio);
    }

    public void deleteById(Long id){
        envioRepository.deleteById(id);
    }

    public Envio actualizarEstado(Long id, String nuevoEstado){
        Envio envio = envioRepository.findById(id).orElse(null);
        if (envio != null) {
            envio.setEstado(nuevoEstado);
            return envioRepository.save(envio);
        }
        return null;
    }

    public BoletaDTO obtenerBoletaPorId(Long boletaId){
        String url = "http://localhost:8003/api/boletas/" + boletaId;
        return restTemplate.getForObject(url,BoletaDTO.class);
    }
}
