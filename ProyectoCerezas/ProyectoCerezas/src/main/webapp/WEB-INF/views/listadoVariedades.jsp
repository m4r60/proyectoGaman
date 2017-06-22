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
            <form id="formulario" action="filtroVariedadesTipo" method="POST" > 
            	<label><h1><spring:message code="variedades_listado" /></h1></label><br><br>
                <label for="buscar_cliente" id="buscar_cliente"><spring:message code="buscar_variedad"/></label> 
               	<input type="text" id="buscar_cliente" name="buscar_variedad"/>  
                <!-- BUSCAR -->
                <button title="<spring:message code='buscar'/>" type="submit" id ="buscar" href="controladorBuscarCliente"  class="btn btn-default btn-sm">
                      <span class="glyphicon glyphicon-search"></span>
                </button>  
            </form><br><br>
            <form>
            	<a type="submit" id="modal" href="#dialog2" name="modal" class="btn btn-default btn-sm"  class="button">
                <span>Nueva<br/>Variedad</span></a> 
            </form>
          </div> 
          <div id="listado">
            <table>
              <caption><h5><spring:message code="listado_variedades" /></h5></caption>
              <thead>
                <tr id="texto">
                  <th><spring:message code='tipo' /></th>
                  <th><spring:message code='precio_kg'/></th>
                  <th><spring:message code='peso_caja' /></th>
                  <th><spring:message code='precio_caja' /></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${listado}" var="p">
	                <tr>
	                  <td>${p.tipo}</td>
	                  <td>${p.precioKg}</td>
	                  <td>${p.pesoCaja}</td>
	                  <td>${p.precioCaja}</td>
	                  <!-- EDITAR -->             
	                  <td title="<spring:message code='editar'/>">
	                  	<form action="editarVariedad" method="POST">
		                  	<input type="hidden" name="tipo" value="${p.tipo}"/>
							<button type= "submit"  class="btn btn-default btn-sm"><span class="glyphicon glyphicon-edit"></span></button>
						</form></td>
	                  <!-- DAR DE BAJA -->
	                  <!-- <td title="<spring:message code='baja'/>"><a type="submit" id="modal" href="#baja" onclick="mostrarDialogoConfirmarBajaVariedad('${p.tipo}','${p.precioKg}','${p.pesoCaja}','${p.precioCaja}')" name="modal" class="btn btn-default btn-sm" class="button"><span class="glyphicon glyphicon-remove"></span></a></td> -->   
	                </tr>
	                </c:forEach>  
	          </tbody>
	        </table>
	      </div>    
	   </div>
    </div>
  </section>
  <!-- _____________VENTANA MODAL BUSCAR (tipo)_________ -->
  <div class="overlay" id="overlay" style="display:none;"></div>
    <div id="dialog2" class="window">
        <h1><spring:message code='registrar_variedad' /></h1>
        <p><spring:message code='introduce_variedad' /></p><br/><br/>
        <form action="nuevaVariedad" method="POST" >
          <label for="variedad"><spring:message code="variedad" /></label> 
          <input type="text" id="tipo" name="tipo" placeholder="Tipo de variedad"/><br/><br/><br/><br/>
          <button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar"/></button>  
        </form> 
        <form action="listadoVariedades" method="get" accept-charset="utf-8">
          <button type="submit" class="btn btn-lg btn-default" class="close" /><spring:message code="cancelar"/></button>
        </form>       
      </div>
  <!-- _____________VENTANA MODAL DAR DE BAJA_________ -->
  <div class="overlay" id="overlay" style="display:none;"></div>
     <div id="baja" class="window">
       <h1><spring:message code="dar_baja"/></h1>
       <form action="eliminarVariedad" method="POST">
       		<p><spring:message code="desea_baja_variedad"/></p>
           <h6><label id="mod_tipo"></label></h6>
           <h6><label id="mod_precioKg"></label><h6>
           <h6><label id="mod_pesoCaja"></label></h6>
           <h6><label id="mod_precioCaja"></label></h6>
           <!-- NO TOCAR -->
           <input id="tipo" type="hidden"  name="tipo" value ="">
           <button type="submit" id="aceptar" class="btn btn-lg btn-default"/><spring:message code="aceptar"/></button>
       </form> 
       <form action="listadoVariedades" method="POST">
           <button type="submit" class="btn btn-lg btn-default" class="close" /><spring:message code="cancelar"/></button>
       </form>
     </div>
   <script>
     function mostrarDialogoConfirmarBajaVariedad(tipo,precioKg,pesoCaja,precioCaja){
       document.getElementById("mod_tipo").innerHTML=tipo;
       document.getElementById("mod_precioKg").innerHTML=precioKg;
       document.getElementById("mod_pesoCaja").innerHTML=pesoCaja;
       document.getElementById("mod_precioCaja").innerHTML=precioCaja;
   	   document.getElementById("tipo").value=tipo;}
  </script> 
<c:import url="/WEB-INF/views/end.jsp" />