package BudgetApp;

public class Operation {
    private TypeOperation types;
    private double montants;
    private Categorie categories;
    private String libelles;

    public Operation() {};
    public Operation(TypeOperation types, double montants, Categorie categories, String libelles) {
        if (montants <= 0) {
            System.out.println("Montant invalide");
        }
        if (libelles.length() < 3 || libelles.length() > 40) {
            System.out.println("Libelle invalide");
        }
        this.montants = montants;
        this.types = types;
        this.categories = categories;
        this.libelles = libelles;
    }

    public TypeOperation getType() {
        return types;
    }

    public void setType(TypeOperation types) {
        this.types = types;
    }

    public double getMontant() {
        return montants;
    }

    public void setMontant(double montants) {
        this.montants = montants;
    }

    public Categorie getCategorie() {
        return categories;
    }

    public void setCategorie(Categorie categories) {
        this.categories = categories;
    }

    public String getLibelle() {
        return libelles;
    }

    public void setLibelle(String libelles) {
        this.libelles = libelles;
    }

    public void afficherLigne(int i) {
        System.out.println("["+(i+1)+"] " + "| " + " | "+types + " | "+ categories + " | " + libelles + " |  "+montants+"€");
    }

    public boolean contient(String motCle) {
        return libelles.toLowerCase().contains(motCle.toLowerCase());
    }
}
