# 5th-project-testing

## Configuration de la Base de Données

1. Installez et configurez une instance de base de données (par exemple, MySQL) sur votre machine.
2. Exécutez le script de création de la base de données se trouvant dans le dossier `ressources/sql` pour établir la structure de la base de données.
3. Mettez à jour les informations de connexion dans le fichier de configuration `application.properties`.

## Installation de l'Application

1. Assurez-vous que les dépendances nécessaires sont installées : Java, Node.js, Maven.
2. Clonez ce dépôt sur votre machine locale.

    ```sh
    git clone https://github.com/arthurgcliking/5th-project-testing.git
    ```

3. Dans le répertoire du projet back-end, exécutez la commande suivante pour télécharger les dépendances et compiler le projet :

    ```sh
    cd backend
    mvn clean install
    ```

4. Dans le répertoire du projet front-end, exécutez la commande suivante pour installer les dépendances du front-end :

    ```sh
    cd frontend
    npm install
    ```

## Démarrage de l'Application

1. Dans le répertoire du projet back-end, lancez la commande suivante pour démarrer le back-end :

    ```sh
    cd backend
    mvn spring-boot:run
    ```

2. Dans le répertoire du projet front-end, lancez la commande suivante pour démarrer le front-end :

    ```sh
    cd frontend
    npm run start
    ```

## Exécution des Tests

### Tests Unitaires et d'Intégration Front-End (Jest)

1. Accédez au répertoire du projet front-end :

    ```sh
    cd frontend
    ```

2. Exécutez la commande suivante pour lancer les tests unitaires front-end avec Jest :

    ```sh
    npm run test
    ```

3. Pour générer un rapport de couverture, utilisez la commande suivante :

    ```sh
    npm run test -- --coverage
    ```

    Le rapport se trouvera dans `front/coverage/jest/lcov-report/index.html`.

### Tests End-to-End (Cypress)

1. Accédez au répertoire du projet front-end :

    ```sh
    cd frontend
    ```

2. Exécutez la commande suivante pour lancer les tests end-to-end avec Cypress :

    ```sh
    npm run e2e
    ```

3. Pour générer un rapport de couverture, utilisez la commande suivante :

    ```sh
    npm run e2e:coverage
    ```

    Le rapport se trouvera dans `front/coverage/lcov-report/index.html`.

### Tests Unitaires et d'Intégration Back-End (JUnit et Mockito)

1. Accédez au répertoire du projet back-end :

    ```sh
    cd backend
    ```

2. Exécutez la commande suivante pour lancer les tests unitaires et d'intégration back-end avec JUnit et Mockito :

    ```sh
    mvn clean test
    ```

    Le rapport de couverture sera généré dans `back/target/site/jacoco/index.html`.
