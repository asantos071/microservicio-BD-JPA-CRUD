package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AgendaDao;
import com.model.Contacto;

@Service
public class AgendsServiceImpl implements AgendaService {

	@Autowired
	AgendaDao agendaDAO;

	@Override
	public boolean agregarContacto(Contacto contacto) {
		if (agendaDAO.recuperarContacto(contacto.getIdContacto()) == null) {
			agendaDAO.agregarContacto(contacto);
			return true;
		}
		return false;
	}

	@Override
	public List<Contacto> recuperarContactos() {
		return agendaDAO.devolverContactos();
	}

	@Override
	public void actualizarContacto(Contacto contacto) {
		if (agendaDAO.recuperarContacto(contacto.getIdContacto()) != null) {
			agendaDAO.actualizarContacto(contacto);
		}

	}

	@Override
	public boolean eliminarContacto(int idContacto) {
		if (agendaDAO.recuperarContacto(idContacto) != null) {
			agendaDAO.eliminarContacto(idContacto);
			return true;
		}
		return false;
	}

	@Override
	public Contacto buscarContacto(int idContacto) {
		return agendaDAO.recuperarContacto(idContacto);
	}

}
