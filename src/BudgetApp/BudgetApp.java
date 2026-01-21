package BudgetApp;

import java.util.Locale;
import java.util.Scanner;

public class BudgetApp {

    static final int MAX = 100;
    static TypeOperation[] types = new TypeOperation[MAX];
    static double[] montants = new double[MAX];
    static Categorie[] categories = new Categorie[MAX];
    static String[] libelles = new String[MAX];
    static int nbOps = 0;
    static Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        int choix;
        do {
            afficherMenu();
            choix = Saisie.lireEntier(sc);

            switch (choix) {
                case 1:
                    ajouterOperation();
                    break;
                case 2:
                    afficherBilan();
                    break;
                case 3:
                    afficherHistorique();
                    break;
                case 4:
                    filtrerHistorique();
                    break;
                case 5:
                    rechercher();
                    break;
                case 0:
                    System.out.println("Au revoir");
                    break;
                default: System.out.println("Choix invalide.");
            }
        } while (choix != 0);
        sc.close();
    }

    static void afficherMenu() {
        System.out.println("\n== GESTIONNAIRE BUDGET ==");
        System.out.println("1. Ajouter une operation");
        System.out.println("2. Afficher le bilan");
        System.out.println("3. Afficher l'historique");
        System.out.println("4. Filtrer l'historique");
        System.out.println("5. Rechercher");
        System.out.println("---------------------------");
        System.out.println("0. Quitter");
        System.out.println("---------------------------");
        System.out.print("Votre choix : ");
    }

    static void ajouterOperation() {
        if (nbOps >= MAX) {
            System.out.println("Tableau plein !");
            return;
        }

        System.out.println("\n-> Nouvelle operation");

        System.out.print("Type (1= Recette / 2= Depense) : ");
        int t = Saisie.lireEntier(sc);
        while (t != 1 && t != 2) {
            System.out.print("1 ou 2 : ");
            t = Saisie.lireEntier(sc);
        }
        types[nbOps] = (t == 1) ? TypeOperation.RECETTE : TypeOperation.DEPENSE;

        montants[nbOps] = Saisie.lireMontant(sc);

        System.out.println("Categorie :");
        Categorie[] cats = Categorie.values();
        for (int i = 0; i < cats.length; i++) {
            System.out.println((i + 1) + ". " + cats[i]);
        }
        int c;
        do {
            c = Saisie.lireEntier(sc);
        } while (c < 1 || c > cats.length);
        categories[nbOps] = cats[c - 1];

        sc.nextLine();
        System.out.print("Libelle (3-40 caracteres) : ");
        libelles[nbOps] = Saisie.lireLibelle(sc, 3, 40);

        nbOps++;
        System.out.println("Operation enregistrée !");
    }

    static void afficherBilan() {
        if (nbOps == 0) {
            System.out.println("Aucune operation.");
            return;
        }

        double recettes = 0;
        double depenses = 0;
        double minimum = montants[0];
        double maximum = montants[0];
        for (int i = 0; i < nbOps; i++) {
            if (types[i] == TypeOperation.RECETTE) {
                recettes += montants[i];
            }
            else {
                depenses += montants[i];
            }
            if (montants[i] < minimum) {
                minimum = montants[i];
            }
            if (montants[i] > maximum) {
                maximum = montants[i];
            }
        }

        double solde = recettes - depenses;
        double moyenne = (recettes + depenses) / nbOps;

        System.out.println("\n===== BILAN =====");
        System.out.println("Total Recettes : " + recettes + "€");
        System.out.println("Total Dépenses : " + depenses + "€");
        System.out.println("Solde Actuel   : " + solde + "€");
        System.out.println("Moyenne        : " + moyenne + "€");
        System.out.println("Min/Max        : " + minimum + " / " + maximum + "€");
        System.out.println(solde < 0 ? "[ALERTE] Solde negatif !" : "Bravo, compte sain.");
    }

    static void afficherHistorique() {
        if (nbOps == 0) {
            System.out.println("Aucune operation.");
            return;
        }
        System.out.println("\n===== HISTORIQUE =====");
        for (int i = 0; i < nbOps; i++) {
            afficherLigne(i);
        }
    }

    static void filtrerHistorique() {
        System.out.println("Filtrer par : 1.Type  2.Categorie");
        int choix = Saisie.lireEntier(sc);

        if (choix == 1) {
            System.out.print("1.Recette  2.Depense : ");
            int t = Saisie.lireEntier(sc);
            TypeOperation tf = (t == 1) ? TypeOperation.RECETTE : TypeOperation.DEPENSE;
            System.out.println("\n===== FILTRE TYPE =====");
            int nb = 0;
            for (int i = 0; i < nbOps; i++) {
                if (types[i] == tf) {
                    afficherLigne(i);
                    nb++;
                }
            }
            System.out.println("Résultat : " + nb);
        } else if (choix == 2) {
            Categorie[] cats = Categorie.values();
            for (int i = 0; i < cats.length; i++) {
                System.out.println((i + 1) + ". " + cats[i]);
            }
            int c = Saisie.lireEntier(sc);
            System.out.println("\n===== FILTRE CATEGORIE =====");
            int nb = 0;
            for (int i = 0; i < nbOps; i++) {
                if (categories[i] == cats[c - 1]) {
                    afficherLigne(i);
                    nb++;
                }
            }
            System.out.println("Résultat : " + nb);
        }
    }

    static void rechercher() {
        sc.nextLine();
        System.out.print("Mot-cle (minimum 3 caractères.) : ");
        String mot = Saisie.lireLibelle(sc, 3, 100);
        System.out.println("\n===== RECHERCHE '" + mot + "' ================");
        int nb = 0;
        for (int i = 0; i < nbOps; i++) {
            if (libelles[i].toLowerCase().contains(mot.toLowerCase())) {
                afficherLigne(i);
                nb++;
            }
        }
        System.out.println("Résultat : " + nb);
    }

    static void afficherLigne(int i) {
        System.out.println("["+(i+1)+"] " + "| " + " | "+types[i] + " | "+ categories[i] + " | " + libelles[i] + " |  "+montants[i]+"€");
    }
}