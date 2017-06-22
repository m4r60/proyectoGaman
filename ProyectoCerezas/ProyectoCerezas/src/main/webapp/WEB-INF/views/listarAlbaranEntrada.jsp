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
				<form id="formulario" action="FiltroAlbaranEntradaAlbaran" method="POST">
					<label><h1><spring:message code="albaranes_entrada" /></h1></label><br><br>
			        <label for="n_albaran"><spring:message code="buscar_n_albaran" /></label>
			        	<input type="text" id="n_albaran" value="" />
          			<button title="<spring:message code='buscar'/>" type="submit"  class="btn btn-default btn-sm">
          				<span class="glyphicon glyphicon-search"></span>
          			</button> 
       			</form> 
		        <form id="formulario" action="FiltroAlbaranEntradaCifNif"  method="POST"> 
			        <label for="cif_nif"><spring:message code="buscar_cif_nif" /></label> 
			          	<input type="text" id="cif_nif" name = "cif_nif" value="" />
			        <button title="<spring:message code='buscar'/>" type="submit"  class="btn btn-default btn-sm">
			          	<span class="glyphicon glyphicon-search"></span>
			        </button>
		        </form> 
		        <form id="formulario" action="FiltroAlbaranEntradaFecha"  method="POST"> 
		          	<label for="fechaInicio"><spring:message code="buscar_fecha_inicio" /></label> 
					<input type="text" id="fechaInicio" name = "fecha_inicio" value="" /><br>
					<label for="fechaFinal"><spring:message code="buscar_fecha_fin" /></label> 
					<input type="text" id="fechaFinal" name = "fecha_final" value="" /> 
					<button title="<spring:message code='buscar'/>" type="submit"  class="btn btn-default btn-sm">
			         	<span class="glyphicon glyphicon-search">
			        </button>
		        </form><br><br>
		        <form>
		        	<a type="submit" id="modal" href="#dialog2" name="modal" class="btn btn-default btn-sm"  class="button">
		          	<span>Nuevo<br/>Albaran</span></a> 
		        </form>   
         	</div> 
			<div id="listado">
            	<table>
              		<caption><h5><spring:message code="listado_albaranes" /></h5></caption>
					<thead>
						<tr id="texto">
		                  <th><spring:message code = "n_albaran"/></th>
		                  <th><spring:message code = "n_factura"/></th>
		                  <th><spring:message code = "n_socio"/></th>
		                  <th><spring:message code = "cif_nif"/></th>
		                  <th><spring:message code = "fecha"/></th>
		                  <th><spring:message code='precio_neto' /></th> 
	               		</tr>
            		</thead>
		            <tbody>
		              <c:forEach items="${listado}" var="c">
		                <tr>
			                <td>${c.nAlbaran}</td>
			                <td>${c.nFactura}</td>
			                <td>${c.nSocio}</td>
			                <td>${c.cifNif}</td>
			                <td>${c.fechaStr}</td>
			                <td>${c.precioNetoE}</td>
							<!-- EDITAR -->
							<td title="<spring:message code='editar'/>">
								<form action = "ModificarAlbaranEntrada" method = "POST">
					            	<input  type ="hidden" name = "n_albaran" value = "${c.nAlbaran}"/>
					                <button type="submit" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-edit"></span></button>
					            </form></td> 
					        <!-- DAR DE BAJA -->
							<td title="<spring:message code='baja'/>"><a type="submit" id="modal" href="#baja" onclick="mostrarDialogoConfirmarEliminarAlbaran('${c.nAlbaran}','${c.nFactura}','${c.nSocio}','${c.cifNif}','${c.fechaStr}','${c.precioNetoE}')" name="modal" class="btn btn-default btn-sm" class="button" ><span class="glyphicon glyphicon-remove"></span></a></td>  
		                </tr>
		               </c:forEach> 
		           	</tbody>
            	</table>
          	</div>   
      	</div>
      </div>
    </section>
    <!-- _____________VENTANA MODAL nuevo albarán_________ -->
    <div class="overlay" id="overlay" style="display:none;"></div>
    	<div id="dialog2" class="window">
          <h1><spring:message code='nuevo_albaran_recogida' /></h1><br/><br/><br/>
          <p><spring:message code='introduce_cif' /></p><br/><br/>
          <form action="nuevoAlbaranEntrada" method="POST" >
            <label for="cifNif"><spring:message code="cif_nif" /></label> 
            <input type="text" id="cif_nif" name="cif_nif" placeholder="CIF o NIF"/><br/><br/><br/><br/>
            <button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar"/></button>
          </form> 
          <form action="listarAlbaranEntrada" method="POST">
            <button type="submit" class="btn btn-lg btn-default" class="close" /><spring:message code="cancelar"/></button>
          </form>       
        </div>
    <!-- _____________VENTANA MODAL Dar de baja (eliminar)_________ -->
    <div class="overlay" id="overlay" style="display:none;"></div>
        <div id="baja" class="window">
          <h1><spring:message code="dar_baja"/></h1><br/><br/>
          <p><spring:message code="desea_baja_albaran"/></p>
          <form action="EliminarAlbaranEntrada" method="POST">
              <h6><label id="mod_nAlbaran"></label></h6>
              <h6><label id="mod_nFactura"></label></h6>
              <h6><label id="mod_nSocio"></label></h6>
              <h6><label id="mod_cifNif"></label><h6>
              <h6><label id="mod_fechaStr"></label></h6>
              <h6><label id="mod_precioNetoE"></label></h6>
              <!-- NO TOCAR -->
            <input id="nAlbaran" type="hidden" name="n_albaran" value ="99">
            <button type="submit"  id="aceptar" class="btn btn-lg btn-default"/><spring:message code="aceptar"/></button>
          </form> 
          <form action="listarAlbaranEntrada" method="POST">
              <button type="submit" class="btn btn-lg btn-default"/><spring:message code="cancelar"/></button>
          </form>
        </div>
    <script>
      function mostrarDialogoConfirmarEliminarAlbaran(nAlbaran,nFactura,nSocio,cifNif,fecha,precioNetoE){
      	document.getElementById("mod_nAlbaran").innerHTML=nAlbaran;
        document.getElementById("mod_nFactura").innerHTML=nFactura;
        document.getElementById("mod_nSocio").innerHTML=nSocio;
        document.getElementById("mod_cifNif").innerHTML=cifNif;
        document.getElementById("mod_fechaStr").innerHTML=fechaStr;
        document.getElementById("mod_precioNetoE").innerHTML=precioNetoE;
        document.getElementById("nAlbaran").value = nAlbaran;}
    </script>
<c:import url="/WEB-INF/views/end.jsp" />