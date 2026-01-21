package BudgetApp;

import java.util.Scanner;

public class Saisie {

    public static int lireEntier(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Entrez un nombre : ");
        }
        return sc.nextInt();
    }

    public static double lireMontant(Scanner sc) {
        double montant;
        do {
            System.out.print("Montant : ");
            while (!sc.hasNextDouble()) {
                sc.next();
                System.out.print("Montant invalide. Veuillez réessayez : ");
            }
            montant = sc.nextDouble();
            if (montant <= 0) {
                System.out.println("Le montant doit être positif");
            }
        } while (montant <= 0);
        return montant;
    }

    public static String lireLibelle(Scanner sc, int min, int max) {
        String texte;
        do {
            texte = sc.nextLine();
            if (texte.length() < min || texte.length() > max) {
                System.out.print("Libelle invalide (saisir entre " + min + " et " + max + " caracteres) : ");
            }
        } while (texte.length() < min || texte.length() > max);
        return texte;
    }
}