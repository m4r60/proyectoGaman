<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:import url="/WEB-INF/views/head.jsp" />
<!--==============================content=================================-->
	<!-- FONDO -->
      <script>
      $(function(){
      //  Initialize Backgound Stretcher
      $('BODY').bgStretcher({
        images: ['resources/images/fondoblanco.jpg'], 
		imageWidth: 1600, 
		imageHeight: 964, 
		resizeProportionally:true	
       });	
    });
    </script>
   <section id="content">
      <div class="main-block">
        <div class="main">
          <div id="filtro">
              <label><h1><spring:message code="nuevo_agricultor" /></h1></label>
	         </div><br>
	         <div id="listado">
				<form action="crearAgricultor" method="POST" >
					<label for="nombre_razon_social"><spring:message code="nombre_razon_social"/>:</label> 
						<input type="text" id="nombre_razon_social" name="nombre_razon_social" placeholder="Nombre/RazÃ³n Social"/><br/><br/> 
					<label for="apellido"><spring:message code="apellido" />:</label> 
						<input type="text" id="apellido" name="apellido" placeholder="Apellidos"/><br/><br/> 
					<label for="cifNif"><spring:message code="cif_nif" />:</label> 
						<input type="text" id="cif_nif" name="cif_nif" placeholder="CIF/NIF" value = "${cifNif}"/><br/><br/> 
					<label for="direccion"><spring:message code="direccion" />:</label> 
						<input type="text" id="direccion" name="direccion" placeholder="DirecciÃ³n"/><br/><br/> 
					<label for="telefono"><spring:message code="telefono" />:</label> 
						<input type="text" id="telefono" name="telefono" placeholder="TelÃ©fono"/><br/><br/> 
					<label for="email"><spring:message code="email" />:</label> 
						<input type="email" id="email" name="email" placeholder="E-mail"/><br/><br/><br/><br/> 
	
					<!--  <label for="tipo"><spring:message code="select_alta" /></label>
	                <input type="Radio" id="alta" name="alta" />
	                <br/>
	               
	                <label for="tipo"><spring:message code="select_baja" /></label>
	                <input type="Radio" id="alta" name="baja" value="baja"/>
	                <br>-->
	                
	                <div id="filtro">
	                  <input type="hidden" name="nSocio" value ="${n_socio}">
	                  <button type="submit" class="btn btn-lg btn-default"/><spring:message code="aceptar"/></button>
	                </div>
	            </form> 
	         </div>
	      </div>
	   </div>
  	</section>
<c:import url="/WEB-INF/views/end.jsp" />				