package BudgetApp;

import java.util.Locale;
import java.util.Scanner;

public class BudgetApp {

    private static final int MAX = 100;
    private static Operation[] operations = new Operation[MAX];
    private static int nbOps = 0;
    private static Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        int choix;
        do {
            afficherMenu();
            choix = Saisie.lireEntier(sc);

            switch (choix) {
                case 0:
                    System.out.println("Au revoir !");
                    break;
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
                default: System.out.println("Choix invalide.");
            }
        } while (choix != 0);
        sc.close();
    }

    private static void afficherMenu() {
        System.out.println("\n======== GESTIONNAIRE BUDGET ============");
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


    private static void ajouterOperation() {
        if (nbOps >= MAX) {
            System.out.println("Tableau plein !");
        }

        System.out.println("\n-> Nouvelle operation");

        System.out.print("Type (1= Recette / 2= Depense) : ");
        int t = Saisie.lireEntier(sc);
        while (t != 1 && t != 2) {
            System.out.print("1 ou 2 : ");
            t = Saisie.lireEntier(sc);
        }
        TypeOperation types = (t == 1) ? TypeOperation.RECETTE : TypeOperation.DEPENSE;

        double montants = Saisie.lireMontant(sc);

        System.out.println("Categorie :");
        Categorie[] cats = Categorie.values();
        for (int i = 0; i < cats.length; i++) {
            System.out.println((i + 1) + ". " + cats[i]);
        }
        int c;
        do {
            c = Saisie.lireEntier(sc);
        } while (c < 1 || c > cats.length);
        Categorie categories = cats[c - 1];

        sc.nextLine();
        System.out.print("Libelle (3-40 caracteres) : ");
        String libelles = Saisie.lireLibelle(sc, 3, 40);

        operations[nbOps] = new Operation(types, montants, categories, libelles);
        nbOps++;
        System.out.println("Operation enregistrée !");
    }


    private static void afficherBilan() {
        if (nbOps == 0) {
            System.out.println("Aucune operation.");
        }

        double recettes = 0;
        double depenses = 0;
        double minimum = operations[0].getMontant();
        double maximum = operations[0].getMontant();

        for (int i = 0; i < nbOps; i++) {
            double m = operations[i].getMontant();
            if (operations[i].getType() == TypeOperation.RECETTE) {
                recettes += m;
            } else {
                depenses += m;
            }
            if (m < minimum) minimum = m;
            if (m > maximum) maximum = m;
        }

        double solde = recettes - depenses;
        double moyenne = (recettes + depenses) / nbOps;

        System.out.println("\n===== BILAN ======");
        System.out.println("Total Recettes : " + recettes + " euros");
        System.out.println("Total Depenses : " + depenses + " euros");
        System.out.println("Solde Actuel   : " + solde + " euros");
        System.out.println("Moyenne        : " + moyenne + " euros");
        System.out.println("Min/Max        : " + minimum + " / " + maximum + " euros");
        System.out.println(solde < 0 ? "[ALERTE] Solde negatif !" : "Bravo, compte sain.");
    }


    private static void afficherHistorique() {
        if (nbOps == 0) {
            System.out.println("Aucune operation.");
        }
        System.out.println("\n===== HISTORIQUE =====");
        for (int i = 0; i < nbOps; i++) {
            operations[i].afficherLigne(i);
        }
    }


    private static void filtrerHistorique() {
        System.out.println("Filtrer par : 1.Type  2.Categorie");
        int choix = Saisie.lireEntier(sc);

        if (choix == 1) {
            System.out.print("1.Recette  2.Depense : ");
            int t = Saisie.lireEntier(sc);
            TypeOperation tf = (t == 1) ? TypeOperation.RECETTE : TypeOperation.DEPENSE;
            System.out.println("\n===== FILTRE TYPE =====");
            int nb = 0;
            for (int i = 0; i < nbOps; i++) {
                if (operations[i].getType() == tf) {
                    operations[i].afficherLigne(i);
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
                if (operations[i].getCategorie() == cats[c - 1]) {
                    operations[i].afficherLigne(i);
                    nb++;
                }
            }
            System.out.println("Résultat : " + nb);
        }
    }


    private static void rechercher() {
        sc.nextLine();
        System.out.print("Mot-cle (minimum 3 caractères.) : ");
        String mot = Saisie.lireLibelle(sc, 3, 100);
        System.out.println("\n===== RECHERCHE : '" + mot + "' =================");
        int nb = 0;
        for (int i = 0; i < nbOps; i++) {
            if (operations[i].contient(mot)) {
                operations[i].afficherLigne(i);
                nb++;
            }
        }
        System.out.println("Résultat : " + nb);
    }
}