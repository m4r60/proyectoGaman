<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- creo que javi lo ha llamado cabecera, si no hay que cambiarlo -->
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
      	<label><h1><spring:message code="modificar_albaran_salida" /></h1></label>
                  <br>
		<div id="listado1">
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
            <br>
			<br>
			
			<table>
			<caption><h5><spring:message code="datos_cliente" /></h5></caption>
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
		</div>
		<div id="filtro">
              <label><h3><spring:message code="modificar_cliente" /></h3></label>
         
			<form action="ModificadoAlbaranSalida" method="POST">
				<label for="n_albaran"><spring:message code="n_albaran" /></label> 
				<input type="text" id="n_albaran" name="n_albaran" value="${albaranSalida.nAlbaran}" disabled/>
				<input type = "hidden" name = "n_albaran" value = "${albaranSalida.nAlbaran}"/><!-- Por lo que sea no pilla el n_albaran y lo paso como hidden -->
				<br>
				<label for="n_cliente"><spring:message code="n_cliente" /></label> 
				<input type="text" id="n_cliente" name="n_cliente" value="${albaranSalida.nCliente}"/>
				<br>
				<label for="fecha"><spring:message code="fecha" /></label> 
				<input type="text" id="fecha" name="fecha" value="${albaranSalida.fechaStr}"/>
				<br>
				<label for="n_factura"><spring:message code="n_factura" /></label> 
				<input type="text" id="n_factura" name="n_factura" value="${albaranSalida.nFactura}" disabled/>
				<input type = "hidden" name = "n_factura" value = "${albaranSalida.nFactura}"/><!-- Por lo que sea no pilla el n_factura y lo paso como hidden -->
				<button type="submit" class="btn btn-default btn-sm"><spring:message code = "aceptar"/></button>
			</form>
		</div>

	<!-- listado de las lineas introducidas -->
	<div id="listado1">
		<table>
			<caption><h5><spring:message code="listado_albaranes" /></h5></caption>
			<thead>
				<tr id="texto">
	                <th><spring:message code = "tipo"/></th>
	                <th><spring:message code = "n_cajas"/></th>
                  	<th><spring:message code = "peso_caja"/></th>
	                <th><spring:message code = "precio_caja"/></th>
	                <th><spring:message code = "total"/></th> 
             	</tr>
            </thead>
            <tbody>
            <!-- comprobar el controlador porque no vuelca los datos -->
				<c:forEach items="${listadoLineas}" var="linea">
				<tr>
					<td>${linea.tipo}</td>
					<td>${linea.nCajas}</td>
					<td>${linea.pesoCaja}</td>
					<td>${linea.precioCaja}</td>
					<td>${linea.precioTotal}</td>
					<td title="<spring:message code='eliminar'/>"><form action="eliminarAculumador" method="POST">
						<input type="hidden" name="n_albaran" value="${linea.nAlbaran}" />
						<input type="hidden" name = "id_linea" value = "${linea.idLinea}"/>
						<button type="submit" class="btn btn-default btn-sm">
						<span  class="glyphicon glyphicon-remove" title="<spring:message code='eliminar'/>"></span>
						</button>
					</form></td>
				
                </tr>
				
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- añadir lineas de albaranes -->	
	<div id="filtro">
             <label><h3><spring:message code="modificar_albaran" /></h3></label>
         
			<form action="addAcumulador" method="POST">	
				
				<label for="tipo"><spring:message code="tipo" /></label> 
				<select name="tipoCereza">
				<c:forEach items="${listadoTipos}" var="tipo">
					<option value="${tipo.tipo}">${tipo.tipo}</option>
				</c:forEach>
				</select>
				<br>
				
				<label for="n_cajas"><spring:message code="n_cajas" /></label> 
				<input type="text" id="nCaja" name = "n_cajas"/>			<br>
				
				<label for="peso_caja"><spring:message code="peso_caja" /></label> 
				<input type="text" id="pesoCaja" name = "peso_caja" />
				<br>
				
				<label for="precio_caja"><spring:message code="precio_caja" /></label> 
				<input type="text" id="precioCaja" name = "precio_caja" />
				 
				<input type="hidden" name="n_albaran" value="${albaranSalida.nAlbaran}"/></div>
				<button type="submit" class="btn btn-default btn-sm" ><spring:message code = "anadir"/></button>
			</form>
		</div>
		

	<div id="total">
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
	</div>
	<br>
	<br>
	</div>
</section>	
		 
<c:import url="/WEB-INF/views/end.jsp" />