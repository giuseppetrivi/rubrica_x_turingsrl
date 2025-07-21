-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Lug 21, 2025 alle 15:30
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rubrica_x_turingsrl`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `people`
--

CREATE TABLE `people` (
  `id` int(11) NOT NULL,
  `username_fk` varchar(30) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `age` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `people`
--

INSERT INTO `people` (`id`, `username_fk`, `name`, `surname`, `address`, `phone`, `age`) VALUES
(1, 'admin', 'Giuseppe', 'Trivisano', 'Via Pertini', '+39 3110649372', 33),
(2, 'admin', 'Mario', 'Rossi', 'Via Giovanni Paolo II', '+39 3420861723', 25),
(3, 'admin', 'Giovanni', 'Storti', 'Via Kennedy', '3798865740', 68),
(4, 'admin', 'Aldo', 'Baglio', 'Via Aldo Moro', '+39 4361581045', 66),
(6, 'turing', 'John', 'Von Neumann', 'Fuld Hall', '+39 384693566', 54),
(7, 'turing', 'Kurt', 'Godel', 'Via A. Einstein', '+39 3110649372', 72),
(8, 'turing', 'Alonzo', 'Church', 'Via Washington', '+39 4361581045', 92),
(15, 'admin', 'Linus', 'Torvalds', 'Piazza Aperta', '4653930405', 60);

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `username` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`username`, `password`) VALUES
('admin', 'admin'),
('turing', 'alan');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `people`
--
ALTER TABLE `people`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username_fk` (`username_fk`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `people`
--
ALTER TABLE `people`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `people`
--
ALTER TABLE `people`
  ADD CONSTRAINT `username_fk` FOREIGN KEY (`username_fk`) REFERENCES `users` (`username`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
