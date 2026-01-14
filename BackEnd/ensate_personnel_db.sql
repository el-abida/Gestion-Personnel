--
-- Database: `ensate_personnel_db`
--

DROP DATABASE IF EXISTS ensate_personnel_db;
CREATE DATABASE ensate_personnel_db
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ensate_personnel_db;

-- --------------------------------------------------------

--
-- Table structure for table `absence`
--

CREATE TABLE `absence` (
  `id` bigint(20) NOT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `type` enum('Maladie','Congé_Annuel','Exceptionnelle','Non_Justifiée') DEFAULT NULL,
  `motif` text DEFAULT NULL,
  `justificatif_url` varchar(255) DEFAULT NULL,
  `est_validee_par_admin` tinyint(1) DEFAULT 0,
  `personnel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `absence`
--

INSERT INTO `absence` (`id`, `date_debut`, `date_fin`, `type`, `motif`, `justificatif_url`, `est_validee_par_admin`, `personnel_id`) VALUES
(1, '2023-01-10', '2023-01-12', 'Maladie', 'Grippe saisonnière', '/justificatifs/absence_1.pdf', 1, 1),
(2, '2023-07-15', '2023-07-29', 'Congé_Annuel', 'Congé annuel d\'été', NULL, 1, 3),
(3, '2023-03-20', '2023-03-20', 'Exceptionnelle', 'Événement familial', '/justificatifs/absence_3.pdf', 1, 5),
(4, '2023-08-01', '2023-08-21', 'Congé_Annuel', 'Congé annuel', NULL, 1, 2),
(5, '2023-05-08', '2023-05-10', 'Maladie', 'Consultation médicale', '/justificatifs/absence_5.pdf', 1, 7),
(6, '2023-06-12', '2023-06-12', 'Exceptionnelle', 'Démarches administratives', '/justificatifs/absence_6.pdf', 0, 4),
(7, '2023-09-04', '2023-09-06', 'Maladie', 'Problème de santé', '/justificatifs/absence_7.pdf', 1, 9),
(8, '2023-04-03', '2023-04-03', 'Non_Justifiée', 'Absence non justifiée', NULL, 0, 6),
(9, '2023-12-18', '2023-12-29', 'Congé_Annuel', 'Congé de fin d\'année', NULL, 1, 8),
(10, '2023-02-15', '2023-02-17', 'Maladie', 'Arrêt maladie', '/justificatifs/absence_10.pdf', 1, 10),
(11, '2023-02-05', '2023-02-07', 'Maladie', 'Consultation médicale', '/justificatifs/absence_11.pdf', 1, 11),
(12, '2023-08-10', '2023-08-24', 'Congé_Annuel', 'Congé annuel d\'été', NULL, 1, 12),
(13, '2023-04-18', '2023-04-18', 'Exceptionnelle', 'Raisons familiales', '/justificatifs/absence_13.pdf', 1, 13),
(14, '2023-07-20', '2023-08-09', 'Congé_Annuel', 'Congé annuel', NULL, 1, 14),
(15, '2023-11-08', '2023-11-10', 'Maladie', 'Arrêt maladie', '/justificatifs/absence_15.pdf', 1, 15),
(16, '2023-03-22', '2023-03-22', 'Non_Justifiée', 'Absence injustifiée', NULL, 0, 16),
(17, '2023-09-11', '2023-09-13', 'Maladie', 'Problème de santé', '/justificatifs/absence_17.pdf', 1, 17),
(18, '2023-06-15', '2023-06-16', 'Exceptionnelle', 'Obligations personnelles', '/justificatifs/absence_18.pdf', 0, 18),
(19, '2023-12-20', '2024-01-02', 'Congé_Annuel', 'Congé de fin d\'année', NULL, 1, 19),
(20, '2023-10-25', '2023-10-27', 'Maladie', 'Certificat médical', '/justificatifs/absence_20.pdf', 1, 20);

-- --------------------------------------------------------

--
-- Table structure for table `avancement`
--

CREATE TABLE `avancement` (
  `id` bigint(20) NOT NULL,
  `date_decision` date DEFAULT NULL,
  `date_effet` date DEFAULT NULL,
  `grade_precedent` varchar(50) DEFAULT NULL,
  `grade_nouveau` varchar(50) DEFAULT NULL,
  `echelle_precedente` int(11) DEFAULT NULL,
  `echelle_nouvelle` int(11) DEFAULT NULL,
  `echelon_precedent` int(11) DEFAULT NULL,
  `echelon_nouveau` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `personnel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `avancement`
--

INSERT INTO `avancement` (`id`, `date_decision`, `date_effet`, `grade_precedent`, `grade_nouveau`, `echelle_precedente`, `echelle_nouvelle`, `echelon_precedent`, `echelon_nouveau`, `description`, `personnel_id`) VALUES
(1, '2015-01-15', '2015-02-01', 'Professeur Assistant', 'Professeur Assistant', 10, 10, 3, 4, 'Avancement d\'échelon suite à l\'ancienneté', 1),
(2, '2019-06-20', '2019-09-01', 'Professeur Assistant', 'Professeur Habilité', 10, 11, 5, 1, 'Promotion au grade de Professeur Habilité après habilitation', 2),
(3, '2018-03-10', '2018-04-01', 'Administrateur Grade 1', 'Administrateur Grade 1', 9, 9, 4, 5, 'Avancement d\'échelon', 4),
(4, '2017-12-05', '2018-01-01', 'Technicien Grade 2', 'Technicien Grade 3', 8, 9, 6, 1, 'Promotion au grade supérieur', 6),
(5, '2020-05-15', '2020-09-01', 'Professeur Habilité', 'Professeur Habilité', 11, 11, 2, 3, 'Avancement d\'échelon normal', 8),
(6, '2021-03-20', '2021-09-01', 'Professeur Habilité', 'Professeur Habilité', 11, 11, 5, 6, 'Avancement d\'échelon régulier', 11),
(7, '2019-11-15', '2020-01-01', 'Technicien Grade 2', 'Technicien Grade 3', 8, 9, 5, 1, 'Promotion suite à l\'ancienneté et performance', 13),
(8, '2021-05-10', '2021-09-01', 'Professeur Assistant', 'Professeur Assistant', 10, 10, 2, 3, 'Avancement normal d\'échelon', 14),
(9, '2016-12-01', '2017-01-01', 'Professeur Assistant', 'Professeur Habilité', 10, 11, 6, 1, 'Promotion après obtention de l\'habilitation universitaire', 17),
(10, '2022-06-20', '2022-09-01', 'Administrateur Grade 2', 'Administrateur Grade 2', 8, 8, 3, 4, 'Avancement d\'échelon', 18);

-- --------------------------------------------------------

--
-- Table structure for table `citoyen`
--

CREATE TABLE `citoyen` (
  `cin` varchar(20) NOT NULL,
  `nom_fr` varchar(100) DEFAULT NULL,
  `nom_ar` varchar(100) DEFAULT NULL,
  `prenom_fr` varchar(100) DEFAULT NULL,
  `prenom_ar` varchar(100) DEFAULT NULL,
  `adresse` text DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `lieu_naissance` varchar(100) DEFAULT NULL,
  `sexe` enum('Masculin','Féminin') DEFAULT NULL,
  `photo_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `citoyen`
--

INSERT INTO `citoyen` (`cin`, `nom_fr`, `nom_ar`, `prenom_fr`, `prenom_ar`, `adresse`, `email`, `telephone`, `date_naissance`, `lieu_naissance`, `sexe`, `photo_url`) VALUES
('AB123890', 'Benkirane', 'بنكيران', 'Mehdi', 'مهدي', '73 Boulevard Mohammed VI, Tanger', 'mehdi.benkirane@yahoo.com', '0644555666', '1990-12-09', 'Tanger', 'Masculin', '/photos/AB123890.jpg'),
('AB234567', 'Alami', 'العلمي', 'Mohammed', 'محمد', '45 Avenue Mohammed V, Tanger', 'mohammed.alami@gmail.com', '0612345678', '1985-03-15', 'Tanger', 'Masculin', '/photos/AB234567.jpg'),
('CD234901', 'Hamdaoui', 'الحمداوي', 'Tarik', 'طارق', '88 Rue Moulay El Mehdi, Tétouan', 'tarik.hamdaoui@gmail.com', '0655666777', '1986-08-21', 'Tétouan', 'Masculin', '/photos/CD234901.jpg'),
('CD456789', 'Chakir', 'شاكر', 'Hassan', 'حسن', '12 Rue de la Liberté, Tétouan', 'hassan.chakir@outlook.com', '0623456789', '1990-07-22', 'Tétouan', 'Masculin', '/photos/CD456789.jpg'),
('EF345012', 'Moussaoui', 'الموسوي', 'Bilal', 'بلال', '19 Rue Targui, Chefchaouen', 'bilal.moussaoui@outlook.com', '0666777888', '1994-03-17', 'Chefchaouen', 'Masculin', '/photos/EF345012.jpg'),
('EF678901', 'Rami', 'الرامي', 'Youssef', 'يوسف', '78 Boulevard Pasteur, Tanger', 'youssef.rami@gmail.com', '0634567890', '1988-11-30', 'Tanger', 'Masculin', '/photos/EF678901.jpg'),
('GH456123', 'Sefrioui', 'الصفريوي', 'Adil', 'عادل', '126 Avenue des FAR, Tanger', 'adil.sefrioui@gmail.com', '0677888999', '1983-07-30', 'Tanger', 'Masculin', '/photos/GH456123.jpg'),
('GH890123', 'Benjelloun', 'بنجلون', 'Ahmed', 'أحمد', '23 Rue Hassan II, Larache', 'ahmed.benjelloun@yahoo.com', '0645678901', '1982-05-18', 'Larache', 'Masculin', '/photos/GH890123.jpg'),
('IJ012345', 'Zerhouni', 'الزرهوني', 'Rachid', 'رشيد', '56 Avenue Moulay Youssef, Tétouan', 'rachid.zerhouni@gmail.com', '0656789012', '1992-09-08', 'Tétouan', 'Masculin', '/photos/IJ012345.jpg'),
('IJ567234', 'Chraibi', 'الشرايبي', 'Samira', 'سميرة', '64 Rue Allal Ben Abdellah, Tétouan', 'samira.chraibi@gmail.com', '0688999000', '1991-05-11', 'Tétouan', 'Féminin', '/photos/IJ567234.jpg'),
('KL234567', 'Tazi', 'التازي', 'Khalid', 'خالد', '89 Rue de Fès, Tanger', 'khalid.tazi@outlook.com', '0667890123', '1986-12-25', 'Tanger', 'Masculin', '/photos/KL234567.jpg'),
('KL678345', 'Zahiri', 'الزهيري', 'Khadija', 'خديجة', '97 Rue de Belgique, Tanger', 'khadija.zahiri@yahoo.com', '0699000111', '1988-09-23', 'Tanger', 'Féminin', '/photos/KL678345.jpg'),
('MN456789', 'Filali', 'الفيلالي', 'Omar', 'عمر', '34 Place Outa Hammam, Chefchaouen', 'omar.filali@gmail.com', '0678901234', '1995-04-12', 'Chefchaouen', 'Masculin', '/photos/MN456789.jpg'),
('MN789456', 'Alaoui', 'العلوي', 'Noura', 'نورة', '52 Avenue Moulay Ismail, Larache', 'noura.alaoui@outlook.com', '0600111222', '1992-11-07', 'Larache', 'Féminin', '/photos/MN789456.jpg'),
('OP678901', 'Lahlou', 'لحلو', 'Fatima', 'فاطمة', '67 Avenue Ibn Batouta, Tanger', 'fatima.lahlou@gmail.com', '0689012345', '1989-06-14', 'Tanger', 'Féminin', '/photos/OP678901.jpg'),
('QR890123', 'Bennis', 'بنيس', 'Karima', 'كريمة', '91 Rue Moulay Abbas, Tétouan', 'karima.bennis@outlook.com', '0690123456', '1991-02-20', 'Tétouan', 'Féminin', '/photos/QR890123.jpg'),
('ST012345', 'Naciri', 'الناصري', 'Leila', 'ليلى', '28 Rue Moulay Ismail, Tanger', 'leila.naciri@yahoo.com', '0601234567', '1987-08-05', 'Tanger', 'Féminin', '/photos/ST012345.jpg'),
('UV345678', 'Amrani', 'العمراني', 'Karim', 'كريم', '102 Rue Ibn Khaldoun, Tanger', 'karim.amrani@gmail.com', '0611222333', '1984-01-28', 'Tanger', 'Masculin', '/photos/UV345678.jpg'),
('WX567890', 'Idrissi', 'الإدريسي', 'Mustapha', 'مصطفى', '55 Avenue Hassan II, Tétouan', 'mustapha.idrissi@outlook.com', '0622333444', '1993-10-05', 'Tétouan', 'Masculin', '/photos/WX567890.jpg'),
('YZ789012', 'Qasmi', 'القاسمي', 'Amine', 'أمين', '41 Place de la Libération, Larache', 'amine.qasmi@gmail.com', '0633444555', '1987-04-16', 'Larache', 'Masculin', '/photos/YZ789012.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `diplome`
--

CREATE TABLE `diplome` (
  `id` bigint(20) NOT NULL,
  `intitule` varchar(100) DEFAULT NULL,
  `specialite` varchar(100) DEFAULT NULL,
  `niveau` enum('Bac','Bac+2','Licence','Master','Ingénieur','Doctorat') DEFAULT NULL,
  `etablissement` varchar(150) DEFAULT NULL,
  `date_obtention` date DEFAULT NULL,
  `fichier_preuve` varchar(255) DEFAULT NULL,
  `personnel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `diplome`
--

INSERT INTO `diplome` (`id`, `intitule`, `specialite`, `niveau`, `etablissement`, `date_obtention`, `fichier_preuve`, `personnel_id`) VALUES
(1, 'Licence en Informatique', 'Génie Informatique', 'Licence', 'Université Abdelmalek Essaâdi', '2006-07-15', '/diplomes/licence_AB234567.pdf', 1),
(2, 'Doctorat en Informatique', 'Intelligence Artificielle', 'Doctorat', 'Université Mohammed V', '2010-06-20', '/diplomes/doctorat_AB234567.pdf', 1),
(3, 'Diplôme Ingénieur', 'Génie Logiciel', 'Ingénieur', 'École Nationale des Sciences Appliquées', '2008-07-01', '/diplomes/ingenieur_CD456789.pdf', 2),
(4, 'Baccalauréat Sciences', 'Sciences Mathématiques', 'Bac', 'Lycée Mohammed V Tanger', '2008-07-01', '/diplomes/bac_EF678901.pdf', 3),
(5, 'DUT Réseaux', 'Réseaux et Télécommunications', 'Bac+2', 'EST Tétouan', '2010-06-15', '/diplomes/dut_EF678901.pdf', 3),
(6, 'Master en Administration', 'Administration Publique', 'Master', 'FSJES Tétouan', '2009-07-15', '/diplomes/master_GH890123.pdf', 4),
(7, 'Diplôme Ingénieur', 'Génie Logiciel', 'Ingénieur', 'École Nationale des Sciences Appliquées', '2008-07-01', '/diplomes/ingenieur_IJ012345.pdf', 5),
(8, 'Baccalauréat Sciences', 'Sciences Mathématiques', 'Bac', 'Lycée Mohammed V Tanger', '2008-07-01', '/diplomes/bac_KL234567.pdf', 6),
(9, 'DUT Réseaux', 'Réseaux et Télécommunications', 'Bac+2', 'EST Tétouan', '2010-06-15', '/diplomes/dut_KL234567.pdf', 6),
(10, 'Master en Administration', 'Administration Publique', 'Master', 'FSJES Tétouan', '2009-07-15', '/diplomes/master_MN456789.pdf', 7),
(11, 'Licence en Informatique', 'Génie Informatique', 'Licence', 'Université Abdelmalek Essaâdi', '2006-07-15', '/diplomes/licence_OP678901.pdf', 8),
(12, 'Doctorat en Informatique', 'Intelligence Artificielle', 'Doctorat', 'Université Mohammed V', '2010-06-20', '/diplomes/doctorat_OP678901.pdf', 8),
(13, 'Baccalauréat', 'Sciences Économiques', 'Bac', 'Lycée Regnault Tanger', '2004-07-01', '/diplomes/bac_QR890123.pdf', 9),
(14, 'Licence en Gestion', 'Management', 'Licence', 'FSJES Tanger', '2007-06-25', '/diplomes/licence_QR890123.pdf', 9),
(15, 'Baccalauréat Sciences', 'Sciences Mathématiques', 'Bac', 'Lycée Mohammed V Tanger', '2008-07-01', '/diplomes/bac_ST012345.pdf', 10),
(16, 'DUT Réseaux', 'Réseaux et Télécommunications', 'Bac+2', 'EST Tétouan', '2010-06-15', '/diplomes/dut_ST012345.pdf', 10),
(17, 'Diplôme Ingénieur', 'Réseaux et Télécommunications', 'Ingénieur', 'INPT Rabat', '2007-07-05', '/diplomes/ingenieur_UV345678.pdf', 11),
(18, 'Master en Gestion', 'Gestion des Ressources Humaines', 'Master', 'ENCG Tanger', '2015-07-20', '/diplomes/master_WX567890.pdf', 12),
(19, 'Baccalauréat Technique', 'Sciences et Technologies', 'Bac', 'Lycée Technique Tétouan', '2009-07-01', '/diplomes/bac_YZ789012.pdf', 13),
(20, 'BTS Informatique', 'Développement de Systèmes d\'Information', 'Bac+2', 'ISTA Tanger', '2011-06-20', '/diplomes/bts_YZ789012.pdf', 13),
(21, 'Licence en Sciences', 'Mathématiques Appliquées', 'Licence', 'Faculté des Sciences Tétouan', '2005-07-10', '/diplomes/licence_AB123890.pdf', 14),
(22, 'Doctorat en Sciences', 'Systèmes Informatiques', 'Doctorat', 'Université Abdelmalek Essaâdi', '2009-06-15', '/diplomes/doctorat_AB123890.pdf', 14),
(23, 'Licence Professionnelle', 'Administration Systèmes et Réseaux', 'Licence', 'EST Tétouan', '2012-07-05', '/diplomes/licence_CD234901.pdf', 15),
(24, 'Master en Gestion', 'Gestion des Ressources Humaines', 'Master', 'ENCG Tanger', '2015-07-20', '/diplomes/master_EF345012.pdf', 16),
(25, 'Diplôme Ingénieur', 'Réseaux et Télécommunications', 'Ingénieur', 'INPT Rabat', '2007-07-05', '/diplomes/ingenieur_GH456123.pdf', 17),
(26, 'Baccalauréat', 'Lettres Modernes', 'Bac', 'Lycée Ibn Batouta Tanger', '2010-07-01', '/diplomes/bac_IJ567234.pdf', 18),
(27, 'Licence en Droit', 'Droit Public', 'Licence', 'Faculté de Droit Tanger', '2013-06-30', '/diplomes/licence_IJ567234.pdf', 18),
(28, 'Diplôme Ingénieur', 'Réseaux et Télécommunications', 'Ingénieur', 'INPT Rabat', '2007-07-05', '/diplomes/ingenieur_KL678345.pdf', 19),
(29, 'Baccalauréat Technique', 'Sciences et Technologies', 'Bac', 'Lycée Technique Tétouan', '2009-07-01', '/diplomes/bac_MN789456.pdf', 20),
(30, 'BTS Informatique', 'Développement de Systèmes d\'Information', 'Bac+2', 'ISTA Tanger', '2011-06-20', '/diplomes/bts_MN789456.pdf', 20);

-- --------------------------------------------------------

--
-- Table structure for table `mission`
--

CREATE TABLE `mission` (
  `id` bigint(20) NOT NULL,
  `destination` varchar(150) DEFAULT NULL,
  `objet_mission` varchar(255) DEFAULT NULL,
  `date_depart` date DEFAULT NULL,
  `date_retour` date DEFAULT NULL,
  `statut` enum('Planifiée','En_Cours','Terminée','Annulée') DEFAULT NULL,
  `rapport_url` varchar(255) DEFAULT NULL,
  `personnel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `mission`
--

INSERT INTO `mission` (`id`, `destination`, `objet_mission`, `date_depart`, `date_retour`, `statut`, `rapport_url`, `personnel_id`) VALUES
(1, 'Rabat', 'Participation au colloque national sur l\'enseignement supérieur', '2023-03-15', '2023-03-17', 'Terminée', '/rapports/mission_1.pdf', 1),
(2, 'Casablanca', 'Conférence internationale IEEE sur l\'intelligence artificielle', '2023-05-20', '2023-05-24', 'Terminée', '/rapports/mission_2.pdf', 2),
(3, 'Marrakech', 'Atelier de formation pédagogique', '2023-10-10', '2023-10-12', 'Terminée', '/rapports/mission_3.pdf', 8),
(4, 'Agadir', 'Séminaire sur les nouvelles technologies éducatives', '2024-01-08', '2024-01-10', 'En_Cours', NULL, 5),
(5, 'Fès', 'Réunion de coordination administrative inter-ENSA', '2024-02-15', '2024-02-16', 'Planifiée', NULL, 4),
(6, 'Paris, France', 'Conférence internationale sur l\'enseignement des sciences', '2023-06-12', '2023-06-16', 'Terminée', '/rapports/mission_6.pdf', 11),
(7, 'Meknès', 'Workshop sur les méthodes pédagogiques innovantes', '2023-11-20', '2023-11-22', 'Terminée', '/rapports/mission_7.pdf', 14),
(8, 'Oujda', 'Symposium sur la recherche scientifique', '2023-04-25', '2023-04-27', 'Terminée', '/rapports/mission_8.pdf', 17),
(9, 'El Jadida', 'Formation continue en gestion administrative', '2024-01-22', '2024-01-24', 'Annulée', NULL, 4),
(10, 'Ifrane', 'Réunion technique inter-établissements', '2024-03-05', '2024-03-06', 'Planifiée', NULL, 13);

-- --------------------------------------------------------

--
-- Table structure for table `personnel`
--

CREATE TABLE `personnel` (
  `id` bigint(20) NOT NULL,
  `cin` varchar(20) DEFAULT NULL,
  `ppr` varchar(30) DEFAULT NULL,
  `type_employe` varchar(50) DEFAULT NULL,
  `date_recrutement_ministere` date DEFAULT NULL,
  `date_recrutement_ensa` date DEFAULT NULL,
  `grade_actuel` varchar(50) DEFAULT NULL,
  `echelle_actuelle` int(11) DEFAULT NULL,
  `echelon_actuel` int(11) DEFAULT NULL,
  `solde_conges` int(11) DEFAULT 0,
  `est_actif` tinyint(1) DEFAULT 1,
  `responsable_rh_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `personnel`
--

INSERT INTO `personnel` (`id`, `cin`, `ppr`, `type_employe`, `date_recrutement_ministere`, `date_recrutement_ensa`, `grade_actuel`, `echelle_actuelle`, `echelon_actuel`, `solde_conges`, `est_actif`, `responsable_rh_id`) VALUES
(1, 'AB234567', 'PPR12345678', 'Professeur', '2010-09-01', '2012-09-01', 'Professeur Assistant', 10, 5, 22, 1, 1),
(2, 'CD456789', 'PPR23456789', 'Professeur', '2015-09-01', '2015-09-01', 'Professeur Habilité', 11, 3, 25, 1, 1),
(3, 'EF678901', 'PPR34567890', 'Technicien', '2013-03-15', '2014-03-15', 'Technicien Grade 2', 8, 4, 18, 1, 2),
(4, 'GH890123', 'PPR45678901', 'Administratif', '2008-02-01', '2010-02-01', 'Administrateur Grade 1', 9, 6, 20, 1, 2),
(5, 'IJ012345', 'PPR56789012', 'Professeur', '2018-09-01', '2018-09-01', 'Professeur Assistant', 10, 2, 28, 1, 1),
(6, 'KL234567', 'PPR67890123', 'Technicien', '2011-06-01', '2012-06-01', 'Technicien Grade 3', 9, 5, 15, 1, 2),
(7, 'MN456789', 'PPR78901234', 'Administratif', '2020-01-15', '2020-01-15', 'Adjoint Administratif', 7, 1, 30, 1, 2),
(8, 'OP678901', 'PPR89012345', 'Professeur', '2014-09-01', '2016-09-01', 'Professeur Habilité', 11, 4, 24, 1, 1),
(9, 'QR890123', 'PPR90123456', 'Administratif', '2016-05-01', '2016-05-01', 'Administrateur Grade 2', 8, 3, 19, 1, 2),
(10, 'ST012345', 'PPR01234567', 'Technicien', '2012-10-01', '2013-10-01', 'Technicien Grade 2', 8, 5, 21, 1, 1),
(11, 'UV345678', 'PPR11234567', 'Professeur', '2009-09-01', '2011-09-01', 'Professeur Habilité', 11, 6, 26, 1, 3),
(12, 'WX567890', 'PPR22345678', 'Administratif', '2019-03-01', '2019-03-01', 'Adjoint Administratif', 7, 2, 27, 1, 4),
(13, 'YZ789012', 'PPR33456789', 'Technicien', '2012-05-01', '2013-05-01', 'Technicien Grade 3', 9, 4, 17, 1, 3),
(14, 'AB123890', 'PPR44567890', 'Professeur', '2016-09-01', '2016-09-01', 'Professeur Assistant', 10, 3, 23, 1, 3),
(15, 'CD234901', 'PPR55678901', 'Technicien', '2011-10-01', '2012-10-01', 'Technicien Grade 2', 8, 5, 16, 1, 4),
(16, 'EF345012', 'PPR66789012', 'Administratif', '2021-02-01', '2021-02-01', 'Adjoint Administratif', 7, 1, 29, 1, 4),
(17, 'GH456123', 'PPR77890123', 'Professeur', '2007-09-01', '2009-09-01', 'Professeur Habilité', 11, 7, 20, 1, 3),
(18, 'IJ567234', 'PPR88901234', 'Administratif', '2017-04-01', '2017-04-01', 'Administrateur Grade 2', 8, 4, 22, 1, 4),
(19, 'KL678345', 'PPR99012345', 'Professeur', '2013-09-01', '2015-09-01', 'Professeur Assistant', 10, 4, 25, 1, 3),
(20, 'MN789456', 'PPR00123456', 'Technicien', '2018-06-01', '2018-06-01', 'Technicien Grade 2', 8, 2, 24, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `responsable_rh`
--

CREATE TABLE `responsable_rh` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `responsable_rh`
--

INSERT INTO `responsable_rh` (`id`, `nom`, `prenom`, `email`, `username`, `password`) VALUES
(1, 'Derraoui', 'Fatima', 'karim.bennani@ensate.ac.ma', 'fatima.rh', '$2a$10$qQXrDJIADzeyyKzBoPk/ROidcVTD5LOVfefObLZ9/YKDHYPb1f2yS'),
(2, 'El Abida', 'Rajae', 'amina.tazi@ensate.ac.ma', 'rajae.rh', '$2a$10$qQXrDJIADzeyyKzBoPk/ROidcVTD5LOVfefObLZ9/YKDHYPb1f2yS'),
(3, 'El Fahssi', 'Chaymae', 'nadia.idrissi@ensate.ac.ma', 'chaymae.rh', '$2a$10$qQXrDJIADzeyyKzBoPk/ROidcVTD5LOVfefObLZ9/YKDHYPb1f2yS'),
(4, 'Maroun', 'Ilias', 'samir.elmansouri@ensate.ac.ma', 'ilias.rh', '$2a$10$qQXrDJIADzeyyKzBoPk/ROidcVTD5LOVfefObLZ9/YKDHYPb1f2yS'),
(5, 'El Hauari', 'Mohamed', 'mohamed@ensate.ac.ma', 'admin.rh', '$2a$10$qQXrDJIADzeyyKzBoPk/ROidcVTD5LOVfefObLZ9/YKDHYPb1f2yS'),
(6, 'EL FAHSSI', 'Chaymae', 'admin@ensa.ma', 'admin', '$2a$10$T4VqnAqsw30/qZ8Nk3V/CeZRx9gHnqhm1JqqoH6eNsdAViaezzhFW'),
(7, 'BENANI', 'Ahmed', 'ahmed.rh@ensa.ma', 'ahmed.rh', '$2a$10$CPcwn/R7eHNrFogUt9itS.qwIFK1skoUjkuIMPWHb4aT3XMILY6Q.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absence`
--
ALTER TABLE `absence`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_absence_personnel` (`personnel_id`);

--
-- Indexes for table `avancement`
--
ALTER TABLE `avancement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_avancement_personnel` (`personnel_id`);

--
-- Indexes for table `citoyen`
--
ALTER TABLE `citoyen`
  ADD PRIMARY KEY (`cin`);

--
-- Indexes for table `diplome`
--
ALTER TABLE `diplome`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_diplome_personnel` (`personnel_id`);

--
-- Indexes for table `mission`
--
ALTER TABLE `mission`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_mission_personnel` (`personnel_id`);

--
-- Indexes for table `personnel`
--
ALTER TABLE `personnel`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cin` (`cin`),
  ADD UNIQUE KEY `ppr` (`ppr`),
  ADD KEY `fk_personnel_responsable` (`responsable_rh_id`);

--
-- Indexes for table `responsable_rh`
--
ALTER TABLE `responsable_rh`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absence`
--
ALTER TABLE `absence`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `avancement`
--
ALTER TABLE `avancement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `diplome`
--
ALTER TABLE `diplome`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `mission`
--
ALTER TABLE `mission`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `personnel`
--
ALTER TABLE `personnel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `responsable_rh`
--
ALTER TABLE `responsable_rh`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absence`
--
ALTER TABLE `absence`
  ADD CONSTRAINT `fk_absence_personnel` FOREIGN KEY (`personnel_id`) REFERENCES `personnel` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `avancement`
--
ALTER TABLE `avancement`
  ADD CONSTRAINT `fk_avancement_personnel` FOREIGN KEY (`personnel_id`) REFERENCES `personnel` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `diplome`
--
ALTER TABLE `diplome`
  ADD CONSTRAINT `fk_diplome_personnel` FOREIGN KEY (`personnel_id`) REFERENCES `personnel` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `mission`
--
ALTER TABLE `mission`
  ADD CONSTRAINT `fk_mission_personnel` FOREIGN KEY (`personnel_id`) REFERENCES `personnel` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `personnel`
--
ALTER TABLE `personnel`
  ADD CONSTRAINT `fk_personnel_citoyen` FOREIGN KEY (`cin`) REFERENCES `citoyen` (`cin`),
  ADD CONSTRAINT `fk_personnel_responsable` FOREIGN KEY (`responsable_rh_id`) REFERENCES `responsable_rh` (`id`);


