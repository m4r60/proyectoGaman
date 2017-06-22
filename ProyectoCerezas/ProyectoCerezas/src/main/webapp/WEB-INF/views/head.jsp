<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:import url="/head1.jsp" />
<body>
  <div class="extra-block1"> 
    <!--==============================row-top=================================-->
    <div class="row-top">
      <div class="main">
        <ul class="list-soc">
        </ul>
      </div>
    </div>
      
   	<!--==============================header=================================-->
    <header>
      <div class="row-nav">
        <div class="main">
          <h1 class="logo"><a href="index"><img alt="Grupo Cerezas" src="resources/images/logoNegro.jpg"></a></h1>
          <nav>
            <ul class="menu">
              <li title="<spring:message code='ver_listado_clientes'/>"><a href="listadoClientes"><spring:message code="clientes" /></a></li>
              <li title="<spring:message code='ver_listado_agricultores'/>"><a href="listadoAgricultores"><spring:message code="agricultores" /></a></li>
              <li><a><spring:message code="recogidas" /></a>
                <ul>
                  <li title="<spring:message code='ver_listado_facturas'/>"><a href="listarFacturaEntrada"><spring:message code="facturas" /></a>
                   
                  </li>
                  <li title="<spring:message code='ver_listado_albaranes'/>"><a href="listarAlbaranEntrada"><spring:message code="albaranes" /></a>
                   
                  </li>
                </ul>
              </li>
              <li><a><spring:message code="ventas" /></a>
                <ul>
                  <li title="<spring:message code='ver_listado_facturas'/>"><a href="listarFacturaSalida"><spring:message code="facturas" /></a>
                   
                  </li>
                  <li title="<spring:message code='ver_listado_albaranes'/>"><a href="listarAlbaranSalida"><spring:message code="albaranes" /></a>
                    
                  </li>
                </ul>
              </li>
              <li title="<spring:message code='ver_listado_variedades'/>"><a href="listadoVariedades"><spring:message code="variedades" /></a></li>
              <li><a href="contacto"><spring:message code="contacto" /></a></li>
            </ul>
          </nav>
          <div class="clear"></div>
        </div>
      </div>
    </header>
  </div>