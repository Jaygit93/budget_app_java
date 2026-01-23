# APPLICATION DE GESTIONNAIRE DE BUDGET PERSONNEL

Application console Java pour la gestion de budget personnel avec suivi des recettes et dépenses.
L'application utilise le format US pour les décimales (le point comme séparateur).

## Description

Ce gestionnaire de budget permet de suivre ses finances personnelles en enregistrant toutes les opérations financières (recettes et dépenses) avec un système de catégorisation et de recherche.

L'application utilise maintenant la programmation orientée objet (POO) avec l'encapsulation des données dans la classe `Operation.

## Fonctionnalités principales

- Ajout d'opérations (recettes/dépenses) avec montant, catégorie et libellé
- Affichage d'un bilan financier complet (totaux, solde, moyenne, min/max)
- Consultation de l'historique complet des opérations
- Filtrage par type d'opération ou par catégorie
- Recherche textuelle dans les libellés

## Structure du projet (arborescence)
```
BudgetApp/
├── BudgetApp.java      # Classe principale avec la logique métier
├── Operation.java      # Classe métier représentant une opération (POO)
├── Saisie.java         # Utilitaires de saisie
├── TypeOperation.java  # Enum (RECETTE/DEPENSE)
└── Categorie.java      # Enum (catégories d'opérations)
```

## Prérequis

- Java 8 ou supérieur
- Aucune bibliothèque externe n'est requise