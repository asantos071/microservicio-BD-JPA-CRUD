package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.model.Contacto;

public interface AgendaJPASpring extends JpaRepository<Contacto, Integer> {
	
	Contacto findByEmail (String email);
	
	
	/* Metodo modificado dentro de la capa de persistencia*/
	@Transactional
	@Modifying
	@Query("Delete from Contacto c Where c.email=?1")
	void eliminarPorEmail (String email);

}
