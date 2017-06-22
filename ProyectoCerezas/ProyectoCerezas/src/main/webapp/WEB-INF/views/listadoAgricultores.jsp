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
            <form id="formulario" action="FiltroListadoAgricultores" method="POST" > 
                  <label><h1><spring:message code="agricultor" /></h1></label><br><br>
                  <label for="buscar_agricultor" id="buscar_agricultor_texto"><spring:message code="buscar_agricultor"/></label>
                  <input type="text" id="buscar_agricultor" name="buscar_agricultor"/>  
                  <!-- BUSCAR -->
                  <button title="<spring:message code='buscar'/>" type="submit" id ="buscar" href="controladorBuscarCliente"  class="btn btn-default btn-sm">
                        <span class="glyphicon glyphicon-search"></span>
                  </button>        
            </form><br><br>
            <form id="formulario"  method="POST" >
            	<a type="submit" id="modal" href="#dialog2" name="modal" class="btn btn-default btn-sm"  class="button">
                <span  class="glyphicon glyphicon-user"><br/>Nuevo<br/>agricultor</span></a>
            </form>
          </div> 
          <div id="listado">
            <table>
              <caption><h5><spring:message code="listado_agricultores" /></h5></caption>
              <thead>
                <tr id="texto">
                  <th><spring:message code='n_socio' /></th>
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
                  <td>${p.nSocio}</td>
                  <td>${p.cifNif}</td>
                  <td>${p.nombreRazonSocial}</td>
                  <td>${p.apellidos}</td>
                  <td>${p.direccion}</td>
                  <td>${p.telefono}</td>
                  <td>${p.email}</td>
                  <!-- EDITAR -->
                  <td title="<spring:message code='editar'/>">
                  	<form action = "modificarAgricultor" method = "POST">
                  		<input  type ="hidden" name = "nSocio" value = "${p.nSocio}"/>
                  		<button type="submit" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-edit"></span></button>
                  		</form></td> 
                  <!-- DAR DE BAJA -->
                  <td title="<spring:message code='baja'/>"><a type="submit" id="modal" href="#baja" onclick="mostrarDialogoConfirmarBajaAgricultor('${p.nSocio}','${p.nombreRazonSocial}','${p.apellidos}','${p.cifNif}','${p.direccion}','${p.telefono}','${p.email}')" name="modal" class="btn btn-default btn-sm" class="button" ><span class="glyphicon glyphicon-remove"></span></a></td>  
                </tr>
                </c:forEach>  
              </tbody>
            </table>
          </div>    
      	</div>
      </div>
     </section>
     <!-- _____________VENTANA MODAL cif o nif_________ -->
     <div class="overlay" id="overlay" style="display:none;"></div>
        <div id="dialog2" class="window">
          <h1><spring:message code='registrar_agricultor' /></h1><br/><br/><br/>
          <p><spring:message code='introduce_cif_agricultor' /></p><br/><br/>
          <form action="nuevoAgricultor" method="POST" >
            <label for="cifNif"><spring:message code="cif_nif" /></label> 
            <input type="text" id="cif_nif" name="cif_nif" placeholder="CIF o NIF"/><br/><br/><br/><br/>  
            <button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar"/></button>
          </form> 
          <form action="listadoAgricultores" method="POST">
            <button type="submit" class="btn btn-lg btn-default" class="close" /><spring:message code="cancelar"/></button>
          </form>       
        </div>
	 <!-- _____________VENTANA MODAL Dar de baja (eliminar)_________ -->
	 <div class="overlay" id="overlay" style="display:none;"></div>
        <div id="baja" class="window">
          <h1><spring:message code="dar_baja"/></h1><br/><br/>
          <p><spring:message code="desea_baja_agricultor"/></p>
          <form action="darBajaA" method="POST">
             <h6><label id="mod_nSocio"></label></h6>
             <h6><label id="mod_cifNif"></label><h6>
             <h6><label id="mod_nRSocial"></label></h6>
             <h6><label id="mod_apellidos"></label></h6>
             <h6><label id="mod_direccion"></label></h6>
             <h6><label id="mod_telefono"></label></h6>
             <h6><label id="mod_email"></label></h6></br>
             <!-- NO TOCAR -->
            <input id="nSocio" type="hidden" name="nSocio" value ="99">
            <button type="submit" id="aceptar" class="btn btn-lg btn-default"/><spring:message code="aceptar"/></button>
          </form> 
          <form action="listadoAgricultores" method="POST">
              <button type="submit" class="btn btn-lg btn-default"/><spring:message code="cancelar"/></button>
          </form>
        </div>
    <!-- NO TOCAR -->
	<script>
		function mostrarDialogoConfirmarBajaAgricultor(nSocio,nRSocial,apellidos,cifNif,direccion,telefono,email){
			document.getElementById("mod_nSocio").innerHTML=nSocio;
			document.getElementById("mod_nRSocial").innerHTML=nRSocial;
			document.getElementById("mod_apellidos").innerHTML=apellidos;
			document.getElementById("mod_cifNif").innerHTML=cifNif;
			document.getElementById("mod_telefono").innerHTML=telefono;
			document.getElementById("mod_direccion").innerHTML=direccion;
			document.getElementById("mod_email").innerHTML=email;
			document.getElementById("nSocio").value = nSocio;}
	</script>
<c:import url="/WEB-INF/views/end.jsp" />