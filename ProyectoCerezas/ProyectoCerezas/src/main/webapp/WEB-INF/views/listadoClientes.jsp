<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:import url="/WEB-INF/views/head.jsp" />
<!--==============================content=================================-->
	<!-- FONDO GENERAL-->
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
	            <form id="formulario" action="FiltroListadoClientes" method="POST" > 
                  <label><h1><spring:message code="cliente" /></h1></label><br><br>
                  <label for="buscar_cliente" id="buscar_cliente_texto"><spring:message code="buscar_cliente"/></label> 
                  <input type="text" id="buscar_cliente" name="buscar_cliente"/>  
                  <!-- BUSCAR -->
                  <button title="<spring:message code='buscar'/>" type="submit" id ="buscar"  class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-search"></span>
                  </button>   
	            </form><br><br>
	            <form id="formulario"  method="POST" >
	            	<a type="submit" id="modal" href="#dialog2" name="modal" class="btn btn-default btn-sm"  class="button">
	                <span  class="glyphicon glyphicon-user"><br/>Nuevo<br/>cliente</span></a>
	            </form>   
	          </div> 
	          <div id="listado">
	            <table>
	              <caption><h5><spring:message code="listado_clientes" /></h5></caption>
	              <thead>
	                <tr id="texto">
	                  <th><spring:message code='n_cliente' /></th>
	                  <th><spring:message code='cif_nif' /></th>
	                  <th><spring:message code='nombre_razon_social' /></th>
	                  <th><spring:message code='apellido' /></th>
	                  <th><spring:message code='direccion' /></th>
	                  <th><spring:message code='telefono' /></th>
	                  <th><spring:message code='email' /></th>
	                  <th></th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach items="${listado}" var="p">
	                <tr>
	                  <td>${p.nCliente}</td>
	                  <td>${p.cifNif}</td>
	                  <td>${p.nombreRazonSocial}</td>
	                  <td>${p.apellidos}</td>
	                  <td>${p.direccion}</td>
	                  <td>${p.telefono}</td>
	                  <td>${p.email}</td>
	                  <!-- EDITAR -->
	                  <td title="<spring:message code='editar'/>">
	                  	<form action = "modificarCliente" method = "POST">
	                  		<input  type ="hidden" name = "nCliente" value = "${p.nCliente}"/>
	                  		<button type="submit" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-edit"></span></button>
	                  	</form></td> 
	                  <!-- DAR DE BAJA -->
	                  <td title="<spring:message code='baja'/>"><a type="submit" id="modal" href="#baja" onclick="mostrarDialogoConfirmarBajaCliente('${p.nCliente}','${p.nombreRazonSocial}','${p.apellidos}','${p.cifNif}','${p.direccion}','${p.telefono}','${p.email}')" name="modal" class="btn btn-default btn-sm" class="button"><span class="glyphicon glyphicon-remove"></span></a></td>   
	                </tr>
	                </c:forEach>  
	              </tbody>
	            </table>
	          </div>    
	      </div>
      </div>
    </section>
    <!-- _____________VENTANA MODAL BUSCAR (cif o nif)_________ -->
    <div class="overlay" id="overlay" style="display:none;"></div>
      <div id="dialog2" class="window">
        <h1><spring:message code='registrar' /></h1><br/><br/><br/>
        <p><spring:message code='introduce_cif' /></p><br/><br/>
        <form action="nuevoCliente" method="POST" >
          <label for="cifNif"><spring:message code="cif_nif"/></label> 
          <input type="text" id="cif_nif" name="cif_nif" placeholder="CIF o NIF"/><br/><br/><br/><br/>
          <button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar"/></button>
        </form> 
        <form action="listadoClientes" method="POST">
          <button type="submit" class="btn btn-lg btn-default" class="close" /><spring:message code="cancelar"/></button>
        </form>        
      </div>     
	<!-- _____________VENTANA MODAL DAR DE BAJA_________ -->
	<div class="overlay" id="overlay" style="display:none;"></div>
		<div id="baja" class="window">
		  <h1><spring:message code="dar_baja"/></h1><br/><br/>
		  <p><spring:message code="desea_baja_cliente"/></p>
	      <form action="darBaja" method="POST" accept-charset="utf-8">
	             <h6><label id="mod_nCliente"></label></h6>
	             <h6><label id="mod_cifNif"></label><h6>
	             <h6><label id="mod_nRSocial"></label></h6>
	             <h6><label id="mod_apellidos"></label></h6>
	             <h6><label id="mod_direccion"></label></h6>
	             <h6><label id="mod_telefono"></label></h6>
	             <h6><label id="mod_email"></label></h6></br>  
	             <!-- NO TOCAR -->
	             <input id="nCliente" type="hidden" name="nCliente" value ="">
	             <button type="submit"  id="aceptar" class="btn btn-lg btn-default"/><spring:message code="aceptar"/></button>
	       </form> 
	       <form action="listadoClientes" method="POST">
	           <button type="submit" class="btn btn-lg btn-default" /><spring:message code="cancelar"/></button>
	       </form>
		</div>
	<!-- NO TOCAR -->
	<script>
		function mostrarDialogoConfirmarBajaCliente(nCliente,nRSocial,apellidos,cifNif,direccion,telefono,email){
			document.getElementById("mod_nCliente").innerHTML=nCliente;
			document.getElementById("mod_nRSocial").innerHTML=nRSocial;
			document.getElementById("mod_apellidos").innerHTML=apellidos;
			document.getElementById("mod_cifNif").innerHTML=cifNif;
			document.getElementById("mod_telefono").innerHTML=telefono;
			document.getElementById("mod_direccion").innerHTML=direccion;
			document.getElementById("mod_email").innerHTML=email;
			document.getElementById("nCliente").value = nCliente;}
	</script>
<c:import url="/WEB-INF/views/end.jsp" />