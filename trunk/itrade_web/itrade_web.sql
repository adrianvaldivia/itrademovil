-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-10-2012 a las 00:22:28
-- Versión del servidor: 5.5.27-log
-- Versión de PHP: 5.4.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `prueba`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `IdCategoria` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `IdCliente` int(11) NOT NULL,
  `IdPersona` int(11) DEFAULT NULL,
  `Razon_Social` varchar(100) DEFAULT NULL,
  `RUC` varchar(11) DEFAULT NULL,
  `Latitud` double DEFAULT NULL,
  `Longitud` double DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `Direccion` varchar(200) DEFAULT NULL,
  `IdCobrador` int(11) DEFAULT NULL,
  `IdVendedor` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdCliente`),
  KEY `R_10` (`IdPersona`),
  KEY `R_36` (`IdCobrador`),
  KEY `R_37` (`IdVendedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE IF NOT EXISTS `departamento` (
  `IdDepartamento` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `IdPais` int(11) NOT NULL,
  PRIMARY KEY (`IdDepartamento`,`IdPais`),
  KEY `R_24` (`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`IdDepartamento`, `Descripcion`, `IdPais`) VALUES
(1, 'LIMA', 1),
(2, 'HUANCAYO', 1),
(3, 'AREQUIPA', 1),
(4, 'CHIMBOTE', 1),
(6, 'QUITO', 2),
(7, 'SANTIAGO', 3),
(8, 'BUENOS AIRES', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_hoja_ruta`
--

CREATE TABLE IF NOT EXISTS `detalle_hoja_ruta` (
  `Orden` int(11) DEFAULT NULL,
  `IdHoja` int(11) NOT NULL,
  `IdCliente` int(11) NOT NULL,
  PRIMARY KEY (`IdHoja`,`IdCliente`),
  KEY `R_28` (`IdCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `distrito`
--

CREATE TABLE IF NOT EXISTS `distrito` (
  `IdDistrito` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `IdDepartamento` int(11) NOT NULL,
  `IdPais` int(11) NOT NULL,
  PRIMARY KEY (`IdDistrito`,`IdDepartamento`,`IdPais`),
  KEY `R_23` (`IdDepartamento`,`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `distrito`
--

INSERT INTO `distrito` (`IdDistrito`, `Descripcion`, `IdDepartamento`, `IdPais`) VALUES
(1, 'JESUS MARÍA', 1, 1),
(2, 'LINCE', 1, 1),
(3, 'PUEBLO LIBRE', 1, 1),
(4, 'SAN MIGUEL', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estadopedido`
--

CREATE TABLE IF NOT EXISTS `estadopedido` (
  `IdEstadoPedido` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdEstadoPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento`
--

CREATE TABLE IF NOT EXISTS `evento` (
  `IdEvento` int(11) NOT NULL,
  `IdCreador` int(11) DEFAULT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  `Fecha` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdEvento`),
  KEY `R_39` (`IdCreador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcionalidad`
--

CREATE TABLE IF NOT EXISTS `funcionalidad` (
  `IdFuncionalidad` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdFuncionalidad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcxmodulo`
--

CREATE TABLE IF NOT EXISTS `funcxmodulo` (
  `IdFuncionalidad` int(11) NOT NULL,
  `IdModulo` int(11) NOT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdFuncionalidad`,`IdModulo`),
  KEY `R_7` (`IdModulo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hoja_ruta`
--

CREATE TABLE IF NOT EXISTS `hoja_ruta` (
  `IdHoja` int(11) NOT NULL,
  `Fecha` date DEFAULT NULL,
  `IdVendedor` int(11) DEFAULT NULL,
  `IdCobrador` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdHoja`),
  KEY `R_29` (`IdVendedor`),
  KEY `R_30` (`IdCobrador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jerarquia`
--

CREATE TABLE IF NOT EXISTS `jerarquia` (
  `IdJerarquia` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdJerarquia`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `jerarquia`
--

INSERT INTO `jerarquia` (`IdJerarquia`, `Descripcion`) VALUES
(1, 'VENDEDOR'),
(2, 'JEFE DE ZONA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_credito`
--

CREATE TABLE IF NOT EXISTS `linea_credito` (
  `IdLinea` int(11) NOT NULL,
  `MontoSolicitado` double DEFAULT NULL,
  `MontoActual` double DEFAULT NULL,
  `MontoAprobado` double DEFAULT NULL,
  `IdCliente` int(11) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdLinea`),
  KEY `R_11` (`IdCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_pedido`
--

CREATE TABLE IF NOT EXISTS `linea_pedido` (
  `IdPedido` int(11) NOT NULL,
  `IdProducto` int(11) NOT NULL,
  `MontoLinea` double DEFAULT NULL,
  `Cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdPedido`,`IdProducto`),
  KEY `R_14` (`IdProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE IF NOT EXISTS `marca` (
  `IdMarca` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdMarca`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `meta`
--

CREATE TABLE IF NOT EXISTS `meta` (
  `IdMeta` int(11) NOT NULL,
  `Monto` double DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `FechaIni` date DEFAULT NULL,
  `FechaFin` date DEFAULT NULL,
  PRIMARY KEY (`IdMeta`),
  KEY `R_31` (`IdUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modulo`
--

CREATE TABLE IF NOT EXISTS `modulo` (
  `IdModulo` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdModulo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `moduloxperfil`
--

CREATE TABLE IF NOT EXISTS `moduloxperfil` (
  `IdModulo` int(11) NOT NULL,
  `IdPerfil` int(11) NOT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdModulo`,`IdPerfil`),
  KEY `R_5` (`IdPerfil`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE IF NOT EXISTS `pais` (
  `IdPais` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`IdPais`, `Descripcion`) VALUES
(1, 'PERÚ'),
(2, 'ECUADOR'),
(3, 'CHILE'),
(4, 'ARGENTINA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE IF NOT EXISTS `pedido` (
  `IdPedido` int(11) NOT NULL,
  `IdCliente` int(11) NOT NULL,
  `IdEstadoPedido` int(11) NOT NULL,
  `CheckIn` int(11) DEFAULT NULL,
  `FechaPedido` date DEFAULT NULL,
  `FechaCobranza` date DEFAULT NULL,
  `MontoSinIGV` double DEFAULT NULL,
  `IGV` double DEFAULT NULL,
  `MontoTotal` double DEFAULT NULL,
  PRIMARY KEY (`IdPedido`),
  KEY `R_12` (`IdCliente`),
  KEY `R_15` (`IdEstadoPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE IF NOT EXISTS `perfil` (
  `IdPerfil` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdPerfil`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`IdPerfil`, `Descripcion`, `Activo`) VALUES
(1, 'ADMINISTRADOR', '1'),
(2, 'VENDEDOR', '1'),
(3, 'COBRADOR', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE IF NOT EXISTS `persona` (
  `IdPersona` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `ApePaterno` varchar(50) DEFAULT NULL,
  `ApeMaterno` varchar(50) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `FechNac` date DEFAULT NULL,
  `DNI` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`IdPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`IdPersona`, `Nombre`, `ApePaterno`, `ApeMaterno`, `Telefono`, `Email`, `Activo`, `FechNac`, `DNI`) VALUES
(1, 'ANDREA', 'CÁCERES', 'CHUMPITAZI', '990936543', 'andrea.caceres@pucp.pe', '1', '1987-09-14', '43816638'),
(2, 'CARLOS', 'GARCÍA', 'CÉSPEDES', '993569869', 'carlosg1712@gmail.com', '1', '1989-12-17', '46116007'),
(3, 'LUIS', 'CHUMPITAZ', 'IGNACIO', '984692181', 'luisenrique.chumpitaz@gmail.com', '1', '1991-03-30', '46916139');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personaxevento`
--

CREATE TABLE IF NOT EXISTS `personaxevento` (
  `IdEvento` int(11) NOT NULL,
  `IdPersona` int(11) DEFAULT NULL,
  `Asistir` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdEvento`),
  KEY `R_42` (`IdPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `IdProducto` int(11) NOT NULL,
  `Precio` double DEFAULT NULL,
  `Stock` int(11) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `IdCategoria` int(11) DEFAULT NULL,
  `IdMarca` int(11) DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdProducto`),
  KEY `R_19` (`IdCategoria`),
  KEY `R_38` (`IdMarca`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ubigeo`
--

CREATE TABLE IF NOT EXISTS `ubigeo` (
  `IdUbigeo` int(11) NOT NULL,
  `IdZona` int(11) DEFAULT NULL,
  `IdDistrito` int(11) DEFAULT NULL,
  `IdDepartamento` int(11) DEFAULT NULL,
  `IdPais` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdUbigeo`),
  KEY `R_43` (`IdZona`,`IdDistrito`,`IdDepartamento`,`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ubigeo`
--

INSERT INTO `ubigeo` (`IdUbigeo`, `IdZona`, `IdDistrito`, `IdDepartamento`, `IdPais`) VALUES
(1, 1, 1, 1, 1),
(2, 2, 2, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `IdUsuario` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Password` varchar(20) DEFAULT NULL,
  `IdPerfil` int(11) NOT NULL,
  `IdPersona` int(11) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `IdJerarquia` int(11) DEFAULT NULL,
  `IdUbigeo` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdUsuario`),
  KEY `R_1` (`IdPerfil`),
  KEY `R_2` (`IdPersona`),
  KEY `R_26` (`IdJerarquia`),
  KEY `R_44` (`IdUbigeo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`IdUsuario`, `Nombre`, `Password`, `IdPerfil`, `IdPersona`, `Activo`, `IdJerarquia`, `IdUbigeo`) VALUES
(1, 'andrea.caceres', '1234', 1, 1, '1', 1, 1),
(2, 'luis.chumpitaz', '9876', 1, 3, '1', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zona`
--

CREATE TABLE IF NOT EXISTS `zona` (
  `IdZona` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `IdDistrito` int(11) NOT NULL,
  `IdDepartamento` int(11) NOT NULL,
  `IdPais` int(11) NOT NULL,
  PRIMARY KEY (`IdZona`,`IdDistrito`,`IdDepartamento`,`IdPais`),
  KEY `R_22` (`IdDistrito`,`IdDepartamento`,`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `zona`
--

INSERT INTO `zona` (`IdZona`, `Descripcion`, `IdDistrito`, `IdDepartamento`, `IdPais`) VALUES
(1, 'ZONA01', 1, 1, 1),
(2, 'ZONA02', 2, 1, 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_3` FOREIGN KEY (`IdVendedor`) REFERENCES `usuario` (`IdUsuario`),
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`IdPersona`) REFERENCES `persona` (`IdPersona`),
  ADD CONSTRAINT `cliente_ibfk_2` FOREIGN KEY (`IdCobrador`) REFERENCES `usuario` (`IdUsuario`);

--
-- Filtros para la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD CONSTRAINT `departamento_ibfk_1` FOREIGN KEY (`IdPais`) REFERENCES `pais` (`IdPais`);

--
-- Filtros para la tabla `detalle_hoja_ruta`
--
ALTER TABLE `detalle_hoja_ruta`
  ADD CONSTRAINT `detalle_hoja_ruta_ibfk_2` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`IdCliente`),
  ADD CONSTRAINT `detalle_hoja_ruta_ibfk_1` FOREIGN KEY (`IdHoja`) REFERENCES `hoja_ruta` (`IdHoja`);

--
-- Filtros para la tabla `distrito`
--
ALTER TABLE `distrito`
  ADD CONSTRAINT `distrito_ibfk_1` FOREIGN KEY (`IdDepartamento`, `IdPais`) REFERENCES `departamento` (`IdDepartamento`, `IdPais`);

--
-- Filtros para la tabla `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`IdCreador`) REFERENCES `usuario` (`IdUsuario`);

--
-- Filtros para la tabla `funcxmodulo`
--
ALTER TABLE `funcxmodulo`
  ADD CONSTRAINT `funcxmodulo_ibfk_2` FOREIGN KEY (`IdModulo`) REFERENCES `modulo` (`IdModulo`),
  ADD CONSTRAINT `funcxmodulo_ibfk_1` FOREIGN KEY (`IdFuncionalidad`) REFERENCES `funcionalidad` (`IdFuncionalidad`);

--
-- Filtros para la tabla `hoja_ruta`
--
ALTER TABLE `hoja_ruta`
  ADD CONSTRAINT `hoja_ruta_ibfk_2` FOREIGN KEY (`IdCobrador`) REFERENCES `usuario` (`IdUsuario`),
  ADD CONSTRAINT `hoja_ruta_ibfk_1` FOREIGN KEY (`IdVendedor`) REFERENCES `usuario` (`IdUsuario`);

--
-- Filtros para la tabla `linea_credito`
--
ALTER TABLE `linea_credito`
  ADD CONSTRAINT `linea_credito_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`IdCliente`);

--
-- Filtros para la tabla `linea_pedido`
--
ALTER TABLE `linea_pedido`
  ADD CONSTRAINT `linea_pedido_ibfk_2` FOREIGN KEY (`IdProducto`) REFERENCES `producto` (`IdProducto`),
  ADD CONSTRAINT `linea_pedido_ibfk_1` FOREIGN KEY (`IdPedido`) REFERENCES `pedido` (`IdPedido`);

--
-- Filtros para la tabla `meta`
--
ALTER TABLE `meta`
  ADD CONSTRAINT `meta_ibfk_1` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`IdUsuario`);

--
-- Filtros para la tabla `moduloxperfil`
--
ALTER TABLE `moduloxperfil`
  ADD CONSTRAINT `moduloxperfil_ibfk_2` FOREIGN KEY (`IdPerfil`) REFERENCES `perfil` (`IdPerfil`),
  ADD CONSTRAINT `moduloxperfil_ibfk_1` FOREIGN KEY (`IdModulo`) REFERENCES `modulo` (`IdModulo`);

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`IdEstadoPedido`) REFERENCES `estadopedido` (`IdEstadoPedido`),
  ADD CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `cliente` (`IdCliente`);

--
-- Filtros para la tabla `personaxevento`
--
ALTER TABLE `personaxevento`
  ADD CONSTRAINT `personaxevento_ibfk_2` FOREIGN KEY (`IdPersona`) REFERENCES `persona` (`IdPersona`),
  ADD CONSTRAINT `personaxevento_ibfk_1` FOREIGN KEY (`IdEvento`) REFERENCES `evento` (`IdEvento`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`IdMarca`) REFERENCES `marca` (`IdMarca`),
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`IdCategoria`) REFERENCES `categoria` (`IdCategoria`);

--
-- Filtros para la tabla `ubigeo`
--
ALTER TABLE `ubigeo`
  ADD CONSTRAINT `ubigeo_ibfk_1` FOREIGN KEY (`IdZona`, `IdDistrito`, `IdDepartamento`, `IdPais`) REFERENCES `zona` (`IdZona`, `IdDistrito`, `IdDepartamento`, `IdPais`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_4` FOREIGN KEY (`IdUbigeo`) REFERENCES `ubigeo` (`IdUbigeo`),
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`IdPerfil`) REFERENCES `perfil` (`IdPerfil`),
  ADD CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`IdPersona`) REFERENCES `persona` (`IdPersona`),
  ADD CONSTRAINT `usuario_ibfk_3` FOREIGN KEY (`IdJerarquia`) REFERENCES `jerarquia` (`IdJerarquia`);

--
-- Filtros para la tabla `zona`
--
ALTER TABLE `zona`
  ADD CONSTRAINT `zona_ibfk_1` FOREIGN KEY (`IdDistrito`, `IdDepartamento`, `IdPais`) REFERENCES `distrito` (`IdDistrito`, `IdDepartamento`, `IdPais`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
