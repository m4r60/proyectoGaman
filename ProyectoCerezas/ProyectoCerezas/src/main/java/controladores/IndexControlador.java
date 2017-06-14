package controladores;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexControlador {
	
/* Como mucho hay que pasar un mensaje de los que estan guardados para el titulo*/
	
	@RequestMapping(value={"/index","/"})
	    public ModelAndView index(HttpSession sesion){
			System.out.println("AAAAAAAAAA");
	    	ModelAndView mv = new ModelAndView("index");
	    	return mv;
	    }
	@RequestMapping(value={"/contacto"})
    public ModelAndView contacto(HttpSession sesion){
    	ModelAndView mv = new ModelAndView("contacto");
    	return mv;
    }
}
