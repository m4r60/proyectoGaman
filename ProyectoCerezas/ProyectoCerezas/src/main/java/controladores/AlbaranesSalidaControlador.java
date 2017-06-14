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
	/*Funci�n para mostrar el listado de albaran.
	 * Por defecto, la lista que muestra son los albarances con n_factura = null.*/
	@RequestMapping("/listarAlbaranSalida")
	  public ModelAndView listarAlbaranSalida(HttpSession sesion){
		ModelAndView mv = new ModelAndView("listarAlbaranSalida");
		List <AlbaranSalida> listado = daoas.listar();
		
		Persona p = null; 
		double precio = 0.0;
		for (int i = 0; i<listado.size();i++){ /*Con este bucle recorro el list
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada l�nea.
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
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada l�nea.
		Para ello en modelos de albaran salida he creado una propiedad cifnif. Asimismo calculo el precio
		de cada albaran y lo asigno a cada l�nea*/

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
		/*Luego las asigno a fechaAnterior y posterior para que no de error. El error ser�a introducir 
		 * una fecha mayor en el primer campo y una fecha inferior en el segundo. As� dar�a igual en que
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
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada l�nea.
		Para ello en modelos de albaran salida he creado una propiedad cifnif. Asimismo calculo el precio
		de cada albaran y lo asigno a cada l�nea*/

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
		
		
		List <LineaAlbaranSalida> listadoLineas = daol.listar(nAlbaran); //Recuperamos un List con el listado de l�neas asociado a ese albar�n
		
		/*Asignaci�n el precioTotal de linea a cada l�nea*/
		Double precioLinea = 0.0;
		for (int i = 0; i < listadoLineas.size(); i++){ //Para poder asignar el precioTotal a cada una de las l�neas he tenido que crear una propiedad en el constructor llamada PrecioTotal
			precioLinea = daoas.calcularPrecioUnaLinea(listadoLineas.get(i).getIdLinea()); //Calculo el precio total de una l�nea concreta
			listadoLineas.get(i).setPrecioTotal(precioLinea); //A cada una de las l�neas le asigno el precio total de esa l�nea
		}
		/*Calculo del precio total de todas las l�neas*/
		Double precioTotal = daoas.calcularPrecio(nAlbaran); //Calculamos el precio total de todas las l�neas de albar�n con nAlbaran de ?;
		
		/*Tambi�n tengo que mandar los tipos de variedades que existen*/
		List <Variedades> listadoTipos = daov.listar();
		
		mv.addObject("listadoTipos", listadoTipos); // Env�o las variedades disponibles para que aparezcan en a�adir l�nea de albar�n
		mv.addObject("precioTotal", precioTotal); //Devuelvo el precio total de ese albar�n
		mv.addObject("listadoLineas", listadoLineas); //Devolvemos un listado con todas las l�neas de albar�n.
		mv.addObject("albaranSalida", albaranSalida); //Pasamos un objeto albaran salida que rellenar� por defecto el formulario de modificar
		mv.addObject("persona", p); //Pasamos un objeto persona para que muestre los datos del cliente

	return mv;
	}
	
	/*FUNCIONANDO*/
	
	/*Eliminar AlbaranSalida*/
	@RequestMapping("/EliminarAlbaranSalida")
	public ModelAndView eliminarAlbaranSalida(HttpSession sesion,
			  @RequestParam (value="nAlbaran") int nAlbaran){ //Recupero el n�mero de albar�n
		daoas.delete(nAlbaran); //Con ese n�mero de albar�n lo borro, siempre y cuando no est� facturado.
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
					System.out.println("Nuevo Albar�n - Error al crear el albar�n de salida");
				}
				
				
			}
		}
		

	return mv;
	}
	
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	/****************VISTA MODIFICAR ALBARAN SALIDA*****************/
	/*FUNCIONANDO*/
	/****************VISTA CREAR ALBARAN SALIDA*****************/ /*Tambi�n sirve para esta vista*/
	/*BOT�N DE ENVIAR PARA LAS MODIFICACIONES*/
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
				System.out.println("No se ha podido modificar el albar�n de salida");
			}else{
				mv = listarAlbaranSalida(sesion);
				System.out.println("Se ha modificado el albar�n de salida");
			}
		
	return mv;
	}
	
	/*FUNCIONANDO*/
	/*Borrar una l�nea del acumulador*/
	@RequestMapping ("/eliminarAculumador")
	public ModelAndView eliminarAcumulador(HttpSession sesion,
			  @RequestParam (value="id_linea") int idLinea,
			  @RequestParam (value = "n_albaran") int nAlbaran) { //Recupero el idLinea de la l�nea que quiero borrar
	
	
		daol.delete(idLinea); //Borro la l�nea.
		ModelAndView mv = modificarAlbaranSalida(sesion, nAlbaran);

	return mv;
	}
	
	/*FUNCIONANDO*/
	/*A�adir l�nea de albar�n*/
	@RequestMapping ("/addAcumulador")
	public ModelAndView addAcumulador(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran,
			  @RequestParam (value = "tipoCereza") String tipo,
			  @RequestParam (value = "n_cajas")int nCajas,
			  @RequestParam (value = "peso_caja")double pesoCaja, //Te tendr�a que poner el peso de caja por defecto al seleccionar un tipo de variedad//Javascript
			  @RequestParam (value = "precio_caja")double precioCaja) { //Te tendr�a que poner el precio de caja por defecto al seleccionar un tipo de variedad//Javascript
		
		LineaAlbaranSalida las = new LineaAlbaranSalida (nAlbaran, -1 ,tipo, nCajas, pesoCaja, precioCaja);
		
		boolean lineaCreada = daol.create(las); //Creas una l�nea y compruebas con el boolean
		if (!lineaCreada){
			System.out.println("No se ha podido crear la l�nea de albaran salida");
		} else {
			System.out.println("La l�nea de albaran salida ha sido creada");
		}
		ModelAndView mv =  modificarAlbaranSalida(sesion,nAlbaran); /*Mandas otra vez a la vista de modificarAlbaran para que prosiga asignando l�neas o no*/
		/*Cuando a�adimos una l�nea tambi�n tendr�amos que enviar informaci�n de los campos que ha modificado el cliente como puede ser fecha*/
		return mv;
	}
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	
	
}
