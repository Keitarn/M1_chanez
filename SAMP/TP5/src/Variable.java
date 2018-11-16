public class Variable extends Identifiant {

    public Variable(String identifiant) {
        super(identifiant);
    }

    public String toStringPseudoLanguage() {
        return super.toStringPseudoLanguage() + "var " + identifiant;
    }

    public String toStringCodeSourceJava() {
        return super.toStringCodeSourceJava() + "int " + identifiant + " ;";
    }
}
