<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:import url="/WEB-INF/views/head.jsp" />
 <!--==============================content=================================-->
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
			<label><h1><spring:message code="facturas_salida" /></h1></label>
				<form id="formulario" action="FiltroFacturaSalidanFactura" method="POST">
					<label for="n_factura"><spring:message code="buscar_n_factura" /></label> 
						<input type="text" id="n_factura" name = "n_factura" value=""/>
					<button title="<spring:message code='buscar'/>" type="submit"  class="btn btn-default btn-sm">
			          <span class="glyphicon glyphicon-search"></span>
			    	</button>
				</form>	 
				<form id="formulario" action="FiltroFacturaSalidaCifNif" method="POST">	
					<label for="cif_nif"><spring:message code="buscar_cif_nif" /></label> 
					<input type="text" id="cif_nif" name = "cif_nif" value=""/>
					<button title="<spring:message code='buscar'/>" type="submit"  class="btn btn-default btn-sm">
			          <span class="glyphicon glyphicon-search"></span>
			        </button>
				</form>	
				
				<form id ="formulario" action="FiltroFacturaSalidaFecha" method="POST">        	 
					<label for="fecha"><spring:message code="buscar_fecha_inicio"/></label> 
					<input type="text" id="fecha" name = "fecha_inicio" value="" /><br>
					<label for="fecha"><spring:message code="buscar_fecha_fin" /></label> 
					<input type="text" id="fecha" name = "fecha_final" />  
					<button title="<spring:message code='buscar'/>" type="submit"  class="btn btn-default btn-sm">
			          <span class="glyphicon glyphicon-search"></span>
			        </button>  
				</form><br><br>
				
				<form  id="formulario"  method="POST" >
			        <a type="submit" id="modal" href="#dialog2" name="modal" class="btn btn-default btn-sm"  class="button">
			        <span>Nueva<br/>Factura</span></a> 
		        </form> 
			</div>
			<div id="listado">
				<table>
					<caption><h5><spring:message code="listado_facturas" /></h5></caption>
					<thead>	
						<tr  id="texto">
							<th><spring:message code = "n_factura"/></th>
							<th><spring:message code = "cif_nif"/></th>
							<th><spring:message code = "fecha"/></th>
							<th><spring:message code = "precio_neto"/></th>
							<th><spring:message code = "iva"/></th>
							<th><spring:message code = "precio_bruto"/></th>
						</tr>
					</thead>
			        <tbody>
						<c:forEach items="${listaFacturas}" var="factura">
							<tr>
								<td>${factura.nFactura}</td>
								<td>${factura.cifnif}</td>
								<td>${factura.stringFecha}</td>
								<td>${factura.precioNeto}</td>
								<td>${factura.iva}</td>
								<td>${factura.precioTotal}</td>		
								
								<c:if test="${factura.anulacion == false}">
									<!-- EDITAR -->
									<td title="<spring:message code='editar'/>">
										<form action="ModificarFacturaSalida" method="POST">
											<input type="hidden" name="n_factura" value="${factura.nFactura}"/>
											<button type= "submit"  class="btn btn-default btn-sm"><span class="glyphicon glyphicon-edit"></span></button>
										</form></td> 
									<!-- DAR DE BAJA -->
									<td title="<spring:message code='baja'/>">
									<form action="EliminarFacturaSalida" method="POST">
											<input type="hidden" name="n_factura" value="${factura.nFactura}"/>
											<button type= "submit"  class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></button>
										</form></td> 
									<!--  <a type="submit" id="modal" href="#baja" onclick="mostrarDialogoConfirmarBajaFactura('${factura.nFactura}','${factura.cifnif}',
									'${factura.stringFecha}','${factura.precioNeto}','${factura.iva}','${factura.precioTotal}')" 
									name="modal" class="btn btn-default btn-sm" class="button" ><span class="glyphicon glyphicon-remove"></span></a></td>-->				               
								</c:if>	
								
							</tr>
	 					</c:forEach> 
		           	</tbody>
	            </table>
	        </div>   
	     </div>
      </div>
    </section>
    <!-- _____________VENTANA MODAL NUEVA FACTURA_________ -->
    <div class="overlay" id="overlay" style="display:none;"></div>
       <div id="dialog2" class="window">
          <h1><spring:message code='nuevo_factura_venta' /></h1><br/><br/><br/>
          <p><spring:message code='introduce_cif' /></p><br/><br/>
          <form action="CrearFacturaSalida" method="POST" >
            <label for="cifNif"><spring:message code="cif_nif" /></label> 
            <input type="text" id="cif_nif" name="cif_nif" placeholder="CIF o NIF"/><br/><br/><br/><br/>
            <button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar"/></button>
          </form> 
          <form action="listarFacturaSalida" method="POST">
            <button type="submit" class="btn btn-lg btn-default" class="close" /><spring:message code="cancelar"/></button>
          </form>       
       </div>
    <!-- _____________VENTANA MODAL DAR DE BAJA_________ -->
    <div class="overlay" id="overlay" style="display:none;"></div>
        <div id="baja" class="window">
          <h1><spring:message code="dar_baja"/></h1><br/><br/>
          <p><spring:message code="desea_baja_factura"/></p>
          <form action="EliminarFacturaSalida" method="POST">
              <h6><label id="mod_nFactura"></label></h6>
              <h6><label id="mod_cifNif"></label><h6>
              <h6><label id="mod_fechaStr"></label></h6>
              <h6><label id="mod_precioNeto"></label></h6></br>
              <h6><label id="mod_iva"></label></h6>
              <h6><label id="mod_precioTotal"></label></h6></br>
              <!-- NO TOCAR -->
            <input id="nFactura" type="hidden" name="nFactura" value ="">
            <button type="submit"  id="aceptar" class="btn btn-lg btn-default"/><spring:message code="aceptar"/></button>
          </form> 
          <form action="listarFacturaSalida" method="POST">
              <button type="submit" class="btn btn-lg btn-default"/><spring:message code="cancelar"/></button>
          </form>
        </div>
    <script>
      function mostrarDialogoConfirmarBajaFactura(n_factura,cifnif,stringFecha,precioNeto,iva,precioTotal){
        document.getElementById("mod_nFactura").innerHTML=nFactura;
        document.getElementById("mod_cifNif").innerHTML=cifnif;
        document.getElementById("mod_fechaStr").innerHTML=fecha;
        document.getElementById("mod_precioNeto").innerHTML=precioNeto;
        document.getElementById("mod_iva").innerHTML=nAlbaran;
        document.getElementById("mod_precioTotal").innerHTML=nAlbaran;
        document.getElementById("nFactura").value = nFactura;}
    </script>
<c:import url="/WEB-INF/views/end.jsp" />
