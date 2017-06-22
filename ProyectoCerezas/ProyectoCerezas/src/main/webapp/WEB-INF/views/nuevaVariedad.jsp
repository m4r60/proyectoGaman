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
              <label><h1><spring:message code="nueva_variedad" /></h1></label>
          </div><br>
          <div id="listado">
              <form action="variedadCreada" method="POST" >
                <label for="tipo"><spring:message code="tipo"/></label> 
                	<input type="text" id="tipo" name="tipo" value = "${tipo}" required/><br/><br/>           
                <label for="PrecioKg"><spring:message code="precio_kg" /></label> 
                	<input type="text" id="Precio_kg" name="precio_kg" placeholder="Precio kg"/><br/><br/>
                <label for="pesoCaja"><spring:message code="peso_caja" /></label> 
                	<input type="text" id="precio_caja" name="peso_caja" placeholder="Peso de la caja"/><br/><br/>
                <label for="precioCaja"><spring:message code="precio_caja" /></label> 
                	<input type="text" id="precio_caja" name="precio_caja" placeholder="Precio de la Caja"/><br/><br/>
                <div id="filtro">          
                  <button type="submit" class="btn btn-lg btn-default"/><spring:message code="aceptar"/></button>
                </div>
              </form> 
          </div>
        </div>
      </div> 
    </section> 
<c:import url="/WEB-INF/views/end.jsp" />