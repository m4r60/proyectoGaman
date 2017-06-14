package controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.DAOVariedadesImpl;
import modelos.Variedades;

@Controller
public class VariedadesControlador {

	@Autowired
	DAOVariedadesImpl daov;
	/********************************************************************************************************************/
	/*******************************VISTA LISTADO DE VARIEDADES*********************************************************/
	/*FUNCIONANDO*/
	
	/*Controlador para listar por defecto los distintos tipos de variedades*/
	@RequestMapping ("/listadoVariedades")
	public ModelAndView listadoVariedades (HttpSession sesion){
		List <Variedades>  listado = daov.listar();
		
		ModelAndView mv = new ModelAndView ("listadoVariedades");
		mv.addObject("listado", listado);
		return mv;
	}
	
	/*FUNCIONANDO*/
	/* filtrar las variedades por tipo*/
	@RequestMapping ("/filtroVariedadesTipo")
	public ModelAndView filtroVariedadesTipo (HttpSession sesion,
			@RequestParam (value = "buscar_variedad")String tipo){
		List <Variedades> listadoTipo = daov.listar(tipo); /*He tenido que añadir un método nuevo dentro de DAOVariedades*/
		
		ModelAndView mv = new ModelAndView ("listadoVariedades");

		mv.addObject("listado", listadoTipo);
		return mv;
	}
	
	/*No me deja eliminar una variedad cuando esta ha sido utilizada por una línea de albarán*/
	/*Sirve para eliminar un tipo de variedad*/
	@RequestMapping ("/eliminarVariedad")
	public ModelAndView eliminarVariedad (HttpSession sesion,
			@RequestParam (value = "tipo") String tipo){
		
		daov.delete(tipo);
		
		return listadoVariedades(sesion);
	}
	
	/*Editar una variedad*/
	@RequestMapping ("/editarVariedad")
	public ModelAndView editarVariedad (HttpSession sesion,
			@RequestParam (value = "tipo") String tipo){
		
		Variedades variedad = daov.read(tipo);
		ModelAndView mv = new ModelAndView ("editarVariedad");

		mv.addObject("variedad", variedad);
		return mv;
	}
	
	/*Crear una variedad*/
	@RequestMapping ("/nuevaVariedad")
	public ModelAndView nuevaVariedad (HttpSession sesion,
			@RequestParam (value = "tipo")String tipo){
		ModelAndView mv;
		Variedades variedad = daov.read(tipo);
		if (variedad == null){
			 mv = new ModelAndView ("nuevaVariedad"); // Si no existe la variedad mando a la vista de nueva variedad
			 mv.addObject("tipo", tipo); //Pasamos el nombre para que salga por defecto en el formulario. 
		} else {
			mv = filtroVariedadesTipo(sesion, tipo); //Si existe mando a listar otra vez con el filtro de tipo
		}
		
		return mv;
	}
	
	/********************************************************************************************************************/
	/*******************************VISTA EDITAR VARIEDAD*********************************************************/
	@RequestMapping ("/modificadaVariedad")
	public ModelAndView modificadaVariedad (HttpSession sesion,
			@RequestParam (value = "tipo") String tipo,
			@RequestParam (value = "precio_kg") double precioKg,
			@RequestParam (value = "peso_caja") double pesoCaja,
			@RequestParam (value = "precio_caja") double precioCaja){
	
		ModelAndView mv;
		Variedades variedad = new Variedades (tipo, precioKg, pesoCaja, precioCaja);
		boolean modificado = daov.update(variedad);
		if (modificado){
			System.out.println("La variedad ha sido modificada correctamente");
			mv = filtroVariedadesTipo (sesion, tipo);
		} else {
			System.out.println("La variedad no ha podido ser modificada");
			mv = editarVariedad (sesion, tipo);
		}

		return mv;
	}
	
	/********************************************************************************************************************/
	/*******************************VISTA CREAR VARIEDAD*********************************************************/
	/*Crear una variedad*/
	@RequestMapping ("/variedadCreada")
	public ModelAndView nuevaVariedad (HttpSession sesion,
			@RequestParam (value = "tipo")String tipo,
			@RequestParam (value = "precio_kg")double precioKg,
			@RequestParam (value = "peso_caja")double pesoCaja,
			@RequestParam (value = "precio_caja")double precioCaja){
		ModelAndView mv;
		Variedades variedad = new Variedades (tipo, precioKg, pesoCaja, precioCaja);
		
		boolean creado = daov.create(variedad);
		if (creado){
			mv = filtroVariedadesTipo(sesion, tipo);
		}else {
			mv = nuevaVariedad(sesion, tipo);
		}
		return mv;
	}
}
