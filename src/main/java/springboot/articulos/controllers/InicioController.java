package springboot.articulos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springboot.articulos.utilidadesSetup.ServicioSetUp;


@Controller
public class InicioController {
	
	@Autowired
	private ServicioSetUp servicioSetUp;
	
	@Autowired
	private MessageSource mensajes;
	
	@RequestMapping()
	public String inicio() {
		servicioSetUp .prepararSetUp();
		//vamos a consulta el idioma de usario para dar un inicio u otro
		String idiomaActual = mensajes.getMessage("idioma", null, LocaleContextHolder.getLocale());
		System.out.println("idioma actual " + idiomaActual);
		return "inicio_" + idiomaActual;
	}
}
