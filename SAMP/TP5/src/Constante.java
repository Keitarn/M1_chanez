public class Constante extends Identifiant {

    private int valeur;

    public Constante(String identifiant, int valeur) {
        super(identifiant);
        this.valeur = valeur;
    }

    public String toStringPseudoLanguage() {
        return super.toStringPseudoLanguage() + "const " + identifiant + " = " + valeur;
    }

    public String toStringCodeSourceJava() {
        return super.toStringCodeSourceJava() + "final int " + identifiant + " = " + valeur + " ;";
    }
}
