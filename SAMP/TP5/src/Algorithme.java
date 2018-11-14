import java.util.ArrayList;
import java.util.List;

public class Algorithme {

    private String nom;
    private Declaration declaration;
    private List<Instruction> listInstructions;

    public Algorithme(String nom) {
        this.nom = nom;
        declaration = new Declaration();
        listInstructions = new ArrayList<Instruction>();
    }

    public void addIdentifantToDeclaration(Identifiant identifiant) {
        declaration.addIdentifiant(identifiant);
    }

    public void addInstruction(Instruction instruction) {
        listInstructions.add(instruction);
    }

    public String toStringPseudoLanguage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Algorithme ").append(nom).append("\n");
        stringBuilder.append(declaration.toStringPseudoLanguage());
        stringBuilder.append("debut\n");
        for (Instruction instruction : listInstructions) {
            stringBuilder.append(instruction.toStringPseudoLanguage());
        }
        stringBuilder.append("fin\n");
        return stringBuilder.toString();
    }

    public String toStringCodeSourceJava() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("public class ").append(nom).append(" {\n");
        stringBuilder.append(declaration.toStringCodeSourceJava());
        stringBuilder.append("\tpublic static void main(String[] args) {\n");
        for (Instruction instruction : listInstructions) {
            stringBuilder.append("\t\t").append(instruction.toStringCodeSourceJava()).append("\n");
        }
        stringBuilder.append("\t}\n");
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}
