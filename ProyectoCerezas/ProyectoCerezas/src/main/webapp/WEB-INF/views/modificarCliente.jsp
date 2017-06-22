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
              <label><h1><spring:message code="modificar_cliente" /></h1></label>
	        </div><br>
	        <div id="listado">
				<form action="modificadoCliente" method="POST" >
					<input type="hidden" name="id_persona" value="${cliente.getIdPersona()}">
					<input type="hidden" name="n_cliente" value="${cliente.nCliente}">
					<input type="hidden" name="baja" value="${cliente.baja}">
					<label for="nombre_razon_social"><spring:message code="nombre_razon_social"/>:</label> 
						<input type="text" id="nombre_razon_social" name="nombre_razon_social" placeholder="Nombre/RazÃ³n Social"  value = "${cliente.nombreRazonSocial}"/><br/><br/> 
					<label for="apellido"><spring:message code="apellido" />:</label> 
						<input type="text" id="apellido" name="apellido" placeholder="Apellidos" value = "${cliente.apellidos}"/><br/><br/> 
					<label for="cifNif"><spring:message code="cif_nif" />:</label> 
						<input type="text" id="cif_nif" name="cif_nif" placeholder="CIF/NIF" value = "${cliente.cifNif}"/><br/><br/> 
					<label for="direccion"><spring:message code="direccion" />:</label> 
						<input type="text" id="direccion" name="direccion" placeholder="DirecciÃ³n" value = "${cliente.direccion}"/><br/><br/> 
					<label for="telefono"><spring:message code="telefono" />:</label> 
						<input type="text" id="telefono" name="telefono" placeholder="TelÃ©fono" value = "${cliente.telefono}"/><br/><br/> 
					<label for="email"><spring:message code="email" />:</label> 
						<input type="email" id="email" name="email" placeholder="E-mail" value = "${cliente.email}"/><br/><br/> 				
					<label for="nCliente"><spring:message code="numeroCliente" /></label> 
						<input type="text" id="n_cliente" name="n_cliente" placeholder="Nº Cliente" value = "${cliente.nCliente}" disabled/><br/><br/><br/><br/> 	
			 		<div id="filtro">
						<button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar" /></button>
			 		</div>
				</form>
	        </div>
        </div>
      </div>
    </section>
<c:import url="/WEB-INF/views/end.jsp" />