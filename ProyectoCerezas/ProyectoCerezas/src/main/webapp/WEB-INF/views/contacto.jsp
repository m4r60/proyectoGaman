<%@ page language="java" contentType="text/html;  charset=UTF-8"
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
	         <h1><img class="foto" alt="Grupo Cerezas" src="resources/fotos/contactos.jpg"></h1>
	 	</div>
	 </div>
  </section>
 <c:import url="/WEB-INF/views/end.jsp" />     
