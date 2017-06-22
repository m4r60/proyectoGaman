package controladores;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Utils.DateUtils;
import dao.DAOAlbaranSalidaImpl;
import dao.DAOClienteImpl;
import dao.DAOLineaAlbaranSalidaImpl;
import dao.DAOPersonaImpl;
import dao.DAOVariedadesImpl;
import modelos.AlbaranSalida;
import modelos.Cliente;
import modelos.LineaAlbaranSalida;
import modelos.Persona;
import modelos.Variedades;

@Controller
public class AlbaranesSalidaControlador {

	@Autowired
	DAOAlbaranSalidaImpl daoas;
	
	@Autowired
	DAOPersonaImpl daop;
	
	@Autowired
	DAOClienteImpl daoc;
	
	@Autowired
	DAOLineaAlbaranSalidaImpl daol;
	
	@Autowired
	DAOVariedadesImpl daov;
	
	
	
	
/*********************************************************************************************************/
/****************VISTA LISTADO ALBARAN SALIDA*****************/
	
	/*FUNCIONANDO*/
	/*Función para mostrar el listado de albaran.
	 * Por defecto, la lista que muestra son los albarances con n_factura = null.*/
	@RequestMapping("/listarAlbaranSalida")
	  public ModelAndView listarAlbaranSalida(HttpSession sesion){
		ModelAndView mv = new ModelAndView("listarAlbaranSalida");
		List <AlbaranSalida> listado = daoas.listar();
		
		Persona p = null; 
		double precio = 0.0;
		for (int i = 0; i<listado.size();i++){ /*Con este bucle recorro el list
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada línea.
		Para ello, en modelos de albaran salida he creado una propiedad cifnif y otra precio Neto. Asimismo, calculo el precio
		de cada albaran y lo asigno a cada albaran de salida en su propiedad precio Neto */

			p = daop.readAlbaranSalida(listado.get(i).getnCliente()); 
			listado.get(i).setCifNif(p.getCifNif());  //Asigno el cifNif a cada albaran salida
			
			precio = daoas.calcularPrecio(listado.get(i).getnAlbaran());
			listado.get(i).setPrecioNeto(precio); //Asigno el precio neto
			
			Date fecha = listado.get(i).getFecha();
			String fechaStr = DateUtils.formatearFecha(fecha);
			listado.get(i).setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
			
			
		}
		
		mv.addObject("listado", listado);
 
    	return mv;
    }
	
	/*Filtros*/
	
	/*FUNCIONADO*/
	/*Filtro por cifNif*/
	@RequestMapping("/FiltroAlbaranSalidaCifNif")
	  public ModelAndView filtroAlbaranSalidaCifNif(HttpSession sesion,
			  @RequestParam (value="cif_nif") String cifNif){
		
		ModelAndView mv = new ModelAndView("listarAlbaranSalida");
		List <AlbaranSalida> listado = daoas.listar(cifNif);
		
		Persona p = null; 
		double precio = 0.0;
		for (int i = 0; i<listado.size();i++){ /*Con este bucle recorro el list
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada línea.
		Para ello en modelos de albaran salida he creado una propiedad cifnif. Asimismo calculo el precio
		de cada albaran y lo asigno a cada línea*/

			p = daop.readAlbaranSalida(listado.get(i).getnCliente()); 
			listado.get(i).setCifNif(p.getCifNif()); 
			
			precio = daoas.calcularPrecio(listado.get(i).getnAlbaran());
			listado.get(i).setPrecioNeto(precio); //Asigno el precio neto
			
			Date fecha = listado.get(i).getFecha();
			String fechaStr = DateUtils.formatearFecha(fecha);
			listado.get(i).setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
		}
		
		mv.addObject("listado", listado);
	
  	return mv;
  }
	
	/*FUNCIONANDO*/
	/*Filtro por Fecha -- Hay que revisar el datePicker y el dateUtils*/

	@RequestMapping("/FiltroAlbaranSalidaFecha")
	  public ModelAndView filtroAlbaranSalidaFecha(HttpSession sesion,
			  @RequestParam (value="fecha_inicio") String fechaInicio,
			  @RequestParam (value = "fecha_final") String fechaFinal){
		
		Date fi = DateUtils.parseFormato(fechaInicio);
		Date ff = DateUtils.parseFormato(fechaFinal);
		
		/*Creo dos variables una para asignar la fecha mayor y otra asignar la fecha menor*/
		/*Luego las asigno a fechaAnterior y posterior para que no de error. El error sería introducir 
		 * una fecha mayor en el primer campo y una fecha inferior en el segundo. Así daría igual en que
		 *  campo fuera la fecha mayor y la menor*/
		Date fechaAnterior = fi;
		Date fechaPosterior = ff;
		int comparar = ff.compareTo(fi); //Compara las dos fechas y devuelve un int que si es mayor de 0 quiere decir que ff<fi
		if (comparar<0){
			fechaAnterior = ff;
			fechaPosterior = fi;
		}
		ModelAndView mv = new ModelAndView("listarAlbaranSalida");
		List <AlbaranSalida> listado = daoas.buscarFecha(fechaAnterior, fechaPosterior); 
		
		Persona p = null; 
		double precio = 0.0;
		for (int i = 0; i<listado.size();i++){ /*Con este bucle recorro el list
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada línea.
		Para ello en modelos de albaran salida he creado una propiedad cifnif. Asimismo calculo el precio
		de cada albaran y lo asigno a cada línea*/

			p = daop.readAlbaranSalida(listado.get(i).getnCliente());
			listado.get(i).setCifNif(p.getCifNif()); 
			
			precio = daoas.calcularPrecio(listado.get(i).getnAlbaran());
			listado.get(i).setPrecioNeto(precio); //Asigno el precio neto
			
			Date fecha = listado.get(i).getFecha();
			String fechaStr = DateUtils.formatearFecha(fecha);
			listado.get(i).setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
		}
		
		
		mv.addObject("listado", listado);
		
  	return mv;
  }
	
	/*FUNCIONANDO*/
	
	/*Filtro por nAlbaran*/
	@RequestMapping("/FiltroAlbaranSalidanAlbaran")
	public ModelAndView filtroAlbaranSalidanAlbaran(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran){
		
		ModelAndView mv = new ModelAndView("listarAlbaranSalida");
		List <AlbaranSalida> listado = new ArrayList <AlbaranSalida>();
		AlbaranSalida albaranSalida = daoas.read(nAlbaran);
		
		Persona p = daop.readAlbaranSalida(albaranSalida.getnCliente());
		albaranSalida.setCifNif(p.getCifNif()); //Asigno el cifNif para la tabla
		
		double precio = daoas.calcularPrecio(albaranSalida.getnAlbaran());
		albaranSalida.setPrecioNeto(precio); //Asigno el precio neto
		
		Date fecha = albaranSalida.getFecha();
		String fechaStr = DateUtils.formatearFecha(fecha);
		albaranSalida.setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
		
		
		listado.add(albaranSalida);
		mv.addObject("listado", listado);
	
	return mv;
	}
	
	/*FUNCIONANDO*/
	
	/*Modificar AlbaranSalida*/ 
	@RequestMapping("/ModificarAlbaranSalida")
	public ModelAndView modificarAlbaranSalida(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran){ //Al darle a modificar recuperamos nAlbaran
	
		ModelAndView mv = new ModelAndView("editarAlbaranSalida");
		AlbaranSalida albaranSalida = daoas.read(nAlbaran); //Recuperamos el objeto albaran
		
		/*Recuperamos la fecha para formatearla y para devolverla como string formateada.*/
		Date fecha = albaranSalida.getFecha();
		String fechaFormateada = DateUtils.formatearFecha(fecha);
		albaranSalida.setFechaStr(fechaFormateada);
		
		Persona p = daop.readAlbaranSalida(albaranSalida.getnCliente()); //Recuperamos el objeto persona asociado a ese albaran
		
		
		List <LineaAlbaranSalida> listadoLineas = daol.listar(nAlbaran); //Recuperamos un List con el listado de líneas asociado a ese albarán
		
		/*Asignación el precioTotal de linea a cada línea*/
		Double precioLinea = 0.0;
		for (int i = 0; i < listadoLineas.size(); i++){ //Para poder asignar el precioTotal a cada una de las líneas he tenido que crear una propiedad en el constructor llamada PrecioTotal
			precioLinea = daoas.calcularPrecioUnaLinea(listadoLineas.get(i).getIdLinea()); //Calculo el precio total de una línea concreta
			listadoLineas.get(i).setPrecioTotal(precioLinea); //A cada una de las líneas le asigno el precio total de esa línea
		}
		/*Calculo del precio total de todas las líneas*/
		Double precioTotal = daoas.calcularPrecio(nAlbaran); //Calculamos el precio total de todas las líneas de albarán con nAlbaran de ?;
		
		/*También tengo que mandar los tipos de variedades que existen*/
		List <Variedades> listadoTipos = daov.listar();
		
		mv.addObject("listadoTipos", listadoTipos); // Envío las variedades disponibles para que aparezcan en añadir línea de albarán
		mv.addObject("precioTotal", precioTotal); //Devuelvo el precio total de ese albarán
		mv.addObject("listadoLineas", listadoLineas); //Devolvemos un listado con todas las líneas de albarán.
		mv.addObject("albaranSalida", albaranSalida); //Pasamos un objeto albaran salida que rellenará por defecto el formulario de modificar
		mv.addObject("persona", p); //Pasamos un objeto persona para que muestre los datos del cliente

	return mv;
	}
	
	/*FUNCIONANDO*/
	
	/*Eliminar AlbaranSalida*/
	@RequestMapping("/EliminarAlbaranSalida")
	public ModelAndView eliminarAlbaranSalida(HttpSession sesion,
			  @RequestParam (value="nAlbaran") int nAlbaran){ //Recupero el número de albarán
		daoas.delete(nAlbaran); //Con ese número de albarán lo borro, siempre y cuando no esté facturado.
		ModelAndView mv = listarAlbaranSalida(sesion);

	return mv;
	}
	
	/*Nuevo AlbaranSalida*/
	@RequestMapping("/nuevoAlbaranSalida")
	public ModelAndView nuevoAlbaranSalida(HttpSession sesion,
			  @RequestParam (value="cif_nif") String cifNif){
		
		Date diaActual = new Date();
		//Primero metemos una ventana modal que nos pida el cifNif para recuperar persona.
		ModelAndView mv = listarAlbaranSalida (sesion);
		Persona p = daop.read(cifNif);
		if (p==null){
			//Persona no existe
			System.out.println("No se puede crear albaran porque no existe persona");
			/*Nos manda a la vista listarAlbaran por defecto*/
		}else {
			//Persona existe
			Cliente c = daoc.read(p.getIdPersona());
			if (c==null){
				//Cliente no existe
				System.out.println("No se puede crear albaran porque no existe cliente");
			} else {
				//Cliente existe
				System.out.println("El cliente existe, por lo tanto, se puede crear albaran");
				
				List <Variedades> listadoVariedades = daov.listar();
				
				AlbaranSalida albaranSalida = new AlbaranSalida(-1, c.getnCliente(),diaActual, 0);
				boolean creado = daoas.create(albaranSalida);
				if (creado){
					mv = new ModelAndView ("editarAlbaranSalida");
					
					Date fecha = albaranSalida.getFecha();
					String fechaStr = DateUtils.formatearFecha(fecha);
					albaranSalida.setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
					
					mv.addObject("albaranSalida", albaranSalida); /*Si ponemos los mv.addObject por encima del mv no me los coge. Los coge la vista anterior*/
					mv.addObject("listadoTipos", listadoVariedades);
					mv.addObject("persona", p); //Si existe persona y cliente le paso el objeto a la vista
				} else {
					System.out.println("Nuevo Albarán - Error al crear el albarán de salida");
				}
				
				
			}
		}
		

	return mv;
	}
	
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	/****************VISTA MODIFICAR ALBARAN SALIDA*****************/
	/*FUNCIONANDO*/
	/****************VISTA CREAR ALBARAN SALIDA*****************/ /*También sirve para esta vista*/
	/*BOTÓN DE ENVIAR PARA LAS MODIFICACIONES*/
	@RequestMapping ("/modificadoAlbaranSalida")
	public ModelAndView modificadoAlbaranSalida(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran,
			  @RequestParam (value ="n_cliente") int nCliente,
			  @RequestParam (value = "fecha") String fecha,
			  @RequestParam (value = "n_factura") int nFactura) {
		
		Date fechaDate = DateUtils.parseFormato(fecha); /*Recibo un String, lo paso a tipo Date y lo incluyo en el objeto para hacer el update*/
		AlbaranSalida albaranSalida = new AlbaranSalida (nAlbaran, nCliente, fechaDate, nFactura);
		ModelAndView mv = modificarAlbaranSalida(sesion, nAlbaran); /*CUIDADO!!!!!!*/
		
			boolean modificado = daoas.update(albaranSalida);
			if (!modificado){
				//Mandamos otra vez a la vista de modificarAlbaran, con el mismo albaran
				System.out.println("No se ha podido modificar el albarán de salida");
			}else{
				mv = listarAlbaranSalida(sesion);
				System.out.println("Se ha modificado el albarán de salida");
			}
		
	return mv;
	}
	
	/*FUNCIONANDO*/
	/*Borrar una línea del acumulador*/
	@RequestMapping ("/eliminarAculumador")
	public ModelAndView eliminarAcumulador(HttpSession sesion,
			  @RequestParam (value="id_linea") int idLinea,
			  @RequestParam (value = "n_albaran") int nAlbaran) { //Recupero el idLinea de la línea que quiero borrar
	
	
		daol.delete(idLinea); //Borro la línea.
		ModelAndView mv = modificarAlbaranSalida(sesion, nAlbaran);

	return mv;
	}
	
	/*FUNCIONANDO*/
	/*Añadir línea de albarán*/
	@RequestMapping ("/addAcumulador")
	public ModelAndView addAcumulador(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran,
			  @RequestParam (value = "tipoCereza") String tipo,
			  @RequestParam (value = "n_cajas")int nCajas) { //Te tendría que poner el precio de caja por defecto al seleccionar un tipo de variedad//Javascript
		
		Variedades v = daov.read(tipo);
		LineaAlbaranSalida las = new LineaAlbaranSalida (nAlbaran, -1 ,tipo, nCajas, v.getPesoCaja(), v.getPrecioCaja());
		
		boolean lineaCreada = daol.create(las); //Creas una línea y compruebas con el boolean
		if (!lineaCreada){
			System.out.println("No se ha podido crear la línea de albaran salida");
		} else {
			System.out.println("La línea de albaran salida ha sido creada");
		}
		ModelAndView mv =  modificarAlbaranSalida(sesion,nAlbaran); /*Mandas otra vez a la vista de modificarAlbaran para que prosiga asignando líneas o no*/
		/*Cuando añadimos una línea también tendríamos que enviar información de los campos que ha modificado el cliente como puede ser fecha*/
		return mv;
	}
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	
	
}
