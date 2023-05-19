package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

    public UnidadMedida findByIdUnidadMedida(Long idUnidadMedida);

}
