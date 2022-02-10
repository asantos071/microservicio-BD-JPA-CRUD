package com.service;

import java.util.List;

import com.model.Contacto;

public interface AgendaService {

	void agregarContacto(Contacto contacto) throws Exception;

	List<Contacto> recuperarContactos();

	void actualizarContacto(Contacto contacto);

	boolean eliminarContacto(int idContacto);

	Contacto buscarContacto(int idContacto);

}
