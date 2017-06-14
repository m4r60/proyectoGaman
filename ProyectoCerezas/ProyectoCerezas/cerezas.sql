-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-06-2017 a las 14:25:06
-- Versión del servidor: 10.1.21-MariaDB
-- Versión de PHP: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cerezas`
--
CREATE DATABASE IF NOT EXISTS `cerezas` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `cerezas`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `agricultores`
--

DROP TABLE IF EXISTS `agricultores`;
CREATE TABLE `agricultores` (
  `n_socio` int(11) NOT NULL,
  `id_persona` int(11) NOT NULL,
  `baja` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `agricultores`
--

INSERT INTO `agricultores` (`n_socio`, `id_persona`, `baja`) VALUES
(1, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `albaranes_entrada`
--

DROP TABLE IF EXISTS `albaranes_entrada`;
CREATE TABLE `albaranes_entrada` (
  `n_albaran` int(11) NOT NULL,
  `n_socio` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `n_factura` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `albaranes_entrada`
--

INSERT INTO `albaranes_entrada` (`n_albaran`, `n_socio`, `fecha`, `n_factura`) VALUES
(1235, 1, '2017-06-05', NULL),
(1236, 1, '2017-06-14', NULL),
(1237, 1, '2017-06-14', NULL),
(1238, 1, '2017-06-14', NULL),
(1239, 1, '2017-06-14', NULL),
(1240, 1, '2017-06-14', NULL),
(1241, 1, '2017-06-14', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `albaranes_salida`
--

DROP TABLE IF EXISTS `albaranes_salida`;
CREATE TABLE `albaranes_salida` (
  `n_albaran` int(11) NOT NULL,
  `n_cliente` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `n_factura` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `albaranes_salida`
--

INSERT INTO `albaranes_salida` (`n_albaran`, `n_cliente`, `fecha`, `n_factura`) VALUES
(874, 2, '2017-06-08', NULL),
(992, 1, '2017-06-14', NULL),
(993, 1, '2017-06-14', NULL),
(994, 1, '2017-06-14', NULL),
(995, 1, '2017-06-14', NULL),
(996, 1, '2017-06-14', NULL),
(997, 1, '2017-06-14', NULL),
(998, 1, '2017-06-14', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

DROP TABLE IF EXISTS `clientes`;
CREATE TABLE `clientes` (
  `n_cliente` int(11) NOT NULL,
  `id_persona` int(11) NOT NULL,
  `baja` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`n_cliente`, `id_persona`, `baja`) VALUES
(1, 1, 0),
(2, 2, 0),
(9, 9, 1),
(433, 432, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura_e`
--

DROP TABLE IF EXISTS `factura_e`;
CREATE TABLE `factura_e` (
  `iva` int(11) NOT NULL,
  `precio_neto` double(10,2) NOT NULL,
  `n_factura` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `anulacion` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura_s`
--

DROP TABLE IF EXISTS `factura_s`;
CREATE TABLE `factura_s` (
  `n_factura` int(11) NOT NULL,
  `iva` int(11) NOT NULL,
  `precio_neto` double(10,2) NOT NULL,
  `fecha` date NOT NULL,
  `anulacion` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lineas_albaranes_e`
--

DROP TABLE IF EXISTS `lineas_albaranes_e`;
CREATE TABLE `lineas_albaranes_e` (
  `n_albaran` int(11) NOT NULL,
  `id_linea_e` int(11) NOT NULL,
  `tipo` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `peso` double(10,2) NOT NULL,
  `precio_kg` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `lineas_albaranes_e`
--

INSERT INTO `lineas_albaranes_e` (`n_albaran`, `id_linea_e`, `tipo`, `peso`, `precio_kg`) VALUES
(1235, 2, '1', 5645.00, 56456.00),
(1235, 3, '2', 1.00, 1.00),
(1235, 10, '1', 2.00, 2.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lineas_albaranes_s`
--

DROP TABLE IF EXISTS `lineas_albaranes_s`;
CREATE TABLE `lineas_albaranes_s` (
  `n_albaran` int(11) NOT NULL,
  `id_linea` int(11) NOT NULL,
  `tipo` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `numero_cajas` int(11) NOT NULL,
  `peso_caja` double(10,2) NOT NULL,
  `precio_caja` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `lineas_albaranes_s`
--

INSERT INTO `lineas_albaranes_s` (`n_albaran`, `id_linea`, `tipo`, `numero_cajas`, `peso_caja`, `precio_caja`) VALUES
(874, 1, '2', 54, 64.00, 464.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

DROP TABLE IF EXISTS `personas`;
CREATE TABLE `personas` (
  `id_persona` int(11) NOT NULL,
  `cif_nif` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `nombre_razon_social` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `apellidos` varchar(100) COLLATE utf8_spanish_ci DEFAULT NULL,
  `direccion` varchar(200) COLLATE utf8_spanish_ci NOT NULL,
  `telefono` varchar(12) COLLATE utf8_spanish_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id_persona`, `cif_nif`, `nombre_razon_social`, `apellidos`, `direccion`, `telefono`, `email`) VALUES
(1, '123', 'prueba de insercion', 'asdg', 'direc', 'telef', '@'),
(2, '498', 'gsfdgs', 'sfgdf', 'sdfgs', '7987', 'gsfgs@iie.doi'),
(9, '987', 'gqewrqw', 'rqewr', 'qers', '2227', 'gsfgs@ewqrqrewre.doi'),
(432, '8798', 'difqueiour', 'fasdfa', 'adfadsf', '8756', 'adsfa@asjdafs.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `variedades`
--

DROP TABLE IF EXISTS `variedades`;
CREATE TABLE `variedades` (
  `tipo` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `precio_kg` double(10,2) NOT NULL,
  `peso_caja` double(10,2) NOT NULL,
  `precio_caja` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `variedades`
--

INSERT INTO `variedades` (`tipo`, `precio_kg`, `peso_caja`, `precio_caja`) VALUES
('1', 5645.00, 4656.00, 4656.00),
('2', 444.00, 44.00, 44.00),
('3,3', 7.00, 7.00, 7.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `agricultores`
--
ALTER TABLE `agricultores`
  ADD PRIMARY KEY (`n_socio`),
  ADD KEY `id_persona` (`id_persona`);

--
-- Indices de la tabla `albaranes_entrada`
--
ALTER TABLE `albaranes_entrada`
  ADD PRIMARY KEY (`n_albaran`),
  ADD KEY `n_socio` (`n_socio`),
  ADD KEY `fk_albaranes_entrada_factura` (`n_factura`);

--
-- Indices de la tabla `albaranes_salida`
--
ALTER TABLE `albaranes_salida`
  ADD PRIMARY KEY (`n_albaran`),
  ADD KEY `n_cliente` (`n_cliente`),
  ADD KEY `n_factura` (`n_factura`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`n_cliente`),
  ADD KEY `id_persona` (`id_persona`);

--
-- Indices de la tabla `factura_e`
--
ALTER TABLE `factura_e`
  ADD PRIMARY KEY (`n_factura`);

--
-- Indices de la tabla `factura_s`
--
ALTER TABLE `factura_s`
  ADD PRIMARY KEY (`n_factura`);

--
-- Indices de la tabla `lineas_albaranes_e`
--
ALTER TABLE `lineas_albaranes_e`
  ADD PRIMARY KEY (`id_linea_e`),
  ADD KEY `n_albaran` (`n_albaran`),
  ADD KEY `tipo` (`tipo`);

--
-- Indices de la tabla `lineas_albaranes_s`
--
ALTER TABLE `lineas_albaranes_s`
  ADD PRIMARY KEY (`id_linea`),
  ADD KEY `n_albaran` (`n_albaran`),
  ADD KEY `tipo` (`tipo`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id_persona`),
  ADD UNIQUE KEY `cif_nif` (`cif_nif`);

--
-- Indices de la tabla `variedades`
--
ALTER TABLE `variedades`
  ADD PRIMARY KEY (`tipo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `agricultores`
--
ALTER TABLE `agricultores`
  MODIFY `n_socio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `albaranes_entrada`
--
ALTER TABLE `albaranes_entrada`
  MODIFY `n_albaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1242;
--
-- AUTO_INCREMENT de la tabla `albaranes_salida`
--
ALTER TABLE `albaranes_salida`
  MODIFY `n_albaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=999;
--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `n_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=434;
--
-- AUTO_INCREMENT de la tabla `factura_e`
--
ALTER TABLE `factura_e`
  MODIFY `n_factura` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `factura_s`
--
ALTER TABLE `factura_s`
  MODIFY `n_factura` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `lineas_albaranes_e`
--
ALTER TABLE `lineas_albaranes_e`
  MODIFY `id_linea_e` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `lineas_albaranes_s`
--
ALTER TABLE `lineas_albaranes_s`
  MODIFY `id_linea` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=433;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `agricultores`
--
ALTER TABLE `agricultores`
  ADD CONSTRAINT `agricultores_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `albaranes_entrada`
--
ALTER TABLE `albaranes_entrada`
  ADD CONSTRAINT `fk_albaranes_entrada_agricultores` FOREIGN KEY (`n_socio`) REFERENCES `agricultores` (`n_socio`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_albaranes_entrada_factura` FOREIGN KEY (`n_factura`) REFERENCES `factura_e` (`n_factura`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `albaranes_salida`
--
ALTER TABLE `albaranes_salida`
  ADD CONSTRAINT `albaranes_salida_clientes` FOREIGN KEY (`n_cliente`) REFERENCES `clientes` (`n_cliente`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `albaranes_salida_facturas` FOREIGN KEY (`n_factura`) REFERENCES `factura_s` (`n_factura`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `clientes_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
