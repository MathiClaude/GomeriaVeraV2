package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {
    //public Usuario findByEmail(String email);
    public TipoDocumento findByIdTipoDocumento(Long idTipoDocumento);

}
