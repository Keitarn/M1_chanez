public class App {

    public static void main(String[] args) {
        Algorithme cPuissance = new Algorithme("Puissance");
        Constante fNombre = new Constante("nombre", 2);
        Constante fPuissance = new Constante("puissance", 4);
        Variable vResult = new Variable("resultat");
        Variable vI = new Variable("i");
        cPuissance.addIdentifantToDeclaration(fNombre);
        cPuissance.addIdentifantToDeclaration(fPuissance);
        cPuissance.addIdentifantToDeclaration(vResult);
        cPuissance.addIdentifantToDeclaration(vI);
        cPuissance.addInstruction(new Affectation(vI, new Expression()));
        cPuissance.addInstruction(new Affectation(vResult, new Expression()));
        cPuissance.addInstruction(new TantQue(new Expression(), new Instruction[]{new Instruction(), new Instruction()}));
        cPuissance.addInstruction(new Ecrire(new Expression()));
        cPuissance.addInstruction(new Ecrire(new Expression()));
        cPuissance.addInstruction(new Ecrire(new Expression()));
        cPuissance.addInstruction(new Ecrire(new Expression()));
        cPuissance.addInstruction(new Ecrire(new Expression()));
        System.out.println(cPuissance.toStringPseudoLanguage());
        System.out.println(cPuissance.toStringCodeSourceJava());


        Algorithme cTriangle = new Algorithme("Triangle");
        cTriangle.addIdentifantToDeclaration(new Variable("ligne"));
        cTriangle.addIdentifantToDeclaration(new Variable("colonne"));
        cTriangle.addIdentifantToDeclaration(new Constante("maxLignes", 10));
        //TODO
        System.out.println(cTriangle.toStringPseudoLanguage());
        System.out.println(cTriangle.toStringCodeSourceJava());
    }
}
