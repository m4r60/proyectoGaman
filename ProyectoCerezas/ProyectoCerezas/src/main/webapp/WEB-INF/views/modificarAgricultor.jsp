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
              <label><h1><spring:message code="modificar_agricultor" /></h1></label>
         </div><br>
         <div id="listado">
			<form action="modificadoAgricultor" method="POST" >
				<input type="hidden" name="id_persona" value="${agricultor.getIdPersona()}">
				<input type="hidden" name="n_socio" value="${agricultor.nSocio}">
				<input type="hidden" name="baja" value="${agricultor.baja}">
				<label for="nombre_razon_social"><spring:message code="nombre_razon_social"/>:</label> 
					<input type="text" id="nombre_razon_social" name="nombre_razon_social" placeholder="Nombre/RazÃ³n Social"  value = "${agricultor.nombreRazonSocial}"/><br/><br/> 
				<label for="apellido"><spring:message code="apellido" />:</label> 
					<input type="text" id="apellido" name="apellido" placeholder="Apellidos" value = "${agricultor.apellidos}"/><br/><br/> 
				<label for="cifNif"><spring:message code="cif_nif" />:</label> 
					<input type="text" id="cif_nif" name="cif_nif" placeholder="CIF/NIF" value = "${agricultor.cifNif}"/><br/><br/> 
				<label for="direccion"><spring:message code="direccion" />:</label> 
					<input type="text" id="direccion" name="direccion" placeholder="DirecciÃ³n" value = "${agricultor.direccion}"/><br/><br/> 
				<label for="telefono"><spring:message code="telefono" />:</label> 
					<input type="text" id="telefono" name="telefono" placeholder="TelÃ©fono" value = "${agricultor.telefono}"/><br/><br/> 
				<label for="email"><spring:message code="email" />:</label> 
					<input type="email" id="email" name="email" placeholder="E-mail" value = "${agricultor.email}"/><br/><br/> 
				<label for="nSocio"><spring:message code="numeroAgricultor" /></label> 
					<input type="text" id="n_socio" name="n_socio" placeholder="Nº Socio" value = "${agricultor.nSocio}" disabled/><br/><br/><br/><br/> 
				<div id="filtro">
					<button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar" /></button>
		 		</div>
			</form>
          </div>
        </div>
     </div>
   </section>
<c:import url="/WEB-INF/views/end.jsp" />