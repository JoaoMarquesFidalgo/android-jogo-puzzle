-- phpMyAdmin SQL Dump
-- version 4.0.10.14
-- http://www.phpmyadmin.net
--
-- Máquina: localhost:3306
-- Data de Criação: 04-Jun-2018 às 14:14
-- Versão do servidor: 10.0.27-MariaDB-cll-lve
-- versão do PHP: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `jsonbusc_moveis`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `elemento`
--

CREATE TABLE IF NOT EXISTS `elemento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `descricao` text COLLATE utf8_unicode_ci NOT NULL,
  `raridade` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `representacao` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `numero` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Extraindo dados da tabela `elemento`
--

INSERT INTO `elemento` (`id`, `nome`, `descricao`, `raridade`, `representacao`, `numero`) VALUES
(1, 'Ferro', 'O ferro (do latim ferrum) é um elemento químico, símbolo Fe, de número atômico 26 (26 prótons e 26 elétrons) e massa atómica 56 u. À temperatura ambiente, o ferro encontra-se no estado sólido. É extraído da natureza sob a forma de minério de ferro que, depois de passado para o estágio de ferro-gusa, através de processos de transformação, é usado na forma de lingotes. Controlando-se o teor de carbono (o carbono ocorre de forma natural no minério de ferro), dá-se origem a várias formas de aço.', 'Commum', 'FE', 26),
(2, 'Oxigénio', 'O oxigénio (português europeu) ou oxigênio (português brasileiro), é um elemento químico de número atómico 8 e símbolo O (8 protões e 8 eletrões) representando com massa atómica 16 u. Constitui parte do grupo dos calcogénios e é um não metal reactivo e um forte agente oxidante que facilmente forma compostos com a maioria doutros elementos, principalmente óxidos. Tem a segunda electronegatividade mais elevada de todos os elementos químicos, superado apenas pelo flúor.[1] Medido pela sua massa, o oxigénio é o terceiro elemento mais abundante do universo, atrás do hidrogénio e hélio,[2] e o mais abundante na crosta terrestre como parte de compostos óxidos, formando praticamente metade da sua massa.[3] Em condições normais de pressão e temperatura, dois átomos do elemento ligam-se para formar o dioxigénio, um gás diatómico incolor, inodoro e insípido com fórmula O2. Este gás diatómico constitui 20,8% da atmosfera e é fundamental para suportar a vida terrestre.[4] Não obstante, vários estudos dos níveis de oxigénio atmosférico indicam uma progressão global descendente na proporção deste elemento, principalmente por causa das emissões procedentes da queima de combustíveis fósseis.[5]', 'Comum', 'O', 8),
(3, 'Silício', 'O silício (latim: silex, sílex ou "pedra dura") é um elemento químico de símbolo Si de número atômico 14 (14 prótons e 14 elétrons) com massa atómica igual a 28 u. À temperatura ambiente, o silício encontra-se no estado sólido. Foi descoberto pelo químico sueco Jöns Jacob Berzelius, em 1823. O silício é o segundo elemento mais abundante na crosta terrestre, perfazendo mais de 28% de sua massa[1] (atrás somente do Oxigênio e seus 47% de composição da crosta)[2]. Aparece na argila, feldspato, granito, quartzo e areia, normalmente na forma de dióxido de silício (também conhecido como sílica) e silicatos (compostos contendo silício, oxigênio e metais). O silício é o principal componente do vidro, cimento, cerâmica, da maioria dos componentes semicondutores e dos silicones, que são substâncias plásticas muitas vezes confundidas com o silício.[3][2]', 'Comum', 'SI', 14),
(4, 'Magnésio', 'O magnésio é um elemento químico de símbolo Mg de número atômico 12 (12 prótons e 12 elétrons) com massa atômica 24 u. É um metal alcalinoterroso, pertencente ao grupo (ou família) 2 (anteriormente chamada IIA), sólido nas condições ambientais.\r\n\r\nÉ o oitavo elemento mais abundante na crosta terrestre, onde constitui cerca de 2,5% da sua massa,[1] e o nono no Universo conhecido, no seu todo.[2] Esta abundância do magnésio está relacionada com o facto de se formar facilmente em supernovas através da adição sequencial de três núcleos de hélio ao carbono (que é, por sua vez, feito de três núcleos de hélio). A alta solubilidade dos iões de magnésio na água assegura-lhe a posição como terceiro elemento mais abundante na água do mar[3].', 'Comum', 'MG', 12),
(5, 'Enxofre', 'O enxofre (do latim sulphur) é um elemento químico de símbolo S, com número atômico 16 e massa atômica 32 u. À temperatura ambiente, o enxofre encontra-se no estado sólido.\r\n\r\nÉ um não-metal insípido e inodoro,[1](o "cheiro de enxofre" vem de seus compostos voláteis, como o sulfeto de hidrogênio.)[2] facilmente reconhecido na forma de cristais amarelos que ocorrem em diversos minerais de sulfito e sulfato, ou mesmo em sua forma pura (especialmente em regiões vulcânicas). O enxofre é um elemento químico essencial para todos os organismos vivos, sendo constituinte importante de muitos aminoácidos. É utilizado em fertilizantes, além de ser constituinte da pólvora, de medicamentos laxantes, de palitos de fósforos e de inseticidas.', 'Incomum', 'S', 16),
(6, 'Níquel', 'Níquel é um elemento químico de símbolo Ni de número atômico 28 (28 prótons e 28 elétrons) e de massa atómica 58,7 uma. À temperatura ambiente, encontra-se no estado sólido. É um elemento de transição situado no grupo 10 (8 B) da Classificação Periódica dos Elementos.', 'Incomum', 'NI', 28),
(7, 'Cálcio', 'O cálcio é um elemento químico, símbolo Ca, de número atómico 20 (20 prótons e 20 elétrons) e massa atómica 40. É um metal da família dos alcalino-terrosos, pertencente ao grupo 2 da classificação periódica dos elementos químicos.\r\n\r\nFoi isolado pela primeira vez em 1808, em uma forma impura, pelo químico britânico Humphry Davy mediante a eletrólise de uma amálgama de mercúrio (HgO) e cal (CaO).', 'Incomum', 'Ca', 20),
(8, 'Ástato', 'O ástato (também conhecido como astatínio) é um elemento químico de símbolo At e de número atômico igual a 85 (85 prótons e 85 elétrons), com massa atômica de aproximadamente [210] u. É encontrado no Grupo 17 ou VIIA da classificação periódica dos elementos. À temperatura ambiente, o ástato encontra-se no estado sólido. Há atualmente cerca de 31 gramas de ástato na Terra, sendo assim o elemento mais raro de que se tem notícia.\r\n\r\nFoi sintetizado pela primeira vez em 1940 por Dale R. Corson, Kenneth Ross MacKenzie, e Emilio Gino Segrè.', 'Raro', 'AT', 85);

-- --------------------------------------------------------

--
-- Estrutura da tabela `foto`
--

CREATE TABLE IF NOT EXISTS `foto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=123 ;

--
-- Extraindo dados da tabela `foto`
--

INSERT INTO `foto` (`id`, `url`) VALUES
(1, 'carahattrick4.png'),
(2, 'carahattrick2.png'),
(112, 'carahattrick4.png'),
(114, 'carahattrick2.png'),
(115, 'carahattrick1.png'),
(116, 'carahattrick1.png'),
(117, 'carahattrick1.png'),
(118, 'carahattrick1.png'),
(119, 'carahattrick1.png'),
(120, 'carahattrick1.png'),
(121, 'carahattrick1.png'),
(122, 'carahattrick1.png');

-- --------------------------------------------------------

--
-- Estrutura da tabela `opcoes`
--

CREATE TABLE IF NOT EXISTS `opcoes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `som` tinyint(1) DEFAULT NULL,
  `musica` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=106 ;

--
-- Extraindo dados da tabela `opcoes`
--

INSERT INTO `opcoes` (`id`, `som`, `musica`, `url`) VALUES
(1, 1, 'a_97202_mp4', 'texto'),
(2, 1, 'a_97202_mp4', 'texto'),
(94, 0, 'UFO - Rock Bottom', 'song1.mp3'),
(95, 0, 'a_97202_mp4', 'song1.mp3'),
(96, 0, 'a_97202_mp4', 'song1.mp3'),
(97, 0, 'a_97202_mp4', 'song1.mp3'),
(98, 0, 'a_97202_mp4', 'song1.mp3'),
(99, 0, 'a_97202_mp4', 'song1.mp3'),
(100, 0, 'a_97202_mp4', 'song1.mp3'),
(101, 0, 'a_97202_mp4', 'song1.mp3'),
(102, 0, 'a_97202_mp4', 'song1.mp3'),
(103, 0, 'a_97202_mp4', 'song1.mp3'),
(104, 0, 'a_97202_mp4', 'song1.mp3'),
(105, 0, 'a_97202_mp4', 'song1.mp3');

-- --------------------------------------------------------

--
-- Estrutura da tabela `perguntas`
--

CREATE TABLE IF NOT EXISTS `perguntas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_elemento` int(11) NOT NULL,
  `pergunta` text COLLATE utf8_unicode_ci NOT NULL,
  `dificuldade` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Extraindo dados da tabela `perguntas`
--

INSERT INTO `perguntas` (`id`, `id_elemento`, `pergunta`, `dificuldade`) VALUES
(1, 1, 'Qual destes elementos dá origem ao aço?', 1),
(2, 2, 'Qual destes elementos forma a água?', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `planeta`
--

CREATE TABLE IF NOT EXISTS `planeta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `dificuldade` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Extraindo dados da tabela `planeta`
--

INSERT INTO `planeta` (`id`, `nome`, `dificuldade`) VALUES
(1, 'Terra', 1),
(2, 'Mercurio', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `planeta_elemento`
--

CREATE TABLE IF NOT EXISTS `planeta_elemento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_planeta` int(11) NOT NULL,
  `id_elemento` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Extraindo dados da tabela `planeta_elemento`
--

INSERT INTO `planeta_elemento` (`id`, `id_planeta`, `id_elemento`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 4),
(4, 1, 7),
(5, 2, 1),
(6, 2, 4),
(7, 2, 7);

-- --------------------------------------------------------

--
-- Estrutura da tabela `pontuacao`
--

CREATE TABLE IF NOT EXISTS `pontuacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pontuacao` int(11) DEFAULT NULL,
  `tempo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ajuda` int(11) DEFAULT NULL,
  `pontuacao_desaf_1` int(11) NOT NULL,
  `pontuacao_desaf_2` int(11) NOT NULL,
  `pontuacao_desaf_3` int(11) NOT NULL,
  `pontuacao_desaf_4` int(11) NOT NULL,
  `pontuacao_desaf_5` int(11) NOT NULL,
  `pontuacao_desaf_6` int(11) NOT NULL,
  `pontuacao_desaf_7` int(11) NOT NULL,
  `pontuacao_desaf_8` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=106 ;

--
-- Extraindo dados da tabela `pontuacao`
--

INSERT INTO `pontuacao` (`id`, `pontuacao`, `tempo`, `ajuda`, `pontuacao_desaf_1`, `pontuacao_desaf_2`, `pontuacao_desaf_3`, `pontuacao_desaf_4`, `pontuacao_desaf_5`, `pontuacao_desaf_6`, `pontuacao_desaf_7`, `pontuacao_desaf_8`) VALUES
(1, 26, '68', 1, 5, 5, 5, 2, 5, 3, 1, 0),
(2, 0, '0', 0, 0, 0, 0, 0, 0, 0, 0, 0),
(105, 0, '0', 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `utilizador`
--

CREATE TABLE IF NOT EXISTS `utilizador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `facebook` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_planeta` int(11) NOT NULL,
  `id_pontuacao` int(11) NOT NULL,
  `id_opcoes` int(11) NOT NULL,
  `id_foto` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome` (`nome`),
  KEY `id_planeta` (`id_planeta`),
  KEY `id_planeta_2` (`id_planeta`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=90 ;

--
-- Extraindo dados da tabela `utilizador`
--

INSERT INTO `utilizador` (`id`, `nome`, `facebook`, `id_planeta`, `id_pontuacao`, `id_opcoes`, `id_foto`) VALUES
(1, 'j', '100003392841439', 1, 1, 1, 1),
(2, 'zuck', '4', 1, 2, 2, 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
