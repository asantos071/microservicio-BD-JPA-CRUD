package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.model.Contacto;

import com.service.AgendaService;

@CrossOrigin(origins = "*")
@RestController
public class ContactosController {

	@Autowired
	AgendaService agendaService;

	@GetMapping(value = "contactos", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Contacto> recuperarContactos() {
		return agendaService.recuperarContactos();
	}

	@GetMapping(value = "contactos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Contacto recuperarContacto(@PathVariable("id") int idContacto) {
		return agendaService.buscarContacto(idContacto);
	}

	@PostMapping(value = "contactos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_NDJSON_VALUE)
	public String guardarContacto(@RequestBody Contacto contacto) {
		return String.valueOf(agendaService.agregarContacto(contacto));
	}

	@PutMapping(value = "contactos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void actualizarContacto(@RequestBody Contacto contacto) {
		agendaService.actualizarContacto(contacto);
	}

	@DeleteMapping(value = "contactos/{id}")
	public void eliminarContacto(@PathVariable("id") int idContacto) {
		agendaService.eliminarContacto(idContacto);
	}

}
