-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 18, 2018 at 04:08 AM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 7.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kartu_mahasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `pengajuan`
--

CREATE TABLE `pengajuan` (
  `id` int(100) NOT NULL,
  `nrp` varchar(9) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `prodi` varchar(25) NOT NULL,
  `kota` varchar(25) NOT NULL,
  `tanggal` date NOT NULL,
  `gender` varchar(7) NOT NULL,
  `status` varchar(7) NOT NULL,
  `validasi` varchar(25) DEFAULT NULL,
  `active` varchar(25) DEFAULT NULL,
  `foto` blob NOT NULL,
  `surat` blob NOT NULL,
  `signature` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengajuan`
--

INSERT INTO `pengajuan` (`id`, `nrp`, `nama`, `prodi`, `kota`, `tanggal`, `gender`, `status`, `validasi`, `active`, `foto`, `surat`, `signature`) VALUES
(11, '151111123', 'Jangkung Ar', 'Teknik Informatika', 'Malang', '1997-06-13', 'Male', 'Baru', 'Belum Divalidasi', 'Inactive', '', '', ''),
(12, '151111123', 'Jangkung Ar', 'Teknik Informatika', 'Malang', '1997-06-13', 'Male', 'Baru', 'Belum Divalidasi', 'Inactive', '', '', ''),
(14, '151111123', 'Jangkung Ari Mukti', 'Teknik Informatika', 'Malang', '1997-06-13', 'Male', 'Hilang', 'Validasi Ditolak', 'Inactive', '', '', ''),
(15, '151111119', 'Alim Arief', 'Teknik Informatika', 'Malang', '1997-07-15', 'Male', 'Baru', 'Validasi Ditolak', 'Inactive', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(5) NOT NULL,
  `username` varchar(9) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `prodi` varchar(25) NOT NULL,
  `tempat_lahir` varchar(15) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `hp` varchar(13) NOT NULL,
  `email` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `level` varchar(1) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `nama`, `prodi`, `tempat_lahir`, `tanggal_lahir`, `hp`, `email`, `gender`, `alamat`, `level`, `password`) VALUES
(5, '151111119', 'Alim Arief', 'Teknik Informatika', 'Malang', '1997-07-15', '081111111111', 'alimarief97@gmail.com', 'Male', 'Pakis', '1', 'asd'),
(2, '151111123', 'Jangkung Ari Mukti', 'Teknik Informatika', 'Malang', '1997-06-13', '081357208008', 'nodameeee@gmail.com', 'Male', 'Sumberpucung', '1', 'asd'),
(4, '162111029', 'Meutia Tivani', 'Desain Komunikasi Visual', 'Malang', '1980-06-13', '0800000000', 'inggrid@stiki.ac.id', 'Female', 'Bromo', '', 'asd'),
(3, 'Admin', 'Admin, M .Kom', 'Dosen', 'Mergosono', '0000-00-00', '081787878787', 'admin@gmail.com', 'Male', 'Kepanjen', '0', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pengajuan`
--
ALTER TABLE `pengajuan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`),
  ADD KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pengajuan`
--
ALTER TABLE `pengajuan`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
