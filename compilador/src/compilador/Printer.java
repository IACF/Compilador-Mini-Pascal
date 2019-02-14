/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author italo
 */
public class Printer implements Visitor {
    
    private int count;

    public Printer() {
        this.count = 0;
    }

    public void print (Programa P) {
        P.visit(this);
    }
    
    @Override
    public void visitorPrograma(Programa arg0) {
        if (arg0 != null) {
            count++;
            arg0.I.visit(this);
            arg0.C.visit(this);
            count--;
        } else {
            //errors
        }
    }

    @Override
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel arg0) {
        if(arg0 != null){
            count++;
            arg0.I.visit(this);
            arg0.T.visit(this);
            count--;
        }
    }
    
    @Override
    public void visitorCorpo(Corpo arg0) {
        if(arg0 != null){
            count++;
            arg0.D.visit(this);
            arg0.C.visit(this);
            count--;
        }
    }

    @Override
    public void visitorDeclaracaoSequencial(declaracaoSequencial arg0) {
        if(arg0 != null){
            count++;
            arg0.D1.visit(this);
            arg0.D2.visit(this);
            count--;
        }
    }

    @Override
    public void visitorExpressaoBinaria(expressaoBinaria arg0) {
        if(arg0 != null){
            count++;
            arg0.E1.visit(this);
            arg0.O.visit(this);
            arg0.E2.visit(this);
            count--;
        }
    }

    @Override
    public void visitorExpressaoSequencial(expressaoSequencial arg0) {
        if(arg0 != null){
            count++;
            arg0.E1.visit(this);
            arg0.E2.visit(this);
            count--;
        }
    }

    @Override
    public void visitorIdentificadorSequencial(identificadorSequencial arg0) {
        count++;
        arg0.I2.visit(this);
        arg0.I1.visit(this);
        count--;
    }

    @Override
    public void visitorIdentificadorSimples(identificadorSimples arg0) {
        if (arg0 != null) {
            count++;
            arg0.TK.visit(this);
            count--;
        }
    }

    @Override
    public void visitorTipoAgregado(tipoAgregado arg0) {
        count++;
        arg0.L1.visit(this);
        arg0.L2.visit(this);
        arg0.T.visit(this);
        count--;
    }

    @Override
    public void visitorTipoSimples(tipoSimples arg0) {
        if (arg0 != null) {
            count++;
            arg0.TK.visit(this);
            count--;
        }
    }

  
    @Override
    public void visitorComandoIterativo(comandoIterativo arg0) {
        if (arg0 != null) {
            count++;
            arg0.E.visit(this);
            arg0.C.visit(this);
            count--;
        }
    }

    @Override
    public void visitorComandoCondicional(comandoCondicional arg0) {
        if (arg0 != null) {
            count++;
            arg0.E.visit(this);
            arg0.C1.visit(this);
            arg0.C2.visit(this);
            count--;
        }
    }

    @Override
    public void visitorComandoComposto(comandoComposto arg0) {
        if (arg0 != null) {
            count++;
            arg0.C1.visit(this);
            if (arg0.C2 != null) 
                arg0.C2.visit(this);
            count--;
        }
    }

    @Override
    public void visitorComandoAtribuicao(comandoAtribuicao arg0) {
        if (arg0 != null) {
            count++;
            arg0.V.visit(this);
            arg0.E.visit(this);
            count--;
        }
    }

    @Override
    public void visitorVariavel(Variavel arg0) {
        if (arg0 != null) {
            count++;
            arg0.I.visit(this);
            
            if (arg0.E != null)
                arg0.E.visit(this);
            
            count--;
        }
    }

    @Override
    public void visitorLiteral(Literal arg0) {
        if (arg0 != null) {
            count++;
            arg0.TK.visit(this);
            count--;
        }
    }

    @Override
    public void visitorToken(Token t) {
        System.out.println(count + "-> " + t.spelling + "\n");
    }

    @Override
    public void visitorOperador(Operador arg0) {
        count++;
        arg0.TK.visit(this);
        count--;
    }

}
