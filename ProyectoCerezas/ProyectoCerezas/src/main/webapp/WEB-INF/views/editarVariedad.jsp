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
              <label><h1><spring:message code="editar_variedad" /></h1></label>
          </div><br><br>
          <div id="listado">
            <form action="modificadaVariedad" method="POST" >
              <label for="tipo"><spring:message code="variedad"/></label> 
              	<input type="text" id="tipo" name="tipo_variedad" value = "${variedad.tipo}" disabled/><br/><br/>
              	<input type = "hidden" name = "tipo" value = "${variedad.tipo}"/>
              <label for="PrecioKg"><spring:message code="precio_kg" /></label> 
              	<input type="text" id="Precio_kg" name="precio_kg" value = "${variedad.precioKg}"/><br/><br/>
              <label for="pesoCaja"><spring:message code="peso_caja" /></label> 
              	<input type="text" id="precio_caja" name="peso_caja" value = "${variedad.pesoCaja}"/><br/><br/>
              <label for="precioCaja"><spring:message code="precio_caja" /></label> 
              	<input type="text" id="precio_caja" name="precio_caja" value = "${variedad.precioCaja}"/><br/><br/><br>
              <div id="filtro">
                <button type="submit" class="btn btn-lg btn-default"><spring:message code="aceptar"/></button>
              </div>
            </form> 
          </div>
        </div>
      </div> 
    </section> 
<c:import url="/WEB-INF/views/end.jsp" />