package controladores;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.DAOAlbaranSalida;
import dao.DAOFacturaSalida;
import dao.DAOLineaAlbaranSalida;
import dao.DAOPersona;
import modelos.AlbaranSalida;
import modelos.FacturaSalida;
import modelos.LineaAlbaranSalida;
import modelos.Persona;
import Utils.DateUtils;

@Controller
public class FacturaSalidaControlador {
	
	@Autowired
	DAOPersona daop;
	
	@Autowired
	DAOFacturaSalida daof;
	
	@Autowired
	DAOAlbaranSalida daoal;
	
	@Autowired
	DAOLineaAlbaranSalida daola;
	
	@RequestMapping("/listarFacturaSalida")
	public ModelAndView listarFacturaSalida(HttpSession sesion){
		
		ModelAndView mv = new ModelAndView("listarFacturasSalida");
		
		List<FacturaSalida> listaFac = daof.listar();
		
		//Recupero en cada factura el Dni que lleva vinculado el Albaran que tiene esta factura
		//Calculo el precio total de cada factura
		
		for(FacturaSalida f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
			
		}
				
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
		
		return mv;
	}
	
	@RequestMapping("/FiltroFacturaSalidaFecha")
	public ModelAndView filtroFacturaSalidaFecha(HttpSession sesion,
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
		
		ModelAndView mv = new ModelAndView("listarFacturasSalida");
		List<FacturaSalida> listaFac = daof.buscarFecha(fechaAnterior, fechaPosterior);
		
		for(FacturaSalida f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
		}
				
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
		
		return mv;
	}
	
	@RequestMapping("/FiltroFacturaSalidaCifNif")
	public ModelAndView filtroFacturaSalidaCifNif(HttpSession sesion,
			  @RequestParam (value="cif_nif") String cifNif){
		
		ModelAndView mv = new ModelAndView("listarFacturasSalida");
		List<FacturaSalida> listaFac = daof.listar(cifNif);
		
		/*Recupero en cada factura el Dni que lleva vinculado el Albaran 
		 * que tiene esta factura y calculo el precio total de cada factura
		 */
		
		for(FacturaSalida f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
			
		}
		
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
				
		return mv;
	}
	
	
	@RequestMapping("/FiltroFacturaSalidanFactura")
	public ModelAndView filtroFacturaSalidanFactura(HttpSession sesion,
			  @RequestParam (value="n_factura") int nFactura){
		
		ModelAndView mv = new ModelAndView("listarFacturasSalida");
		List<FacturaSalida> listaFac = daof.listar(nFactura);
		
		/*Recupero en cada factura el Dni que lleva vinculado el Albaran 
		 * que tiene esta factura y calculo el precio total de cada factura
		 */
		
		for(FacturaSalida f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
			
		}
		
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
				
		return mv;
	}
	
	/*Modificar FacturaSalida*/ 
	@RequestMapping("/ModificarFacturaSalida")
	public ModelAndView modificarFacturaSalida(HttpSession sesion,
			  @RequestParam (value="n_factura") int nFactura){ //Al darle a modificar recuperamos nFactura
	
		ModelAndView mv = new ModelAndView("editarFacturaSalida");
		FacturaSalida fs=daof.readConDetalles(nFactura); //Recuperamos el objeto Factura con la lista de albaranes que tiene
		
		daof.meterDni(fs);
		Double precio = fs.getPrecioNeto()+((fs.getPrecioNeto()*fs.getIva())/100);
		fs.setPrecioTotal(precio);
		
		for(AlbaranSalida al:fs.getAlbaranes()){
			//Meto el precio neto de cada albaran de la lista
			Double precioAl = daoal.calcularPrecio(al.getnAlbaran());
			al.setPrecioNeto(precioAl);	
			
			//Meto el precio en cada linea de cada albaran
			for(LineaAlbaranSalida li:al.getLineas()){
				li.setPrecioTotal(li.getnCajas()*li.getPrecioCaja());	
			}
			
		}
		
		//Recupero los datos de la persona
		Persona p=daop.read(fs.getCifnif());
				
		//Recupero una lista de albaranes pendientes de facturar
		List<AlbaranSalida> listaPendiente = daoal.listarPendientes(fs.getCifnif());
		
		//Meto el precio neto de cada albaran de la lista
		for(AlbaranSalida al:listaPendiente){
			Double precioAl = daoal.calcularPrecio(al.getnAlbaran());
			al.setPrecioNeto(precioAl);	
		}
				
		mv.addObject("cliente", p);
		mv.addObject("factura", fs);
		mv.addObject("listaPendiente", listaPendiente);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
				
		return mv;
	}
		
	/*Eliminar FacturaSalida*/
	@RequestMapping("/EliminarFacturaSalida")
	public ModelAndView eliminarFacturaSalida(HttpSession sesion,
			  @RequestParam (value="n_factura") int nFactura){ //Recibo el nFactura de la factura que quiero borrar
		//Anulo la factura
		daof.anularFactura(nFactura);
		
		//Creo una factura analoga a la anulada con la fecha de hoy y precio neto negativo
		FacturaSalida fac=daof.readConDetalles(nFactura);
		FacturaSalida f=new FacturaSalida(-1,new Date(),fac.getIva(),-(fac.getPrecioNeto()),false);
		daof.create(f);
		daof.anularFactura(f.getnFactura());
		
		//Por cada albaran que contiene una factura a anular, creo un albaran analogo con precio negativo y nFactura de la factura anulada
		for(AlbaranSalida al:fac.getAlbaranes()){
			AlbaranSalida b=new AlbaranSalida(-1,al.getnCliente(),new Date(),0);
			daoal.create(b);
			daoal.facturar(b.getnAlbaran(), f.getnFactura());
		}
		
		ModelAndView mv = listarFacturaSalida(sesion);

	return mv;
	}
	
	/*Crear FacturaSalida -- 
	 * Hay que modificar el direccionamiento de las vistas 
	 * en los casos en los que el cliente no exista 
	 * y en los que no haya ningún albaran pendiente de facturar*/ 
	
	@RequestMapping("/CrearFacturaSalida")
	public ModelAndView crearFacturaSalida(HttpSession sesion,
			  @RequestParam (value="cif_nif") String CifNif){
		
		ModelAndView mv = null;
		
		//Recupero los datos de la persona
		Persona p=daop.read(CifNif);
		
		//Recupero una lista de albaranes pendientes de facturar de ese cliente
		List<AlbaranSalida> listaPendiente = daoal.listarPendientes(CifNif);
		
		if(p==null){
			mv=new ModelAndView("listadoClientes"); //Enviaría al controlador o la vista de nuevoCliente
		}else{
			
			if(listaPendiente.size()<1){
				mv=new ModelAndView("listarAlbaranSalida"); //Enviaria al controlador o la vista de nuevoAlbaranSalida
			}else{
				
				//Creo una factura sin datos con la fecha actual
				FacturaSalida fs=new FacturaSalida(-1,new Date(),21,0,false);
				daof.create(fs);
				
				//Meto el precio neto de cada albaran de la lista
				for(AlbaranSalida al:listaPendiente){
					Double precioAl = daoal.calcularPrecio(al.getnAlbaran());
					al.setPrecioNeto(precioAl);	
								
				}
					
					mv=new ModelAndView("editarFacturaSalida");
					mv.addObject("cliente", p);
					mv.addObject("factura", fs);
					mv.addObject("listaPendiente", listaPendiente);
					mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
									
			}
		}
		return mv;
	}
	
	@RequestMapping("/FacturarAlbaranSalida")
	public ModelAndView facturarAlbaranSalida(HttpSession sesion,
			@RequestParam (value="n_factura") int nFactura,
			@RequestParam (value="n_albaran") int nAlbaran,
			@RequestParam (value="borrar") boolean borrarAlbaran){
		
		
		ModelAndView mv = new ModelAndView("editarFacturaSalida");
		int nf=nFactura;
		if(borrarAlbaran){
			nf=0;
		}
		daoal.facturar(nAlbaran, nf);
				
		System.out.println(nFactura);
		FacturaSalida fs=daof.readConDetalles(nFactura); //Recuperamos el objeto Factura con la lista de albaranes que tiene
		
		if(fs.getAlbaranes().size()==0){
			daof.delete(fs.getnFactura());
			mv = listarFacturaSalida(sesion);
		}else{
			daof.meterDni(fs);
				
			for(AlbaranSalida al:fs.getAlbaranes()){
				//Meto el precio neto de cada albaran de la lista
				Double precioAl = daoal.calcularPrecio(al.getnAlbaran());
				al.setPrecioNeto(precioAl);	
			
				//Meto el precio en cada linea de cada albaran
				for(LineaAlbaranSalida li:al.getLineas()){
					li.setPrecioTotal(li.getnCajas()*li.getPrecioCaja());	
				}
			
			}
		
			//Recupero los datos de la persona
			Persona p=daop.read(fs.getCifnif());
				
			//Recupero una lista de albaranes pendientes de facturar
			List<AlbaranSalida> listaPendiente = daoal.listarPendientes(fs.getCifnif());
		
			//Meto el precio neto de cada albaran de la lista
			for(AlbaranSalida al:listaPendiente){
				Double precioAl = daoal.calcularPrecio(al.getnAlbaran());
				al.setPrecioNeto(precioAl);	
			}
		
			//Recalculo el precio neto y total de la factura
			Double neto = daof.calcularPrecioFactura(nFactura);
			fs.setPrecioNeto(neto);
		
			Double precio = fs.getPrecioNeto()+((fs.getPrecioNeto()*fs.getIva())/100);
			fs.setPrecioTotal(precio);
		
			daof.update(fs);
				
			mv.addObject("cliente", p);
			mv.addObject("factura", fs);
			mv.addObject("listaPendiente", listaPendiente);
			mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
		}
		return mv;
	}
	
	@RequestMapping("/updateFacturaSalidaIVA")
	public ModelAndView updateFacturaSalidaIVA(HttpSession sesion,
			@RequestParam (value="n_factura") int nFactura,
			@RequestParam (value="iva") int iva){
		
		FacturaSalida fs=daof.read(nFactura);
		fs.setIva(iva);
		daof.update(fs);
		
		ModelAndView mv = modificarFacturaSalida(sesion, nFactura);
		return mv;
		
	}
	
	@RequestMapping("/updateFacturaSalidaFecha")
	public ModelAndView updateFacturaSalidaIVA(HttpSession sesion,
			@RequestParam (value="n_factura") int nFactura,
			@RequestParam (value="fecha") String fecha){
		
		Date fi = DateUtils.parseFormato(fecha);
		
		FacturaSalida fs=daof.read(nFactura);
		fs.setFecha(fi);
		daof.update(fs);
		
		ModelAndView mv = modificarFacturaSalida(sesion, nFactura);
		return mv;
		
	}
}
