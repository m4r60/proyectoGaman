package controladores;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.DAOAlbaranEntrada;
import dao.DAOFacturaEntrada;
import dao.DAOLineaAlbaranEntrada;
import dao.DAOPersona;
import modelos.AlbaranEntrada;
import modelos.FacturaEntrada;
import modelos.LineaAlbaranEntrada;
import modelos.Persona;
import Utils.DateUtils;

@Controller
public class FacturaEntradaControlador {
	
	@Autowired
	DAOPersona daop;
	
	@Autowired
	DAOFacturaEntrada daof;
	
	@Autowired
	DAOAlbaranEntrada daoal;
	
	@Autowired
	DAOLineaAlbaranEntrada daola;
	
	@RequestMapping("/listarFacturaEntrada")
	public ModelAndView listarFacturaEntrada(HttpSession sesion){
		
		ModelAndView mv = new ModelAndView("listarFacturasEntrada");
		
		List<FacturaEntrada> listaFac = daof.listar();
		
		//Recupero en cada factura el Dni que lleva vinculado el Albaran que tiene esta factura
		//Calculo el precio total de cada factura
		
		for(FacturaEntrada f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
			
		}
				
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
		
		return mv;
	}
	
	@RequestMapping("/FiltroFacturaEntradaFecha")
	public ModelAndView filtroFacturaEntradaFecha(HttpSession sesion,
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
		
		ModelAndView mv = new ModelAndView("listarFacturasEntrada");
		List<FacturaEntrada> listaFac = daof.buscarFecha(fechaAnterior, fechaPosterior);
		
		for(FacturaEntrada f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
		}
				
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
		
		return mv;
	}
	
	@RequestMapping("/FiltroFacturaEntradaCifNif")
	public ModelAndView filtroFacturaEntradaCifNif(HttpSession sesion,
			  @RequestParam (value="cif_nif") String cifNif){
		
		ModelAndView mv = new ModelAndView("listarFacturasEntrada");
		List<FacturaEntrada> listaFac = daof.listar(cifNif);
		
		/*Recupero en cada factura el Dni que lleva vinculado el Albaran 
		 * que tiene esta factura y calculo el precio total de cada factura
		 */
		
		for(FacturaEntrada f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
			
		}
		
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
				
		return mv;
	}
	
	
	@RequestMapping("/FiltroFacturaEntradanFactura")
	public ModelAndView filtroFacturaEntradanFactura(HttpSession sesion,
			  @RequestParam (value="n_factura") int nFactura){
		
		ModelAndView mv = new ModelAndView("listarFacturasEntrada");
		List<FacturaEntrada> listaFac = daof.listar(nFactura);
		
		/*Recupero en cada factura el Dni que lleva vinculado el Albaran 
		 * que tiene esta factura y calculo el precio total de cada factura
		 */
		
		for(FacturaEntrada f:listaFac){
			daof.meterDni(f);
			Double precio = f.getPrecioNeto()+((f.getPrecioNeto()*f.getIva())/100);
			f.setPrecioTotal(precio);
			
		}
		
		mv.addObject("listaFacturas", listaFac);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
				
		return mv;
	}
	
	/*Modificar FacturaEntrada*/ 
	@RequestMapping("/ModificarFacturaEntrada")
	public ModelAndView modificarFacturaEntrada(HttpSession sesion,
			  @RequestParam (value="n_factura") int nFactura){ //Al darle a modificar recuperamos nFactura
	
		ModelAndView mv = new ModelAndView("editarFacturaEntrada");
		FacturaEntrada fe=daof.readConDetalles(nFactura); //Recuperamos el objeto Factura con la lista de albaranes que tiene
		
		daof.meterDni(fe);
		Double precio = fe.getPrecioNeto()+((fe.getPrecioNeto()*fe.getIva())/100);
		fe.setPrecioTotal(precio);
		
		for(AlbaranEntrada al:fe.getAlbaranes()){
			//Meto el precio neto de cada albaran de la lista
			Double precioAl = daoal.calcularPrecioE(al.getnAlbaran());
			al.setPrecioNetoE(precioAl);	
			
			//Meto el precio en cada linea de cada albaran
			for(LineaAlbaranEntrada li:al.getLineas()){
				li.setPrecioTotal(li.getPeso()*li.getPrecioKg());	
			}
			
		}
		
		//Recupero los datos de la persona
		Persona p=daop.read(fe.getCifnif());
				
		//Recupero una lista de albaranes pendientes de facturar
		List<AlbaranEntrada> listaPendiente = daoal.listarPendientes(fe.getCifnif());
		
		//Meto el precio neto de cada albaran de la lista
		for(AlbaranEntrada al:listaPendiente){
			Double precioAl = daoal.calcularPrecioE(al.getnAlbaran());
			al.setPrecioNetoE(precioAl);	
		}
				
		mv.addObject("agricultor", p);
		mv.addObject("factura", fe);
		mv.addObject("listaPendiente", listaPendiente);
		mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
				
		return mv;
	}
		
	/*Eliminar FacturaEntrada*/
	@RequestMapping("/EliminarFacturaEntrada")
	public ModelAndView eliminarFacturaEntrada(HttpSession sesion,
			  @RequestParam (value="n_factura") int nFactura){ //Recibo el nFactura de la factura que quiero borrar
		//Anulo la factura
		daof.anularFactura(nFactura);
		
		//Creo una factura analoga a la anulada con la fecha de hoy y precio neto negativo
		FacturaEntrada fac=daof.readConDetalles(nFactura);
		FacturaEntrada f=new FacturaEntrada(-1,new Date(),fac.getIva(),-(fac.getPrecioNeto()),false);
		daof.create(f);
		daof.anularFactura(f.getnFactura());
		
		//Por cada albaran que contiene una factura a anular, creo un albaran analogo con precio negativo y nFactura de la factura anulada
		for(AlbaranEntrada al:fac.getAlbaranes()){
			AlbaranEntrada b=new AlbaranEntrada(-1,al.getnSocio(),new Date(),0);
			daoal.create(b);
			daoal.facturar(b.getnAlbaran(), f.getnFactura());
		}
		
		ModelAndView mv = listarFacturaEntrada(sesion);

	return mv;
	}
	
	/*Crear FacturaEntrada -- 
	 * Hay que modificar el direccionamiento de las vistas 
	 * en los casos en los que el Agricultor no exista 
	 * y en los que no haya ningún albaran pendiente de facturar*/ 
	
	@RequestMapping("/CrearFacturaEntrada")
	public ModelAndView crearFacturaEntrada(HttpSession sesion,
			  @RequestParam (value="cif_nif") String CifNif){
		
		ModelAndView mv = null;
		
		//Recupero los datos de la persona
		Persona p=daop.read(CifNif);
		
		//Recupero una lista de albaranes pendientes de facturar de ese Agricultor
		List<AlbaranEntrada> listaPendiente = daoal.listarPendientes(CifNif);
		
		if(p==null){
			mv=new ModelAndView("listadoAgricultores"); //Enviaría al controlador o la vista de nuevoAgricultor
		}else{
			
			if(listaPendiente.size()<1){
				mv=new ModelAndView("listarAlbaranEntrada"); //Enviaria al controlador o la vista de nuevoAlbaranEntrada
			}else{
				
				//Creo una factura sin datos con la fecha actual
				FacturaEntrada fe=new FacturaEntrada(-1,new Date(),21,0,false);
				daof.create(fe);
				
				//Meto el precio neto de cada albaran de la lista
				for(AlbaranEntrada al:listaPendiente){
					Double precioAl = daoal.calcularPrecioE(al.getnAlbaran());
					al.setPrecioNetoE(precioAl);	
								
				}
					
					mv=new ModelAndView("editarFacturaEntrada");
					mv.addObject("agricultor", p);
					mv.addObject("factura", fe);
					mv.addObject("listaPendiente", listaPendiente);
					mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
									
			}
		}
		return mv;
	}
	
	@RequestMapping("/FacturarAlbaranEntrada")
	public ModelAndView facturarAlbaranEntrada(HttpSession sesion,
			@RequestParam (value="n_factura") int nFactura,
			@RequestParam (value="n_albaran") int nAlbaran,
			@RequestParam (value="borrar") boolean borrarAlbaran){
		
		ModelAndView mv = new ModelAndView("editarFacturaEntrada");
		int nf=nFactura;
		if(borrarAlbaran){
			nf=0;
		}
		daoal.facturar(nAlbaran, nf);
				
		System.out.println(nFactura);
		FacturaEntrada fe=daof.readConDetalles(nFactura); //Recuperamos el objeto Factura con la lista de albaranes que tiene
		
		if(fe.getAlbaranes().size()==0){
			daof.delete(fe.getnFactura());
			mv = listarFacturaEntrada(sesion);
		}else{
			daof.meterDni(fe);
				
			for(AlbaranEntrada al:fe.getAlbaranes()){
				//Meto el precio neto de cada albaran de la lista
				Double precioAl = daoal.calcularPrecioE(al.getnAlbaran());
				al.setPrecioNetoE(precioAl);	
			
				//Meto el precio en cada linea de cada albaran
				for(LineaAlbaranEntrada li:al.getLineas()){
					li.setPrecioTotal(li.getPeso()*li.getPrecioKg());	
				}
			
			}
		
			//Recupero los datos de la persona
			Persona p=daop.read(fe.getCifnif());
				
			//Recupero una lista de albaranes pendientes de facturar
			List<AlbaranEntrada> listaPendiente = daoal.listarPendientes(fe.getCifnif());
		
			//Meto el precio neto de cada albaran de la lista
			for(AlbaranEntrada al:listaPendiente){
				Double precioAl = daoal.calcularPrecioE(al.getnAlbaran());
				al.setPrecioNetoE(precioAl);	
			}
		
			//Recalculo el precio neto y total de la factura
			Double neto = daof.calcularPrecioFactura(nFactura);
			fe.setPrecioNeto(neto);
		
			Double precio = fe.getPrecioNeto()+((fe.getPrecioNeto()*fe.getIva())/100);
			fe.setPrecioTotal(precio);
		
			daof.update(fe);
				
			mv.addObject("agricultor", p);
			mv.addObject("factura", fe);
			mv.addObject("listaPendiente", listaPendiente);
			mv.addObject("formatoFecha",DateUtils.getFormatoFechaVista());
		}
		return mv;
	}
	
	@RequestMapping("/updateFacturaEntradaIVA")
	public ModelAndView updateFacturaEntradaIVA(HttpSession sesion,
			@RequestParam (value="n_factura") int nFactura,
			@RequestParam (value="iva") int iva){
		
		FacturaEntrada fe=daof.read(nFactura);
		fe.setIva(iva);
		daof.update(fe);
		
		ModelAndView mv = modificarFacturaEntrada(sesion, nFactura);
		return mv;
		
	}
	
	@RequestMapping("/updateFacturaEntradaFecha")
	public ModelAndView updateFacturaEntradaIVA(HttpSession sesion,
			@RequestParam (value="n_factura") int nFactura,
			@RequestParam (value="fecha") String fecha){
		
		Date fi = DateUtils.parseFormato(fecha);
		
		FacturaEntrada fe=daof.read(nFactura);
		fe.setFecha(fi);
		daof.update(fe);
		
		ModelAndView mv = modificarFacturaEntrada(sesion, nFactura);
		return mv;
		
	}
}

