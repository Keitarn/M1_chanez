import java.util.ArrayList;
import java.util.List;

public final class Declaration {

    private List<Identifiant> listIdentifiants;

    public Declaration() {
        listIdentifiants = new ArrayList<Identifiant>();
    }

    public void addIdentifiant(Identifiant identifiant) {
        listIdentifiants.add(identifiant);
    }

    public String toStringPseudoLanguage() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Identifiant identifiant : listIdentifiants) {
            stringBuilder.append("\t").append(identifiant.toStringPseudoLanguage()).append("\n");
        }
        return stringBuilder.toString();
    }

    public String toStringCodeSourceJava() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (Identifiant identifiant : listIdentifiants) {
            stringBuilder.append("\t").append("static ").append(identifiant.toStringCodeSourceJava()).append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
