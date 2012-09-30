-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 30-09-2012 a las 00:45:21
-- Versión del servidor: 5.5.8
-- Versión de PHP: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `itrade`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `IdCategoria` char(18) NOT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `categoria`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE IF NOT EXISTS `ciudad` (
  `IdCiudad` char(18) NOT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  `IdPais` char(18) NOT NULL,
  PRIMARY KEY (`IdCiudad`,`IdPais`),
  KEY `R_24` (`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `ciudad`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `IdCliente` char(18) NOT NULL,
  `IdPersona` int(11) DEFAULT NULL,
  `Razon_Social` char(18) DEFAULT NULL,
  `RUC` char(18) DEFAULT NULL,
  `Latitud` char(18) DEFAULT NULL,
  `Longitud` char(18) DEFAULT NULL,
  `Activo` char(18) DEFAULT NULL,
  `Direccion` char(18) DEFAULT NULL,
  `IdCobrador` int(11) DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdCliente`),
  KEY `R_10` (`IdPersona`),
  KEY `R_36` (`IdCobrador`),
  KEY `R_37` (`IdUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `cliente`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_hoja_ruta`
--

CREATE TABLE IF NOT EXISTS `detalle_hoja_ruta` (
  `Orden` int(11) DEFAULT NULL,
  `IdHoja` int(11) NOT NULL,
  `IdCliente` char(18) NOT NULL,
  PRIMARY KEY (`IdHoja`,`IdCliente`),
  KEY `R_28` (`IdCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `detalle_hoja_ruta`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `distrito`
--

CREATE TABLE IF NOT EXISTS `distrito` (
  `IdDistrito` char(18) NOT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  `IdCiudad` char(18) NOT NULL,
  `IdPais` char(18) NOT NULL,
  PRIMARY KEY (`IdDistrito`,`IdCiudad`,`IdPais`),
  KEY `R_23` (`IdCiudad`,`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `distrito`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estadopedido`
--

CREATE TABLE IF NOT EXISTS `estadopedido` (
  `IdEstadoPedido` char(18) NOT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdEstadoPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `estadopedido`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento`
--

CREATE TABLE IF NOT EXISTS `evento` (
  `IdEvento` char(18) NOT NULL,
  `IdCreador` int(11) DEFAULT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  `Fecha` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdEvento`),
  KEY `R_39` (`IdCreador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `evento`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcionalidad`
--

CREATE TABLE IF NOT EXISTS `funcionalidad` (
  `IdFuncionalidad` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdFuncionalidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `funcionalidad`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `funcxmodulo`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `hoja_ruta`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jerarquia`
--

CREATE TABLE IF NOT EXISTS `jerarquia` (
  `IdJerarquia` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdJerarquia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `jerarquia`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_credito`
--

CREATE TABLE IF NOT EXISTS `linea_credito` (
  `IdLinea` char(18) NOT NULL,
  `MontoSolicitado` char(18) DEFAULT NULL,
  `MontoActual` char(18) DEFAULT NULL,
  `MontoAprobado` char(18) DEFAULT NULL,
  `IdCliente` char(18) DEFAULT NULL,
  `Activo` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdLinea`),
  KEY `R_11` (`IdCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `linea_credito`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_pedido`
--

CREATE TABLE IF NOT EXISTS `linea_pedido` (
  `IdPedido` char(18) NOT NULL,
  `IdProducto` char(18) NOT NULL,
  `MontoTotal` char(18) DEFAULT NULL,
  `MontoParcial` char(18) DEFAULT NULL,
  `IGV` char(18) DEFAULT NULL,
  `Cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdPedido`),
  KEY `R_14` (`IdProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `linea_pedido`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE IF NOT EXISTS `marca` (
  `IdMarca` char(18) NOT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdMarca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `marca`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `meta`
--

CREATE TABLE IF NOT EXISTS `meta` (
  `IdMeta` char(18) NOT NULL,
  `Monto` char(18) DEFAULT NULL,
  `IdUsuario` int(11) DEFAULT NULL,
  `FechaIni` char(18) DEFAULT NULL,
  `FechaFin` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdMeta`),
  KEY `R_31` (`IdUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `meta`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modulo`
--

CREATE TABLE IF NOT EXISTS `modulo` (
  `IdModulo` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdModulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `modulo`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `moduloxperfil`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE IF NOT EXISTS `pais` (
  `IdPais` char(18) NOT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `pais`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE IF NOT EXISTS `pedido` (
  `IdPedido` char(18) NOT NULL,
  `IdCliente` char(18) NOT NULL,
  `IdEstadoPedido` char(18) NOT NULL,
  `CheckIn` char(18) DEFAULT NULL,
  `FechaPedido` char(18) DEFAULT NULL,
  `FechaCobranza` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdPedido`),
  KEY `R_12` (`IdCliente`),
  KEY `R_15` (`IdEstadoPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `pedido`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE IF NOT EXISTS `perfil` (
  `IdPerfil` int(11) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdPerfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`IdPerfil`, `Descripcion`, `Activo`) VALUES
(1, 'Vendedor', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE IF NOT EXISTS `persona` (
  `IdPersona` int(11) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `ApePaterno` varchar(50) DEFAULT NULL,
  `ApeMaterno` varchar(50) DEFAULT NULL,
  `Telefono` char(12) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `FechNac` date DEFAULT NULL,
  `DNI` char(8) DEFAULT NULL,
  PRIMARY KEY (`IdPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `persona`
--

INSERT INTO `persona` (`IdPersona`, `Nombre`, `ApePaterno`, `ApeMaterno`, `Telefono`, `Email`, `Activo`, `FechNac`, `DNI`) VALUES
(1, 'Joel', 'Prada', 'Licla', '5555555', 'japl600@gmail.com', '1', '0000-00-00', '47027556'),
(2, 'Eder', 'Santander', 'Cjuno', '6666666', 'editopatuconsumo@gmail.com', '1', '0000-00-00', '46662166');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personasxevento`
--

CREATE TABLE IF NOT EXISTS `personasxevento` (
  `IdEvento` char(18) NOT NULL,
  `IdPersona` int(11) DEFAULT NULL,
  `Asistir` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdEvento`),
  KEY `R_42` (`IdPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `personasxevento`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `IdProducto` char(18) NOT NULL,
  `Precio` char(18) DEFAULT NULL,
  `Stock` char(18) DEFAULT NULL,
  `Activo` char(18) DEFAULT NULL,
  `IdCategoria` char(18) DEFAULT NULL,
  `IdMarca` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdProducto`),
  KEY `R_19` (`IdCategoria`),
  KEY `R_38` (`IdMarca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `producto`
--


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
  `IdZona` char(18) DEFAULT NULL,
  `IdDistrito` char(18) DEFAULT NULL,
  `IdCiudad` char(18) DEFAULT NULL,
  `IdPais` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdUsuario`),
  KEY `R_1` (`IdPerfil`),
  KEY `R_2` (`IdPersona`),
  KEY `R_26` (`IdJerarquia`),
  KEY `R_34` (`IdZona`,`IdDistrito`,`IdCiudad`,`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`IdUsuario`, `Nombre`, `Password`, `IdPerfil`, `IdPersona`, `Activo`, `IdJerarquia`, `IdZona`, `IdDistrito`, `IdCiudad`, `IdPais`) VALUES
(1, 'adg', 'jmp', 1, 1, '1', NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zona`
--

CREATE TABLE IF NOT EXISTS `zona` (
  `IdZona` char(18) NOT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  `IdDistrito` char(18) NOT NULL,
  `IdCiudad` char(18) NOT NULL,
  `IdPais` char(18) NOT NULL,
  PRIMARY KEY (`IdZona`,`IdDistrito`,`IdCiudad`,`IdPais`),
  KEY `R_22` (`IdDistrito`,`IdCiudad`,`IdPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `zona`
--


--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD CONSTRAINT `ciudad_ibfk_1` FOREIGN KEY (`IdPais`) REFERENCES `pais` (`IdPais`);

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_3` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`IdUsuario`),
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`IdPersona`) REFERENCES `persona` (`IdPersona`),
  ADD CONSTRAINT `cliente_ibfk_2` FOREIGN KEY (`IdCobrador`) REFERENCES `usuario` (`IdUsuario`);

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
  ADD CONSTRAINT `distrito_ibfk_1` FOREIGN KEY (`IdCiudad`, `IdPais`) REFERENCES `ciudad` (`IdCiudad`, `IdPais`);

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
-- Filtros para la tabla `personasxevento`
--
ALTER TABLE `personasxevento`
  ADD CONSTRAINT `personasxevento_ibfk_2` FOREIGN KEY (`IdPersona`) REFERENCES `persona` (`IdPersona`),
  ADD CONSTRAINT `personasxevento_ibfk_1` FOREIGN KEY (`IdEvento`) REFERENCES `evento` (`IdEvento`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_2` FOREIGN KEY (`IdMarca`) REFERENCES `marca` (`IdMarca`),
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`IdCategoria`) REFERENCES `categoria` (`IdCategoria`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_4` FOREIGN KEY (`IdZona`, `IdDistrito`, `IdCiudad`, `IdPais`) REFERENCES `zona` (`IdZona`, `IdDistrito`, `IdCiudad`, `IdPais`),
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`IdPerfil`) REFERENCES `perfil` (`IdPerfil`),
  ADD CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`IdPersona`) REFERENCES `persona` (`IdPersona`),
  ADD CONSTRAINT `usuario_ibfk_3` FOREIGN KEY (`IdJerarquia`) REFERENCES `jerarquia` (`IdJerarquia`);

--
-- Filtros para la tabla `zona`
--
ALTER TABLE `zona`
  ADD CONSTRAINT `zona_ibfk_1` FOREIGN KEY (`IdDistrito`, `IdCiudad`, `IdPais`) REFERENCES `distrito` (`IdDistrito`, `IdCiudad`, `IdPais`);
