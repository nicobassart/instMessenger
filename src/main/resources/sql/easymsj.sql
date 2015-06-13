-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-06-2015 a las 15:50:59
-- Versión del servidor: 5.6.24
-- Versión de PHP: 5.5.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `easymsj`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `agenda`
--

CREATE TABLE IF NOT EXISTS `agenda` (
  `id_agenda` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(150) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';

--
-- Volcado de datos para la tabla `agenda`
--

INSERT INTO `agenda` (`id_agenda`, `nombre`, `descripcion`) VALUES
(2, 'Agenda de Morosos', 'Deudas de patente'),
(6, 'a', 'a');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aux_agenda_persona`
--

CREATE TABLE IF NOT EXISTS `aux_agenda_persona` (
  `id_agenda` int(11) NOT NULL,
  `id_persona` int(11) NOT NULL,
  `campo1` varchar(45) DEFAULT NULL,
  `campo2` varchar(45) DEFAULT NULL,
  `campo3` varchar(45) DEFAULT NULL,
  `campo4` varchar(45) DEFAULT NULL,
  `id_aux_agenda_persona` int(11) NOT NULL,
  `mensaje_id_mensaje` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';

--
-- Volcado de datos para la tabla `aux_agenda_persona`
--

INSERT INTO `aux_agenda_persona` (`id_agenda`, `id_persona`, `campo1`, `campo2`, `campo3`, `campo4`, `id_aux_agenda_persona`, `mensaje_id_mensaje`) VALUES
(2, 33, NULL, NULL, NULL, NULL, 3, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE IF NOT EXISTS `mensaje` (
  `id_mensaje` int(11) NOT NULL,
  `id_aux_agenda_persona` int(11) DEFAULT NULL,
  `texto` varchar(250) DEFAULT NULL,
  `sentido` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE IF NOT EXISTS `perfil` (
  `nombre` varchar(45) DEFAULT NULL,
  `id_perfil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`nombre`, `id_perfil`) VALUES
('Carga', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE IF NOT EXISTS `persona` (
  `id_persona` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `tipoDocumento` varchar(5) DEFAULT NULL,
  `numeroDocumento` varchar(20) DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `usuario_id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1 COMMENT='latin1_swedish_ci';

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id_persona`, `nombre`, `apellido`, `mail`, `celular`, `tipoDocumento`, `numeroDocumento`, `estado`, `usuario_id_usuario`) VALUES
(33, 'Ariel', 'Brizi', NULL, '111', NULL, NULL, 'ACTIVO', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `nombre` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `id_perfil` int(11) DEFAULT NULL,
  `id_persona` int(11) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`nombre`, `password`, `id_perfil`, `id_persona`, `id_usuario`) VALUES
('nico', 'nico', 1, 33, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `agenda`
--
ALTER TABLE `agenda`
  ADD PRIMARY KEY (`id_agenda`);

--
-- Indices de la tabla `aux_agenda_persona`
--
ALTER TABLE `aux_agenda_persona`
  ADD PRIMARY KEY (`id_aux_agenda_persona`), ADD KEY `fk_aux_agenda_persona_mensaje1_idx` (`mensaje_id_mensaje`), ADD KEY `agenda_persona_ibfk_1` (`id_agenda`), ADD KEY `agenda_persona_ibfk_2` (`id_persona`);

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id_mensaje`);

--
-- Indices de la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`id_perfil`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id_persona`), ADD KEY `numeroDocumento` (`numeroDocumento`), ADD KEY `fk_persona_usuario1_idx` (`usuario_id_usuario`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `agenda`
--
ALTER TABLE `agenda`
  MODIFY `id_agenda` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `aux_agenda_persona`
--
ALTER TABLE `aux_agenda_persona`
  MODIFY `id_aux_agenda_persona` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id_persona` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=34;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `aux_agenda_persona`
--
ALTER TABLE `aux_agenda_persona`
ADD CONSTRAINT `agenda_persona_ibfk_1` FOREIGN KEY (`id_agenda`) REFERENCES `agenda` (`id_agenda`),
ADD CONSTRAINT `agenda_persona_ibfk_2` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`),
ADD CONSTRAINT `fk_aux_agenda_persona_mensaje1` FOREIGN KEY (`mensaje_id_mensaje`) REFERENCES `mensaje` (`id_mensaje`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
ADD CONSTRAINT `fk_persona_usuario1` FOREIGN KEY (`usuario_id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
ADD CONSTRAINT `fk_usuario_perfil1` FOREIGN KEY (`id_usuario`) REFERENCES `perfil` (`id_perfil`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;