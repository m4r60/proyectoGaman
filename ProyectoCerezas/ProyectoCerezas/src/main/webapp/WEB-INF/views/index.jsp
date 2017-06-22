<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:import url="/WEB-INF/views/head.jsp" />
  <!--==============================content=================================-->
    <!-- FONDO DEL INDEX -->
	<script>
	    $(function(){
	      //  Initialize Backgound Stretcher
	      $('BODY').bgStretcher({
	        images: ['resources/images/fondoBlanco1.jpg','resources/images/fondoColor.jpg','resources/images/fondoRojo.jpg','resources/images/fondoNegro.jpg'], 
			    imageWidth: 1600, 
			    imageHeight: 946,
			    resizeProportionally:true,
	    		slideDirection: 'N',
	    		slideShowSpeed: 1000,
	    		transitionEffect: 'fade',
	    		sequenceMode: 'normal',		
	    		pagination: '#nav'
	      });	
	    });
  	</script>
    <section id="content1">
      <div class="policy">
        <div class="main1"></div>
      </div>  
    </section>
<c:import url="/WEB-INF/views/end.jsp" />
