-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 24, 2012 at 07:07 AM
-- Server version: 5.5.24
-- PHP Version: 5.3.10-1ubuntu3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `itrade`
--

-- --------------------------------------------------------

--
-- Table structure for table `Categoria`
--

CREATE TABLE IF NOT EXISTS `Categoria` (
  `IdCategoria` bigint(20) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Categoria`
--

INSERT INTO `Categoria` (`IdCategoria`, `Descripcion`) VALUES
(1, 'comestible'),
(2, 'oxidable');

-- --------------------------------------------------------

--
-- Table structure for table `Cliente`
--

CREATE TABLE IF NOT EXISTS `Cliente` (
  `IdCliente` bigint(20) NOT NULL,
  `IdPersona` bigint(20) DEFAULT NULL,
  `Razon_Social` varchar(100) DEFAULT NULL,
  `RUC` varchar(11) DEFAULT NULL,
  `Latitud` double DEFAULT NULL,
  `Longitud` double DEFAULT NULL,
  `Direccion` varchar(200) DEFAULT NULL,
  `IdCobrador` bigint(20) DEFAULT NULL,
  `IdVendedor` bigint(20) DEFAULT NULL,
  `IdEstado` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IdCliente`),
  KEY `R_10` (`IdPersona`),
  KEY `R_36` (`IdCobrador`),
  KEY `R_37` (`IdVendedor`),
  KEY `R_45` (`IdEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Cliente`
--

INSERT INTO `Cliente` (`IdCliente`, `IdPersona`, `Razon_Social`, `RUC`, `Latitud`, `Longitud`, `Direccion`, `IdCobrador`, `IdVendedor`, `IdEstado`) VALUES
(1, 1, 'SAC', '12354678', 123, 123, 'AV. XXX 123', 1, 1, 1),
(2, 2, 'SAC', '87654321', 321, 321, 'AV. XXX 321', 2, 2, 1),
(3, 3, 'TDP', '12344321', 111, 111, 'AV. XXX 111', 3, 3, 1),
(4, 7, 'JUAN', '66666666666', 100, 200, 'av miraflores 123', 3, 6, 1),
(5, 8, 'JOSE', '77777777777', 200, 100, 'av espinar 123', 3, 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Detalle_Hoja_Ruta`
--

CREATE TABLE IF NOT EXISTS `Detalle_Hoja_Ruta` (
  `Orden` int(11) DEFAULT NULL,
  `IdHoja` bigint(20) NOT NULL,
  `IdCliente` bigint(20) NOT NULL,
  PRIMARY KEY (`IdHoja`,`IdCliente`),
  KEY `R_28` (`IdCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `EstadoCliente`
--

CREATE TABLE IF NOT EXISTS `EstadoCliente` (
  `IdEstado` bigint(20) NOT NULL,
  `Descripcion` char(50) DEFAULT NULL,
  PRIMARY KEY (`IdEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `EstadoCliente`
--

INSERT INTO `EstadoCliente` (`IdEstado`, `Descripcion`) VALUES
(1, 'pendiente'),
(2, 'aprobado');

-- --------------------------------------------------------

--
-- Table structure for table `EstadoPedido`
--

CREATE TABLE IF NOT EXISTS `EstadoPedido` (
  `IdEstadoPedido` bigint(20) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdEstadoPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `EstadoPedido`
--

INSERT INTO `EstadoPedido` (`IdEstadoPedido`, `Descripcion`) VALUES
(1, 'PENDIENTE'),
(2, 'PAGADO'),
(3, 'cancelado');

-- --------------------------------------------------------

--
-- Table structure for table `Evento`
--

CREATE TABLE IF NOT EXISTS `Evento` (
  `IdEvento` bigint(20) NOT NULL,
  `IdCreador` bigint(20) DEFAULT NULL,
  `Descripcion` char(18) DEFAULT NULL,
  `Fecha` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdEvento`),
  KEY `R_39` (`IdCreador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Funcionalidad`
--

CREATE TABLE IF NOT EXISTS `Funcionalidad` (
  `IdFuncionalidad` bigint(20) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdFuncionalidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `FuncxModulo`
--

CREATE TABLE IF NOT EXISTS `FuncxModulo` (
  `IdFuncionalidad` bigint(20) NOT NULL,
  `IdModulo` bigint(20) NOT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdFuncionalidad`,`IdModulo`),
  KEY `R_7` (`IdModulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Hoja_Ruta`
--

CREATE TABLE IF NOT EXISTS `Hoja_Ruta` (
  `IdHoja` bigint(20) NOT NULL,
  `Fecha` date DEFAULT NULL,
  `IdVendedor` bigint(20) DEFAULT NULL,
  `IdCobrador` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IdHoja`),
  KEY `R_29` (`IdVendedor`),
  KEY `R_30` (`IdCobrador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Jerarquia`
--

CREATE TABLE IF NOT EXISTS `Jerarquia` (
  `IdJerarquia` bigint(20) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdJerarquia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Jerarquia`
--

INSERT INTO `Jerarquia` (`IdJerarquia`, `Descripcion`) VALUES
(1, 'JEFE'),
(2, 'SUPERVISOR');

-- --------------------------------------------------------

--
-- Table structure for table `Linea_Credito`
--

CREATE TABLE IF NOT EXISTS `Linea_Credito` (
  `IdLinea` bigint(20) NOT NULL,
  `MontoSolicitado` double DEFAULT NULL,
  `MontoActual` double DEFAULT NULL,
  `MontoAprobado` double DEFAULT NULL,
  `IdCliente` bigint(20) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdLinea`),
  KEY `R_11` (`IdCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Linea_Credito`
--

INSERT INTO `Linea_Credito` (`IdLinea`, `MontoSolicitado`, `MontoActual`, `MontoAprobado`, `IdCliente`, `Activo`) VALUES
(1, 500, 0, 500, 4, '1'),
(2, 3534, 3, 0, 5, '2');

-- --------------------------------------------------------

--
-- Table structure for table `Linea_Pedido`
--

CREATE TABLE IF NOT EXISTS `Linea_Pedido` (
  `IdPedido` bigint(20) NOT NULL,
  `IdProducto` bigint(20) NOT NULL,
  `MontoLinea` double DEFAULT NULL,
  `Cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdPedido`,`IdProducto`),
  KEY `R_14` (`IdProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Linea_Pedido`
--

INSERT INTO `Linea_Pedido` (`IdPedido`, `IdProducto`, `MontoLinea`, `Cantidad`) VALUES
(1, 1, 100, 10),
(1, 2, 20, 3),
(2, 1, 100, 5),
(2, 2, 200, 20),
(3, 3, 50, 4),
(3, 4, 60, 12),
(3, 5, 70, 15),
(4, 4, 200, 40),
(4, 5, 100, 2);

-- --------------------------------------------------------

--
-- Table structure for table `Marca`
--

CREATE TABLE IF NOT EXISTS `Marca` (
  `IdMarca` bigint(20) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdMarca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Marca`
--

INSERT INTO `Marca` (`IdMarca`, `Descripcion`) VALUES
(1, 'toyota'),
(2, 'audi');

-- --------------------------------------------------------

--
-- Table structure for table `Meta`
--

CREATE TABLE IF NOT EXISTS `Meta` (
  `IdMeta` bigint(20) NOT NULL,
  `Monto` double DEFAULT NULL,
  `IdUsuario` bigint(20) DEFAULT NULL,
  `FechaIni` date DEFAULT NULL,
  `FechaFin` date DEFAULT NULL,
  PRIMARY KEY (`IdMeta`),
  KEY `R_31` (`IdUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Meta`
--

INSERT INTO `Meta` (`IdMeta`, `Monto`, `IdUsuario`, `FechaIni`, `FechaFin`) VALUES
(1, 70, 1, '2012-10-01', '2012-10-30'),
(2, 80, 5, '2012-09-01', '2012-09-30'),
(3, 500, 6, '2012-10-11', '2012-10-18');

-- --------------------------------------------------------

--
-- Table structure for table `Modulo`
--

CREATE TABLE IF NOT EXISTS `Modulo` (
  `IdModulo` bigint(20) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdModulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ModuloxPerfil`
--

CREATE TABLE IF NOT EXISTS `ModuloxPerfil` (
  `IdModulo` bigint(20) NOT NULL,
  `IdPerfil` bigint(20) NOT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdModulo`,`IdPerfil`),
  KEY `R_5` (`IdPerfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Pedido`
--

CREATE TABLE IF NOT EXISTS `Pedido` (
  `IdPedido` bigint(20) NOT NULL,
  `IdCliente` bigint(20) NOT NULL,
  `IdEstadoPedido` bigint(20) NOT NULL,
  `CheckIn` int(11) DEFAULT NULL,
  `FechaPedido` date DEFAULT NULL,
  `FechaCobranza` date DEFAULT NULL,
  `MontoSinIGV` double DEFAULT NULL,
  `IGV` double DEFAULT NULL,
  `MontoTotalPedido` double DEFAULT NULL,
  `NumVoucher` varchar(40) DEFAULT NULL,
  `MontoTotalCobrado` double DEFAULT NULL,
  PRIMARY KEY (`IdPedido`),
  KEY `R_12` (`IdCliente`),
  KEY `R_15` (`IdEstadoPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Pedido`
--

INSERT INTO `Pedido` (`IdPedido`, `IdCliente`, `IdEstadoPedido`, `CheckIn`, `FechaPedido`, `FechaCobranza`, `MontoSinIGV`, `IGV`, `MontoTotalPedido`, `NumVoucher`, `MontoTotalCobrado`) VALUES
(1, 1, 1, 1, '2012-10-01', '2012-10-10', 100, 18, 118, '00001111', 118),
(2, 2, 2, 1, '2012-10-02', '2012-10-10', 1000, 180, 1180, '00001112', 1180),
(3, 4, 1, NULL, '2012-10-17', NULL, 100, 18, 118, NULL, NULL),
(4, 4, 2, NULL, '2012-10-17', '2012-10-24', 10, 1.8, 11.8, NULL, 11.8),
(5, 4, 1, NULL, '2012-10-17', NULL, 1000, 180, 1180, NULL, NULL),
(6, 5, 1, NULL, '2012-10-17', NULL, 10000, 1800, 11800, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Perfil`
--

CREATE TABLE IF NOT EXISTS `Perfil` (
  `IdPerfil` bigint(20) NOT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  PRIMARY KEY (`IdPerfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Perfil`
--

INSERT INTO `Perfil` (`IdPerfil`, `Descripcion`, `Activo`) VALUES
(1, 'ADMINISTRADOR', '1'),
(2, 'VENDEDOR', '1'),
(3, 'COBRADOR', '1'),
(4, 'ADMINISTRADOR DE CRÉDITOS', '1');

-- --------------------------------------------------------

--
-- Table structure for table `Persona`
--

CREATE TABLE IF NOT EXISTS `Persona` (
  `IdPersona` bigint(20) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `ApePaterno` varchar(50) DEFAULT NULL,
  `ApeMaterno` varchar(50) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `FechNac` date DEFAULT NULL,
  `DNI` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`IdPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Persona`
--

INSERT INTO `Persona` (`IdPersona`, `Nombre`, `ApePaterno`, `ApeMaterno`, `Telefono`, `Email`, `Activo`, `FechNac`, `DNI`) VALUES
(1, 'ADMINISTRADOR', NULL, NULL, NULL, NULL, '1', NULL, NULL),
(2, 'ALEX', 'PEREZ', 'SOLDADO', '123', 'a20082304@pucp.edu.pe', '1', '1992-06-04', '42123123'),
(3, 'JUSFRAL', 'PUNTO', 'CHOQUEWANKA', '123', 'jusfral@gmail.com', '1', '2012-10-09', '12345678'),
(4, 'ADMINISTRADOR', 'CREDITOS', 'CREDITOS', '123', 'admin@gmail.com', '1', '0000-00-00', '12345678'),
(5, 'ANDREA', 'CACERES', 'CHUMPITAZI', '990936543', 'andrea.caceres@pucp.pe', '1', '1987-09-14', '43816638'),
(6, 'EDER', 'SANTANDER', 'CJUNO', '123456', 'eder@gmail.com', '1', '1990-10-15', '12345678'),
(7, 'cliente 1', '', NULL, '2451245', 'cliente1@mail.com', '1', '1980-10-10', '23423423'),
(8, 'cliente 2', '', NULL, '234234234', 'cliente2@mail.com', '1', '1985-05-25', '42342322'),
(9, 'Juan', 'Del Potro', 'Garcia', '23423423', 'juan@mail.com', '1', '1986-10-16', '23762347');

-- --------------------------------------------------------

--
-- Table structure for table `PersonaXEvento`
--

CREATE TABLE IF NOT EXISTS `PersonaXEvento` (
  `IdEvento` bigint(20) NOT NULL,
  `IdPersona` bigint(20) DEFAULT NULL,
  `Asistir` char(18) DEFAULT NULL,
  PRIMARY KEY (`IdEvento`),
  KEY `R_42` (`IdPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Producto`
--

CREATE TABLE IF NOT EXISTS `Producto` (
  `IdProducto` bigint(20) NOT NULL,
  `Precio` double DEFAULT NULL,
  `Stock` int(11) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `IdCategoria` bigint(20) DEFAULT NULL,
  `IdMarca` bigint(20) DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdProducto`),
  KEY `R_19` (`IdCategoria`),
  KEY `R_38` (`IdMarca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Producto`
--

INSERT INTO `Producto` (`IdProducto`, `Precio`, `Stock`, `Activo`, `IdCategoria`, `IdMarca`, `Descripcion`) VALUES
(1, 10, 5000, '1', 1, 1, 'desc1'),
(2, 20, 5000, '1', 2, 2, 'desc2'),
(3, 30, 6000, '1', 1, 2, 'desc3'),
(4, 40, 5000, '1', 2, 2, 'desc4'),
(5, 560, 5000, '1', 2, 1, 'desc5');

-- --------------------------------------------------------

--
-- Table structure for table `Ubigeo`
--

CREATE TABLE IF NOT EXISTS `Ubigeo` (
  `IdUbigeo` bigint(20) NOT NULL,
  `Pais` varchar(50) DEFAULT NULL,
  `Departamento` varchar(50) DEFAULT NULL,
  `Distrito` varchar(50) DEFAULT NULL,
  `Zona` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdUbigeo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Ubigeo`
--

INSERT INTO `Ubigeo` (`IdUbigeo`, `Pais`, `Departamento`, `Distrito`, `Zona`) VALUES
(1, 'Peru', 'Lima', 'SanMiguel', 'Pando'),
(2, 'Peru', 'Lima', 'La Molina', 'Santa Patricia'),
(3, 'Peru', 'Lima', 'Pueblo Libre', 'Arco Iris');

-- --------------------------------------------------------

--
-- Table structure for table `Usuario`
--

CREATE TABLE IF NOT EXISTS `Usuario` (
  `IdUsuario` bigint(20) NOT NULL,
  `Nombre` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `IdPerfil` bigint(20) NOT NULL,
  `IdPersona` bigint(20) DEFAULT NULL,
  `Activo` char(1) DEFAULT NULL,
  `IdJerarquia` bigint(20) DEFAULT NULL,
  `IdUbigeo` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IdUsuario`),
  KEY `R_1` (`IdPerfil`),
  KEY `R_2` (`IdPersona`),
  KEY `R_26` (`IdJerarquia`),
  KEY `R_44` (`IdUbigeo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Usuario`
--

INSERT INTO `Usuario` (`IdUsuario`, `Nombre`, `Password`, `IdPerfil`, `IdPersona`, `Activo`, `IdJerarquia`, `IdUbigeo`) VALUES
(1, 'admin', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 1, 1, '1', 1, 1),
(2, 'JoX3R', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 3, 2, '1', 1, 1),
(3, 'adg', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 3, 3, '1', 1, 1),
(4, 'eder', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 3, 2, '1', 1, 1),
(5, 'adminc', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 4, 4, '1', 1, 1),
(6, 'chichan', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 2, 3, '1', 1, 1),
(7, 'juan', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 2, 9, '1', 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `Visita`
--

CREATE TABLE IF NOT EXISTS `Visita` (
  `IdVisita` char(18) NOT NULL,
  `FechaVisita` char(18) DEFAULT NULL,
  `IdCliente` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IdVisita`),
  KEY `R_46` (`IdCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Cliente`
--
ALTER TABLE `Cliente`
  ADD CONSTRAINT `Cliente_ibfk_4` FOREIGN KEY (`IdEstado`) REFERENCES `EstadoCliente` (`IdEstado`),
  ADD CONSTRAINT `Cliente_ibfk_1` FOREIGN KEY (`IdPersona`) REFERENCES `Persona` (`IdPersona`),
  ADD CONSTRAINT `Cliente_ibfk_2` FOREIGN KEY (`IdCobrador`) REFERENCES `Usuario` (`IdUsuario`),
  ADD CONSTRAINT `Cliente_ibfk_3` FOREIGN KEY (`IdVendedor`) REFERENCES `Usuario` (`IdUsuario`);

--
-- Constraints for table `Detalle_Hoja_Ruta`
--
ALTER TABLE `Detalle_Hoja_Ruta`
  ADD CONSTRAINT `Detalle_Hoja_Ruta_ibfk_2` FOREIGN KEY (`IdCliente`) REFERENCES `Cliente` (`IdCliente`),
  ADD CONSTRAINT `Detalle_Hoja_Ruta_ibfk_1` FOREIGN KEY (`IdHoja`) REFERENCES `Hoja_Ruta` (`IdHoja`);

--
-- Constraints for table `Evento`
--
ALTER TABLE `Evento`
  ADD CONSTRAINT `Evento_ibfk_1` FOREIGN KEY (`IdCreador`) REFERENCES `Usuario` (`IdUsuario`);

--
-- Constraints for table `FuncxModulo`
--
ALTER TABLE `FuncxModulo`
  ADD CONSTRAINT `FuncxModulo_ibfk_2` FOREIGN KEY (`IdModulo`) REFERENCES `Modulo` (`IdModulo`),
  ADD CONSTRAINT `FuncxModulo_ibfk_1` FOREIGN KEY (`IdFuncionalidad`) REFERENCES `Funcionalidad` (`IdFuncionalidad`);

--
-- Constraints for table `Hoja_Ruta`
--
ALTER TABLE `Hoja_Ruta`
  ADD CONSTRAINT `Hoja_Ruta_ibfk_2` FOREIGN KEY (`IdCobrador`) REFERENCES `Usuario` (`IdUsuario`),
  ADD CONSTRAINT `Hoja_Ruta_ibfk_1` FOREIGN KEY (`IdVendedor`) REFERENCES `Usuario` (`IdUsuario`);

--
-- Constraints for table `Linea_Credito`
--
ALTER TABLE `Linea_Credito`
  ADD CONSTRAINT `Linea_Credito_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `Cliente` (`IdCliente`);

--
-- Constraints for table `Linea_Pedido`
--
ALTER TABLE `Linea_Pedido`
  ADD CONSTRAINT `Linea_Pedido_ibfk_2` FOREIGN KEY (`IdProducto`) REFERENCES `Producto` (`IdProducto`),
  ADD CONSTRAINT `Linea_Pedido_ibfk_1` FOREIGN KEY (`IdPedido`) REFERENCES `Pedido` (`IdPedido`);

--
-- Constraints for table `Meta`
--
ALTER TABLE `Meta`
  ADD CONSTRAINT `Meta_ibfk_1` FOREIGN KEY (`IdUsuario`) REFERENCES `Usuario` (`IdUsuario`);

--
-- Constraints for table `ModuloxPerfil`
--
ALTER TABLE `ModuloxPerfil`
  ADD CONSTRAINT `ModuloxPerfil_ibfk_2` FOREIGN KEY (`IdPerfil`) REFERENCES `Perfil` (`IdPerfil`),
  ADD CONSTRAINT `ModuloxPerfil_ibfk_1` FOREIGN KEY (`IdModulo`) REFERENCES `Modulo` (`IdModulo`);

--
-- Constraints for table `Pedido`
--
ALTER TABLE `Pedido`
  ADD CONSTRAINT `Pedido_ibfk_2` FOREIGN KEY (`IdEstadoPedido`) REFERENCES `EstadoPedido` (`IdEstadoPedido`),
  ADD CONSTRAINT `Pedido_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `Cliente` (`IdCliente`);

--
-- Constraints for table `PersonaXEvento`
--
ALTER TABLE `PersonaXEvento`
  ADD CONSTRAINT `PersonaXEvento_ibfk_2` FOREIGN KEY (`IdPersona`) REFERENCES `Persona` (`IdPersona`),
  ADD CONSTRAINT `PersonaXEvento_ibfk_1` FOREIGN KEY (`IdEvento`) REFERENCES `Evento` (`IdEvento`);

--
-- Constraints for table `Producto`
--
ALTER TABLE `Producto`
  ADD CONSTRAINT `Producto_ibfk_2` FOREIGN KEY (`IdMarca`) REFERENCES `Marca` (`IdMarca`),
  ADD CONSTRAINT `Producto_ibfk_1` FOREIGN KEY (`IdCategoria`) REFERENCES `Categoria` (`IdCategoria`);

--
-- Constraints for table `Usuario`
--
ALTER TABLE `Usuario`
  ADD CONSTRAINT `Usuario_ibfk_1` FOREIGN KEY (`IdPerfil`) REFERENCES `Perfil` (`IdPerfil`),
  ADD CONSTRAINT `Usuario_ibfk_2` FOREIGN KEY (`IdPersona`) REFERENCES `Persona` (`IdPersona`),
  ADD CONSTRAINT `Usuario_ibfk_3` FOREIGN KEY (`IdJerarquia`) REFERENCES `Jerarquia` (`IdJerarquia`),
  ADD CONSTRAINT `Usuario_ibfk_4` FOREIGN KEY (`IdUbigeo`) REFERENCES `Ubigeo` (`IdUbigeo`);

--
-- Constraints for table `Visita`
--
ALTER TABLE `Visita`
  ADD CONSTRAINT `Visita_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `Cliente` (`IdCliente`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
