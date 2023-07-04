package com.proyecto.is2.proyecto.repository;

import com.proyecto.is2.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import javax.persistence.Tuple;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByEmail(String email);
    public Usuario findByIdUsuario(Long idUsuario);
    
     // reporte del gráfico POR CAJERO
    @Query(value="SELECT id_usuario,u.username,rol_id,email "+
                 "FROM usuario u ",nativeQuery = true)
    List<Tuple> findUsuariosNative();

}
