# 📱 Système de Gestion du Personnel - ENSA Tétouan

## 📋 Description

Application complète de gestion du personnel développée pour l'École Nationale des Sciences Appliquées (ENSA) de Tétouan. Ce système permet de gérer efficacement les informations du personnel, les absences, les missions, les diplômes et les avancements de carrière.

Le projet suit une architecture client-serveur moderne avec:

- **Frontend**: Application Android native développée en Kotlin utilisant l'architecture MVVM (Model-View-ViewModel)
- **Backend**: API REST développée avec Spring Boot 4.0.1 et Java 21

---

## 🏗️ Architecture

### Frontend (Android - Kotlin)

**Patterns utilisés:**
- **MVVM** (Model-View-ViewModel)
- **Repository Pattern** pour l'abstraction des sources de données
- **Dependency Injection** avec Hilt/Dagger
- **Single Activity Architecture** avec Navigation Component
- **Coroutines** pour la programmation asynchrone

### Backend (Spring Boot - Java)

**Technologies Backend:**
- Spring Boot 4.0.1
- Spring Data JPA (Hibernate)
- Spring Security
- Spring Validation
- MySQL Database
- Lombok
- Maven

---

## ✨ Fonctionnalités principales

### 🔐 Authentification
- Connexion sécurisée avec gestion de session
- Authentification par token
- Gestion des rôles et permissions

### 👥 Gestion du Personnel
- CRUD complet (Créer, Lire, Modifier, Supprimer)
- Recherche et filtrage du personnel
- Recherche par PPR (numéro d'identification)

### 📊 Tableau de Bord
- Vue d'ensemble des statistiques
- Données mensuelles et graphiques
- Indicateurs de performance

### 🏖️ Gestion des Absences
- Types d'absences: Congé annuel, Maladie, Exceptionnelle, Non justifiée
- Validation/Invalidation des absences
- Gestion des justificatifs
- Calcul automatique de la durée

### 🌍 Gestion des Missions
- Création et suivi des missions
- Affectation aux membres du personnel
- Clôture des missions

### 🎓 Gestion des Diplômes
- Enregistrement des diplômes
- Association avec le personnel
- Consultation de l'historique académique

### 📈 Gestion des Avancements
- Suivi des avancements de carrière
- Historique des promotions
- Gestion des grades et échelons

---

## 🛠️ Technologies utilisées

### Frontend Android

| Catégorie | Technologies |
|-----------|-------------|
| **Langage** | Kotlin |
| **Architecture** | MVVM, Clean Architecture |
| **UI** | Material Design 3, ViewBinding, DataBinding |
| **Navigation** | Navigation Component, Safe Args |
| **Réseau** | Retrofit 2.9.0, OkHttp 4.12.0 |
| **Injection de dépendances** | Hilt/Dagger 2.47 |
| **Asynchrone** | Coroutines 1.7.3 |
| **Stockage local** | DataStore Preferences |
| **Sérialisation** | Gson 2.10.1 |
| **Images** | Glide 4.16.0 |
| **Graphiques** | MPAndroidChart 3.1.0 |

**Versions:**
- Android SDK Min: 24 (Android 7.0)
- Android SDK Target: 34 (Android 14)

### Backend Spring Boot

| Catégorie | Technologies |
|-----------|-------------|
| **Langage** | Java 21 |
| **Framework** | Spring Boot 4.0.1 |
| **Modules Spring** | Spring Web MVC, Spring Data JPA, Spring Security, Spring Validation |
| **Base de données** | MySQL 8.0+ |
| **ORM** | Hibernate (via Spring Data JPA) |
| **Build** | Maven |
| **Utilitaires** | Lombok |

---

## 📦 Installation et Configuration

### Prérequis

#### Pour le Backend:
- ✅ **Java Development Kit (JDK) 21** ou supérieur
- ✅ **Maven 3.8+** (ou utiliser le wrapper Maven inclus)
- ✅ **MySQL 8.0+** installé et en cours d'exécution
- ✅ **IDE recommandé**: IntelliJ IDEA, Eclipse, ou VS Code

#### Pour le Frontend:
- ✅ **Android Studio** (dernière version stable recommandée)
- ✅ **JDK 17** (inclus avec Android Studio)
- ✅ **Android SDK** avec API Level 34
- ✅ **Émulateur Android** ou appareil physique (Android 7.0+)

---

### 🔧 Configuration du Backend

#### 1. Cloner le projet
```bash
git clone https://github.com/VOTRE_USERNAME/Gestion-Personnel.git
cd Gestion-Personnel/BackEnd
```

#### 2. Configurer la base de données MySQL

Importez le fichier SQL fourni dans le dossier `BackEnd`:

```bash
mysql -u root -p < ensate_personnel_db.sql
```

Ou via MySQL Workbench / phpMyAdmin, importez le fichier `ensate_personnel_db.sql`.

#### 3. Configurer `application.properties`

Le fichier se trouve dans `BackEnd/src/main/resources/application.properties`:

```properties
# Configuration de la base de données
spring.datasource.url=jdbc:mysql://localhost:3306/ensate_personnel_db?useSSL=false&serverTimezone=Africa/Casablanca&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=VOTRE_MOT_DE_PASSE
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuration du serveur
server.port=8080

# Identifiants de test
spring.security.user.name=admin
spring.security.user.password=admin
```

**⚠️ Important:** Modifiez `spring.datasource.password` avec votre mot de passe MySQL.

#### 4. Lancer le backend

**Avec Maven Wrapper (recommandé):**
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

**Avec Maven installé:**
```bash
mvn spring-boot:run
```

Le serveur démarrera sur `http://localhost:8080`

---

### 📱 Configuration du Frontend Android

#### 1. Ouvrir le projet dans Android Studio
```
File → Open → Sélectionner: Gestion-Personnel/FrontEnd
```

#### 2. Synchroniser Gradle
Android Studio synchronisera automatiquement les dépendances.

#### 3. Configurer l'URL du backend

Le fichier `FrontEnd/app/src/main/java/com/ensa/gestionpersonnel/utils/Constants.kt` contient:

```kotlin
object Constants {
    const val BASE_URL = "http://10.0.2.2:8080/api/"
}
```

**Notes importantes:**
- `10.0.2.2` est l'adresse localhost pour l'émulateur Android
- Si vous utilisez un **appareil physique**, remplacez par l'adresse IP de votre ordinateur:
  ```kotlin
  const val BASE_URL = "http://192.168.1.XXX:8080/api/"
  ```

#### 4. Lancer l'application

**Via Android Studio:**
- Cliquer sur le bouton "Run" (▶️) ou `Shift + F10`

#### 5. Identifiants de test

- **Username**: `admin.rh`
- **Password**: `password123`

---

## 🌐 Endpoints API

### Base URL
```
http://localhost:8080/api
```

### 🔐 Authentification

| Méthode | Endpoint | Description | Body |
|---------|----------|-------------|------|
| `POST` | `/auth/login` | Connexion utilisateur | `{ "username": "string", "password": "string" }` |

---

### 👥 Personnel

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/personnels` | Liste de tout le personnel |
| `GET` | `/personnels/{id}` | Détails d'un personnel |
| `GET` | `/personnels/search?query={query}` | Recherche de personnel |
| `POST` | `/personnels` | Créer un nouveau personnel |
| `PUT` | `/personnels/{id}` | Modifier un personnel |
| `DELETE` | `/personnels/{id}` | Supprimer un personnel |

**Exemple PersonnelDTO:**
```json
{
  "id": 1,
  "ppr": "12345678",
  "nom": "Alami",
  "prenom": "Mohammed",
  "dateNaissance": "1985-05-15",
  "cin": "AB123456",
  "adresse": "Tétouan, Maroc",
  "telephone": "0612345678",
  "email": "m.alami@ensa.ac.ma",
  "grade": "Professeur",
  "dateRecrutement": "2010-09-01"
}
```

---

### 🏖️ Absences

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/absences` | Liste de toutes les absences |
| `GET` | `/absences/{id}` | Détails d'une absence |
| `GET` | `/absences/personnel/{id}` | Absences d'un personnel |
| `POST` | `/absences` | Créer une absence |
| `PUT` | `/absences/{id}` | Modifier une absence |
| `PUT` | `/absences/{id}/validate` | Valider une absence |
| `DELETE` | `/absences/{id}` | Supprimer une absence |

**Types d'absence:**
- `CONGE` - Congé annuel
- `MALADIE` - Absence maladie
- `EXCEPTIONNELLE` - Absence exceptionnelle
- `NON_JUSTIFIEE` - Absence non justifiée

---

### 🌍 Missions

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/missions` | Liste de toutes les missions |
| `GET` | `/missions/{id}` | Détails d'une mission |
| `GET` | `/missions/personnel/{id}` | Missions d'un personnel |
| `POST` | `/missions` | Créer une mission |
| `PUT` | `/missions/{id}` | Modifier une mission |
| `PUT` | `/missions/{id}/close` | Clôturer une mission |
| `DELETE` | `/missions/{id}` | Supprimer une mission |

---

### 🎓 Diplômes

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/diplomes` | Liste de tous les diplômes |
| `GET` | `/diplomes/{id}` | Détails d'un diplôme |
| `GET` | `/diplomes/personnel/{id}` | Diplômes d'un personnel |
| `POST` | `/diplomes` | Créer un diplôme |
| `PUT` | `/diplomes/{id}` | Modifier un diplôme |
| `DELETE` | `/diplomes/{id}` | Supprimer un diplôme |

---

### 📈 Avancements

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/avancements` | Liste de tous les avancements |
| `GET` | `/avancements/{id}` | Détails d'un avancement |
| `GET` | `/avancements/personnel/{id}` | Avancements d'un personnel |
| `POST` | `/avancements` | Créer un avancement |
| `PUT` | `/avancements/{id}` | Modifier un avancement |
| `DELETE` | `/avancements/{id}` | Supprimer un avancement |

---

### 📊 Dashboard

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/dashboard/stats` | Statistiques du tableau de bord |

**Réponse:**
```json
{
  "totalPersonnel": 150,
  "totalAbsences": 45,
  "totalMissions": 12,
  "absencesEnAttente": 5,
  "missionsEnCours": 3,
  "monthlyData": [
    {
      "month": "Janvier",
      "absences": 10,
      "missions": 2
    }
  ]
}
```

---

### 👤 Profil

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/profile/{id}` | Consulter le profil |
| `PUT` | `/profile/{id}` | Modifier le profil |

---

## 📱 Captures d'écran

L'application comprend les écrans suivants:

1. **Écran de démarrage (Splash)** - Logo et bienvenue
2. **Connexion** - Authentification sécurisée
3. **Tableau de bord** - Vue d'ensemble avec statistiques et graphiques
4. **Liste du personnel** - Recherche et filtrage
5. **Détails du personnel** - Informations complètes
6. **Gestion des absences** - CRUD complet avec validation
7. **Gestion des missions** - Suivi des missions
8. **Gestion des diplômes** - Historique académique
9. **Gestion des avancements** - Suivi de carrière
10. **Profil utilisateur** - Consultation et modification

---

