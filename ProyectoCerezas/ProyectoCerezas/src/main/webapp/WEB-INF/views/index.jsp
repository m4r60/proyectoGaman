<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:import url="/WEB-INF/views/head.jsp" />
      <!--==============================content=================================-->
       <!-- PARA EL INDEX.JSP -->
	<script>
    $(function(){
      //  Initialize Backgound Stretcher
      $('BODY').bgStretcher({
        images: ['resources/images/fondoBlanco1.jpg','resources/images/fondoColor.jpg','resources/images/fondoRojo.jpg','resources/images/fondoNegro.jpg'], 
		    imageWidth: 1600, 
		    imageHeight: 946,
		    resizeProportionally:true,
    		slideDirection: 'N',
    		slideShowSpeed: 1000,
    		transitionEffect: 'fade',
    		sequenceMode: 'normal',		
    		pagination: '#nav'
      });	
    });
  </script>
     <section id="content1">
      <div class="policy">
        <div class="main1">
           <!--  <div class="row-nav1">
            <h4>Aplicación Web de uso interno para la gestión financiera de una empresa de cerezas.<br><br><br>

			Su finalida es controlar el pago a los agriculotres que traen la cereza al almacén, registrar la salida del producto a traves de 2 tipos de cerezas en cajas de 2 kilos y 2,5 kilos. Asi como el contro de las factruas y los albaranes de entrada y salida del producto, y poder facturar al final de la campaña.</h4>
           </div>-->
         </div>
      </div>  
     </section>
<c:import url="/WEB-INF/views/end.jsp" />
