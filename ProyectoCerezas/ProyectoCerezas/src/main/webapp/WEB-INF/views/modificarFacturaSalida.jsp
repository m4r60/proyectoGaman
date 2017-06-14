<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- creo que javi lo ha llamado cabecera, si no hay que cambiarlo -->
<c:import url="/WEB-INF/views/head.jsp" />

<div id="datos">
	<h1>
		<spring:message code='datos_empresa' />
	</h1>
	<table>
		<!-- Datos de la empresa -->
		<tr>
			<th><spring:message code='nombre_razon_social' /></th>
			<th><spring:message code='cif_nif' /></th>
			<th><spring:message code='direccion' /></th>
			<th><spring:message code='telefono' /></th>
			<th><spring:message code='email' /></th>
		</tr>
		<tr>
			<!-- Hay que introducir manualmente en el c�digo los datos de la empresa de cerezas -->
			<td>Nombre de la empresa</td>
			<td>CIF</td>
			<td>Direcci�n</td>
			<td>Tel�fonos</td>
			<td>E-mail</td>
		</tr>
	</table>

	<!-- recoger los datos del cliente en la tabla personas para que aparezcan sus datos -->
	<h1>
		<spring:message code='datos_cliente' />
	</h1>
	<table>
		<!-- Datos del cliente -->
		<tr>
			<th><spring:message code='nombre_razon_social' /></th>
			<th><spring:message code='apellido' /></th>
			<th><spring:message code='cif_nif' /></th>
			<th><spring:message code='direccion' /></th>
			<th><spring:message code='telefono' /></th>
			<th><spring:message code='email' /></th>
		</tr>
		<tr>
			<td>${cliente.nombreRazonSocial}</td>
			<td>${cliente.apellido}</td>
			<td>${cliente.cifNif}></td>
			<td>${cliente.direccion}></td>
			<td>${cliente.telefono}></td>
			<td>${cliente.email}></td>
		</tr>
	</table>
</div>

<div id=factura>
	<form action="#" method="POST">
		<!-- Editar Factura, modificar fecha, iva y precio Neto -->

		<div id=cabeceraFactura>
			<table>
				<tr class="fila">
					<th><spring:message code='nFactura' /></th>
					<th><spring:message code='Fecha' /></th>
				</tr>

				<tr class="fila">
					<td><input type="text" id="nFactura" name="nFactura"
						value="${factura.nFactura}" disabled /></td>
					<td><input type="date" id="Fecha" name="Fecha"
						value="${factura.fecha}" /></td>
				</tr>
			</table>
		</div>
		
		<div id="albaranes">
			<table>
				<!-- Cabecera de la tabla -->
				<tr class="fila">
					<th><spring:message code="n_albaran" /></th>
					<th><spring:message code="fecha" /></th>
					<th><spring:message code='tipo' /></th>
					<th><spring:message code='n_cajas' /></th>
					<th><spring:message code='peso_caja' /></th>
					<th><spring:message code='precio_caja' /></th>
					<th><spring:message code='precio_neto' /></th>
					<th><spring:message code='borrar' /></th>
					<!-- Borrar no borra el albaran, s�lo lo quita, es decir pone a cero el valor nFactura del albaran -->
				</tr>
			</table>

			<table>
				<!-- Muestra los albaranes que tiene esta factura -->
				<c:set var="i" value="0" />
				<c:forEach items="${factura.albaranes}" var="albaran"> <!-- Datos de cada albaran -->
					<tr class='fila fila_${i%2 eq 0 ? "par" : "impar"}'>
						<td>${albaran.nAlbaran}</td>
						<td>${albaran.fecha}</td> 
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>${albaran.precioNeto}</td>
						<!-- Borrar no borra el albaran, s�lo lo quita, es decir pone a cero el valor nFactura del albaran -->
						<td><input type="hidden" name="nAlabran" value="nAlbaran" />
							<button type="submit" class="btn btn-default btn-sm"><spring:message code="borrar" /></button></td>
					</tr>
				<!-- Consultar si esto funcionar� o no como espero -->
						<c:forEach items="${albaran.lineas}" var="lineaAlbaran">
							<tr> <!-- Muestra las lineas que tiene cada albaran -->
								<td>${lineaAlbaran.tipo}</td>
								<td>${lineaAlbaran.nCajas}</td>
								<td>${lineaAlbaran.pesoCaja}</td>
								<td>${lineaAlbaran.precioCaja}</td>
								<td>${lineaAlbaran.precioTotal}</td>
							</tr>
						</c:forEach>
				<c:set var="i" value="${i+1}" />
				</c:forEach>
			</table>
		</div>

		<!-- Muestra una lista con los albaranes de este cliente que no est�n facturados -->
		<div id="filtro">
			<table>
				<!-- Cabecera tabla pendientes -->
				<tr>
					<th><spring:message code="n_albaran" /></th>
					<th><spring:message code="fecha" /></th>
					<th><spring:message code='precioNeto' /></th>
					<th><spring:message code='anadir' /></th>
					<!-- A�ade este albaran a la factura en la que estamos, es decir pone el valor nFactura en el albaran -->
				</tr>
			</table>

			<table>
				<!-- Albaranes pendientes, muestra datos de los albaranes de este cliente, pendientes de facturar. Se podrian integrar m�s detalle a esta vista -->
				<c:set var="i" value="0" />
				<c:forEach items="${listaPendiente}" var="albaranPendiente">
					<tr class='fila fila_${i%2 eq 0 ? "par" : "impar"}'>
						<td>${albaranPendiente.nAlbaran}</td>
						<td>${albaranPendiente.fecha}</td>
						<td>${albaranPendiente.precioNeto}</td>
						<td><input type="hidden" name="nAlabran" value="nAlbaran" />
							<button type="submit" class="btn btn-default btn-sm">
								<spring:message code="anadir" />
							</button></td>
					</tr>
				<c:set var="i" value="${i+1}" />
				</c:forEach>
			</table>
		</div>

		<h1>
			<spring:message code='total_factura' />
		</h1>
		<div id="totales">
			<table>
				<tr class="fila">
					<th><spring:message code="precioNeto" /></th>
					<th><spring:message code="iva" /></th>
					<th><spring:message code="totalIva" /></th>
				</tr>

				<tr class="fila">
					<td><input type="text" id="precioNeto" name="precioNeto"
						value="${factura.precioNeto}" disabled /></td>
					<td><input type="number" id="iva" name="iva"
						value="${factura.iva}" /></td>
					<td><input type="text" id="precioTotal" name="precioTotal"
						value="${factura.precioTotal}" disabled /></td>
				</tr>

			</table>
		</div>
		<button type="submit" class="btn btn-default btn-sm"
			onClick="location.href = 'listarFacturaSalida.jsp' ">
			<spring:message code="enviar" />
		</button>

	</form>
</div>	

<c:import url="/WEB-INF/views/end.jsp" />