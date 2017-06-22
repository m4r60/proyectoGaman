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
      	<label><h1><spring:message code="modificar_albaran_entrada" /></h1></label><br>
		<div id="listado1">
			<table>
				<caption><h5><spring:message code="datos_socio" /></h5></caption>
				<thead>
					<tr id="texto">
		                <th><spring:message code = "nombre_razon_social"/></th>
		                <th><spring:message code = "cif_nif"/></th>
		                <th><spring:message code = "apellido"/></th>
	                  	<th><spring:message code = "direccion"/></th>
		                <th><spring:message code = "telefono"/></th>
		                <th><spring:message code = "email"/></th> 
	             	</tr>
	            </thead>
	            <tbody>
					<tr>
						<td>${persona.nombreRazonSocial}</td>
						<td>${persona.apellidos}</td>
						<td>${persona.cifNif}</td>
						<td>${persona.direccion}</td>
						<td>${persona.telefono}</td>
						<td>${persona.email}></td>
					</tr>
				</tbody>
			</table>
			<table>
              <caption><h5><spring:message code="datos_empresa" /></h5></caption>
				<thead>
					<tr id="texto">
		                <th><spring:message code = "nombre_empresa"/></th>
		                <th><spring:message code = "cif_nif"/></th>
		                <th><spring:message code = "direccion"/></th>
	                  	<th><spring:message code = "telefono"/></th>
		                <th><spring:message code = "email"/></th>
	             	</tr>
	            </thead>
	            <tbody>
					<tr>
						<td>Nombre</td>
						<td>cif o nif</td>
						<td>Dirección</td>
						<td>Teléfono</td>
						<td>ejemplo@ejemplo.com</td>
					</tr>
				</tbody>
            </table>		
		</div><br><br>		
		<div id="filtro">
            <label><h3><spring:message code="modificar_cliente" /></h3></label>
			<form action="ModificadoAlbaranEntrada" method="POST">
				<label for="n_albaran"><spring:message code="n_albaran" /></label> 
					<input type="text" id="n_albaran" name="n_albaran" value="${albaranEntrada.nAlbaran}" disabled/>
					<input type = "hidden" name = "n_albaran" value = "${albaranEntrada.nAlbaran}"/><!-- Por lo que sea no pilla el n_albaran y lo paso como hidden --><br>
				<label for="n_socio"><spring:message code="n_socio" /></label> 
					<input type="text" id="n_socio" name="n_socio" value="${albaranEntrada.nSocio}"/><br>
				<label for="fecha"><spring:message code="fecha" /></label> 
					<input type="text" id="fecha" name="fecha" value="${albaranEntrada.fechaStr}"/><br>
				<label for="n_factura"><spring:message code="n_factura" /></label> 
					<input type="text" id="n_factura" name="n_factura" value="${albaranEntrada.nFactura}" disabled/>
					<input type = "hidden" name = "n_factura" value = "${albaranEntrada.nFactura}"/><!-- Por lo que sea no pilla el n_factura y lo paso como hidden -->
				<button type="submit" class="btn btn-default btn-sm"><spring:message code = "aceptar"/></button>
			</form>
		</div>
		<!-- LISTA DE ALBARANES -->
		<div id="listado1">
			<table>
				<caption><h5><spring:message code="listado_albaranes" /></h5></caption>
				<thead>
					<tr id="texto">
		                <th><spring:message code = "tipo"/></th>
		                <th><spring:message code = "peso"/></th>
	                  	<th><spring:message code = "precio_kg"/></th>
		                <th><spring:message code = "total"/></th> 
	             	</tr>
            	</thead>
            	<tbody>
					<c:forEach items="${listadoLineas}" var="linea">
					<tr>
						<td>${linea.tipo}</td>
						<td>${linea.peso}</td>
						<td>${linea.precioKg}</td>
						<td>${linea.precioTotal}</td>
						<td title="<spring:message code='eliminar'/>"><form action="eliminarAculumadorE" method="POST">
							<input type="hidden" name="n_albaran" value="${linea.nAlbaran}" />
							<input type="hidden" name = "id_linea_e" value = "${linea.idLinea}"/>
							<button type="submit" class="btn btn-default btn-sm">
							<span  class="glyphicon glyphicon-remove" title="<spring:message code='eliminar'/>"></span>
							</button>
						</form></td>
					
	                </tr>				
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- AÑADIR LINEA DE ALBARAN -->>	
		<div id="listado1">
			<table>
             	<caption><h5><spring:message code="modificar_albaran" /></h5></caption>
				<thead>
					<tr id="texto">
		                <th><spring:message code="tipo" /></th>
		                <th><spring:message code="peso" /></th>
		                <th><spring:message code="precio_kg" /></th>
	             	</tr>
	            </thead>
				<tbody>
					<tr class="fila">
						<form action="addAcumuladorE" method="POST">	
							<td style= "color:black"><select name="tipoCereza">
								<c:forEach items="${listadoTipos}" var="tipo">
									<option value="${tipo.tipo}">${tipo.tipo}</option>
								</c:forEach>
							</select></td><br>
							<td style= "color:black"><input type="text" id="peso" name = "peso" /></td><br>
							<td style= "color:black"><input type="text" id="precioKg" name = "precio_kg" disabled/></td>
				 
							<input type="hidden" name="n_albaran" value="${albaranEntrada.nAlbaran}"/></div>
							<td><button type="submit" class="btn btn-default btn-sm" ><spring:message code = "anadir"/></button></td>
						</form>
					</tr>
				</tbody>
			</table>				
		</div>		
		<div id="listado1">
			<table>
				<thead>
					<tr id="texto">
		                <th><spring:message code="total_albaran" /></th>
		                <th></th>
	             	</tr>
	            </thead>
				<tbody>
					<tr class="fila">	
						<td>${precioTotal}</td>
					</tr>
				</tbody>
			</table>
		</div><br><br>
	  </div>
    </div>
  </section>	 
<c:import url="/WEB-INF/views/end.jsp" />