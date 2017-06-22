<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:import url="/WEB-INF/views/head.jsp" />
<!--==============================content=================================-->
<!-- FONDO GENERAL-->
<script>
	$(function() {
		//  Initialize Backgound Stretcher
		$('BODY').bgStretcher({
			images : [ 'resources/images/fondoblanco.jpg' ],
			imageWidth : 1600,
			imageHeight : 964,
			resizeProportionally:true
		});
	});
</script>
<section id="content">
	<div class="main-block">
		<div class="main">
			<label><h1><spring:message code="modificar_factura" /></h1></label>
			<div id="listado1">
				<table>
					<caption><h5><spring:message code="datos_empresa" /></h5></caption>
					<thead>
						<tr id="texto">
							<th><spring:message code='nombre_razon_social' /></th>
							<th><spring:message code='cif_nif' /></th>
							<th><spring:message code='direccion' /></th>
							<th><spring:message code='telefono' /></th>
							<th><spring:message code='email' /></th>
						</tr>
					</thead>
					<tbody>
						<tr class="fila">
							<td>Nombre de la empresa</td>
							<td>CIF</td>
							<td>Dirección</td>
							<td>Teléfonos</td>
							<td>E-mail</td>
						</tr>
					</tbody>
				</table>

				<table>
					<caption><h5><spring:message code="datos_cliente" /></h5></caption>
					<thead>
						<tr id="texto">
							<th><spring:message code='nombre_razon_social' /></th>
							<th><spring:message code='apellido' /></th>
							<th><spring:message code='cif_nif' /></th>
							<th><spring:message code='direccion' /></th>
							<th><spring:message code='telefono' /></th>
							<th><spring:message code='email' /></th>
						</tr>
					</thead>
					<tbody>
						<tr class="fila">
							<td>${cliente.nombreRazonSocial}</td>
							<td>${cliente.apellidos}</td>
							<td>${cliente.cifNif}</td>
							<td>${cliente.direccion}</td>
							<td>${cliente.telefono}</td>
							<td>${cliente.email}</td>
						</tr>
					</tbody>
				</table>

				<form action="updateFacturaSalidaFecha" method="POST">
					<table>
						<caption><h5><spring:message code="datos_factura" /></h5></caption>
						<thead>
							<tr id="texto">
								<th><spring:message code='n_factura' /></th>
								<th><spring:message code='fecha' /></th>
							</tr>
						</thead>
						<tbody>
							<tr class="fila">
								<td>${factura.nFactura}</td>
								<td style="color: black"><input type="text" id="fecha" name="fecha"
									value="${factura.stringFecha}" /></td>
								<td><input type="hidden" name="n_factura"
									value="${factura.nFactura}" />
									<button type="submit" class="btn btn-default btn-sm">
										<span class="glyphicon glyphicon-edit"></span>
									</button></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<!-- LISTA DE ALBARANES -->
			<div id="listado1">
				<table>
					<caption><h5><spring:message code="listado_albaranes" /></h5></caption>
					<thead>
						<tr id="texto">
							<th><spring:message code="n_albaran" /></th>
							<th><spring:message code="fecha" /></th>
							<th><spring:message code='tipo' /></th>
							<th><spring:message code='n_cajas' /></th>
							<th><spring:message code='peso_caja' /></th>
							<th><spring:message code='precio_caja' /></th>
							<th><spring:message code='precio_neto' /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${factura.albaranes}" var="albaran">
							<tr class="fila">
								<td>${albaran.nAlbaran}</td>
								<td>${albaran.stringFecha}</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>${albaran.precioNeto}</td>
								<!-- Borrar no borra el albaran, sólo lo quita, es decir pone a cero el valor nFactura del albaran -->
								<td><form action="FacturarAlbaranSalida" method="POST">
										<input type="hidden" name="n_factura" value="${factura.nFactura}" /> 
										<input type="hidden" name="n_albaran" value="${albaran.nAlbaran}" /> 
										<input type="hidden" name="borrar" value="true" />
										<button type="submit" class="btn btn-default btn-sm">
											<span class="glyphicon glyphicon-remove"></span>
										</button>
									</form></td>
							</tr>
						
							<!-- LINEAS DE CADA ALBARAN -->
							<c:forEach items="${albaran.lineas}" var="lineaAlbaran">
								<tr class="fila">
									<td></td>
									<td></td>
									<td>${lineaAlbaran.tipo}</td>
									<td>${lineaAlbaran.nCajas}</td>
									<td>${lineaAlbaran.pesoCaja}</td>
									<td>${lineaAlbaran.precioCaja}</td>
									<td>${lineaAlbaran.precioTotal}</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
				<!-- ALBARANES NO FACTURADOS -->
				<table>
					<caption><h5><spring:message code="pendientes" /></h5></caption>
					<thead>
						<tr id="texto">
							<th><spring:message code="n_albaran" /></th>
							<th><spring:message code="fecha" /></th>
							<th><spring:message code="precio_neto" /></th>
							<th></th>
							<!-- Añade este albaran a la factura en la que estamos, es decir pone el valor nFactura en el albaran -->
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listaPendiente}" var="albaranPendiente">
							<tr class="fila">
								<td>${albaranPendiente.nAlbaran}</td>
								<td>${albaranPendiente.stringFecha}</td>
								<td>${albaranPendiente.precioNeto}</td>
								<td><form action="FacturarAlbaranSalida" method="POST">
										<input type="hidden" name="n_factura"
											value="${factura.nFactura}" /> <input type="hidden"
											name="n_albaran" value="${albaranPendiente.nAlbaran}" /> <input
											type="hidden" name="borrar" value="false" />
										<button type="submit" class="btn btn-default btn-sm">
											<spring:message code="anadir" />
										</button>
									</form></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- TOTALES Y Modificar IVA de la factura, por defecto aparece 21%-->
			<div id="listado1">
				<form action="updateFacturaSalidaIVA" method="POST">
					<table>
						<caption><h5><spring:message code="total_factura" /></h5></caption>
						<thead>
							<tr id="texto">
								<th><spring:message code="precio_neto" /></th>
								<th><spring:message code="iva" /></th>
								<th><spring:message code="totalIva" /></th>
							</tr>
						</thead>
						<tbody>
							<tr class="fila">
								<td style="color: black"><input type="text" id="precioNeto"
									name="precioNeto" value="${factura.precioNeto}" disabled /></td>
								<td style="color: black"><input type="number" id="iva"
									name="iva" value="${factura.iva}" /></td>
								<td style="color: black"><input type="text"
									id="precioTotal" name="precioTotal"
									value="${factura.precioTotal}" disabled /></td>
								<td><input type="hidden" name="n_factura"
									value="${factura.nFactura}" />
									<button type="submit" class="btn btn-default btn-sm">
										<span class="glyphicon glyphicon-edit"></button></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
</section>

<c:import url="/WEB-INF/views/end.jsp" />