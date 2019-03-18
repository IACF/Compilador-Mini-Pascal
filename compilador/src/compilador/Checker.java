/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author victor
 */
public class Checker implements Visitor{
    
    tabelaDeIdentificacao table;
    int enderecoVariaveis;
     Map<String, Integer> tamanhoTipos;
    
    public void check (Programa P) {
        this.enderecoVariaveis = 0;
        this.table = new tabelaDeIdentificacao();
         this.tamanhoTipos = new HashMap<>()
        {{
            put("boolean",(int) 1);
            put("integer",(int) 4);
            put("real", (int) 8);
        }};
        P.visit(this);

    }

    @Override
    public void visitorPrograma(Programa arg0) {
            arg0.I.visit(this);
            arg0.C.visit(this);
    }
    
    @Override
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel arg0) {
        if(arg0 != null){
            tipoSimples  tp;
            int tamanho =0;
            int enderecoVirtual = 0;
            arg0.I.visit(this);
            arg0.T.visit(this); 
            
            if(arg0.T instanceof tipoSimples){
                tp = (tipoSimples) arg0.T; 
                tamanho = this.tamanhoTipos.get(tp.TK.spelling);
            }else{
                tipoAgregado t;
                Tipo aux = arg0.T;

                while(aux instanceof tipoAgregado){
                    t = (tipoAgregado) aux;
                    tamanho += (Integer.parseInt(t.L2.TK.spelling) - Integer.parseInt(t.L1.TK.spelling) + 1);
                    aux = t.T;
                }
                
                tp = (tipoSimples) aux;
                tamanho *= this.tamanhoTipos.get(tp.TK.spelling);
            }
          
            if(arg0.I instanceof  identificadorSimples){
                table.insert((identificadorSimples) arg0.I, arg0.T, this.enderecoVariaveis);
                this.enderecoVariaveis += tamanho;
            
            }else{
                identificadorSequencial gambirra;
                Identificador aux = arg0.I;
                Stack pilha = new Stack();
                
                do{
                    gambirra = (identificadorSequencial) aux;
                    pilha.push((identificadorSimples) gambirra.I1);
                    aux =  gambirra.I2;
                }while(aux instanceof  identificadorSequencial);
                
                pilha.push((identificadorSimples) aux);
                
                while(!pilha.isEmpty()){
                    table.insert((identificadorSimples) pilha.pop(), arg0.T, this.enderecoVariaveis);
                    this.enderecoVariaveis += tamanho;
                }
           
            }
        }
    }
    
    @Override
    public void visitorCorpo(Corpo arg0) {
        if(arg0 != null){
            arg0.D.visit(this);
            arg0.C.visit(this);
        }
    }

    @Override
    public void visitorDeclaracaoSequencial(declaracaoSequencial arg0) {
        if(arg0 != null){
            arg0.D1.visit(this);
            arg0.D2.visit(this);
        }
    }

    @Override
    public void visitorExpressaoBinaria(expressaoBinaria arg0) {
        if(arg0 != null){
            
        arg0.E1.visit(this);
        arg0.O.visit(this);

        arg0.E2.visit(this);
        arg0.tipo = tipagemExpressao(arg0.E1.tipo, arg0.O, arg0.E2.tipo);
            
        }
    }

    @Override
    public void visitorExpressaoSequencial(expressaoSequencial arg0) {
        if(arg0 != null){
            
            arg0.E1.visit(this);
            arg0.E2.visit(this);
            
            
        }
    }

    @Override
    public void visitorIdentificadorSequencial(identificadorSequencial arg0) {
        
        arg0.I2.visit(this);
        arg0.I1.visit(this);
        
    }

    @Override
    public void visitorIdentificadorSimples(identificadorSimples arg0) {
        if (arg0 != null) {
       
            arg0.TK.visit(this);

        }
    }

    @Override
    public void visitorTipoAgregado(tipoAgregado arg0) {
   
        if((arg0.L1.tipo.equals("integer") && arg0.L2.tipo.equals("integer"))) {
            arg0.L1.visit(this);
            arg0.L2.visit(this);
            arg0.T.visit(this);
        } else {
            throw new Error("Os intervalos no array devem ser do tipo integer");
        }
 
    }

    @Override
    public void visitorTipoSimples(tipoSimples arg0) {
        if (arg0 != null) {
            arg0.TK.visit(this);      
        }
    }

  
    @Override
    public void visitorComandoIterativo(comandoIterativo arg0) {
        if (arg0 != null) {    
            arg0.E.visit(this);
            expressaoBinaria exp;
            expressaoSequencial expSeq;
            if (arg0.E.tipo.equals("boolean")) {
                arg0.C.visit(this);
            } else {
                if(arg0.E instanceof expressaoBinaria){
                    exp = (expressaoBinaria) arg0.E;
                    throw new Error ("A estrutura iterativa necessita de uma operação lógica, linha = " + exp.O.TK.line);
                }
                expSeq = (expressaoSequencial) arg0.E;
                exp = (expressaoBinaria) expSeq.E2;
                throw new Error ("A estrutura iterativa necessita de uma operação lógica, linha =" + exp.O.TK.line);
            }
        }
    }

    @Override
    public void visitorComandoCondicional(comandoCondicional arg0) {
        if (arg0 != null) {
            arg0.E.visit(this);
            if("boolean".equals(arg0.E.tipo)) {
                arg0.C1.visit(this);
                if(arg0.C2 != null)
                    arg0.C2.visit(this);
            } else {
                expressaoBinaria exp;
                expressaoSequencial expSeq;
                
               if(arg0.E instanceof expressaoBinaria){
                    exp = (expressaoBinaria) arg0.E;
                    throw new Error ("A estrutura condicional necessita de uma operação lógica, linha = " + exp.O.TK.line);
                }
                expSeq = (expressaoSequencial) arg0.E;
                exp = (expressaoBinaria) expSeq.E2;
                throw new Error ("A estrutura condicional necessita de uma operação lógica, linha =" + exp.O.TK.line);
            }
            
            System.out.println(arg0.E.tipo);
               
        }
    }

    @Override
    public void visitorComandoComposto(comandoComposto arg0) {
        if (arg0 != null) {
           
            arg0.C1.visit(this);
            if (arg0.C2 != null) 
                arg0.C2.visit(this);
            
        }
    }

    @Override
    public void visitorComandoAtribuicao(comandoAtribuicao arg0) {
        if (arg0 != null) {
            
            arg0.V.visit(this);
            arg0.E.visit(this);
            
            System.out.println("test = " + arg0.V.tipo);
            Tipo aux = arg0.V.ponteiro.tipo;
            
            if(aux instanceof tipoAgregado){
            
                tipoAgregado t;
                tipoSimples tipo;

                while(aux instanceof tipoAgregado){
                    t = (tipoAgregado) aux;
                    aux = t.T;
                }
                
                tipo = (tipoSimples) aux;
                if (!arg0.E.tipo.equals(tipo.TK.spelling))
                    throw new Error(" possui um tipo incompatível ",(identificadorSimples) arg0.V.I);
            }else{
                System.out.println(arg0.E.tipo);
                if (!arg0.E.tipo.equals(arg0.V.tipo))
                    throw new Error(" possui um tipo incompatível ",(identificadorSimples) arg0.V.I);
            }
            
        }
    }

    @Override
    public void visitorVariavel(Variavel arg0) {
        if (arg0 != null) {
            tipoSimples  tSimples;
            
            arg0.I.visit(this);
            
            identificadorSimples I = (identificadorSimples) arg0.I;
            
            elementoTabelaDeIdentificacao element = this.table.retrieve(I.TK.spelling);
            
            if(element == null)
                throw new Error(I);
            
            arg0.setPonteiroDeclaracao(element);
            
            if(element.tipo instanceof tipoSimples){
                tSimples = (tipoSimples) element.tipo;
                arg0.tipo = tSimples.TK.spelling;
            }else{
                Expressao e = arg0.E;
                int count2 = 0;
                expressaoSequencial exp;
                int[] limite = new int[30];
                boolean[] notliteral = new boolean[30];
                Literal l;
                
                while(e instanceof expressaoSequencial){
                    exp = (expressaoSequencial) e;
                    if(exp.E2 instanceof Literal){
                        if(exp.E2.tipo.equals("integer")){
                            l = (Literal) exp.E2;
                            limite[count2] = Integer.parseInt(l.TK.spelling);
                        }else{
                            throw new Error(" => Tipo do indice invalido no array ",(identificadorSimples) arg0.I);
                        }
                    }else{
                        notliteral[count2] = true;
                    }
                    count2++;
                    e = exp.E1;   
                }
                
                if(e instanceof Literal){
                    if(((Literal) e).tipo.equals("integer")){
                        l = (Literal) e;
                        limite[count2] = Integer.parseInt(l.TK.spelling);
                    }else{
                        throw new Error(" => Tipo do indice invalido no array ",(identificadorSimples) arg0.I);
                    }
                }else{
                    notliteral[count2] = true;
                }
                
                count2++;
                
                System.out.println(count2);
                
                for (int i= (count2-1); i >= 0; i--) {
                    System.out.println("lim = " + limite[i]);
                }
               
                /////////////////////////////////////////////////////////////////
                
                tipoAgregado t;
                Tipo aux = element.tipo;
                int count = 0;
                
                while(aux instanceof tipoAgregado ){
                    t = (tipoAgregado) aux;                    
                    aux = t.T;
                    count++;
                }
     
                aux = element.tipo;
                
                if(count != count2)
                    throw new Error(" => Dimensão inválida ",(identificadorSimples) arg0.I);
                
                count--;
                 
                while(aux instanceof tipoAgregado ){
                    t = (tipoAgregado) aux;  
                    if(!notliteral[count]){
                        System.out.println(limite[count]); 
                       if(!(limite[count] >= Integer.parseInt(t.L1.TK.spelling)
                           && limite[count] <= Integer.parseInt(t.L2.TK.spelling))){
                            throw new Error(" => Indice invalido no array ",(identificadorSimples) arg0.I);
                        }
                    }
                    aux = t.T;
                    count--;
                }
                
                tSimples = (tipoSimples) aux;
                         
                arg0.tipo = tSimples.TK.spelling;
                 
            }
            
            arg0.endereco = element.enderecoVariavel;
            
            if (arg0.E != null)
                arg0.E.visit(this);

        }
    }

    @Override
    public void visitorLiteral(Literal arg0) {
        if (arg0 != null) {
            arg0.TK.visit(this);
        }
    }

    @Override
    public void visitorToken(Token t) {
          
        System.out.println(" { " + t.spelling + " } ");
        
    }

    @Override
    public void visitorOperador(Operador arg0) {
        
       
        arg0.TK.visit(this);
        
        

    }
    
    public String tipagemExpressao(String tipoE1,Operador Operador, String tipoE2) {
        
        
        if (Operador.TK.kind >= 19 && Operador.TK.kind <= 23 && Operador.TK.kind != 21) {
            if(tipoE1.equals("integer") && tipoE2.equals("integer"))
                return "integer";
            if(tipoE1.equals("real") && tipoE2.equals("real"))
                return "real";
            if(tipoE1.equals("boolean") || tipoE2.equals("boolean"))
                throw new Error ("Operandos inválidos para a operação binária ", Operador);
            if(
                (tipoE1.equals("real") && tipoE2.equals("integer")) || 
                (tipoE1.equals("integer") && tipoE2.equals("real"))
            )
                return "real";
        }
        
        System.out.println(tipoE1);
        
        if( Operador.TK.kind >= 25 && Operador.TK.kind <= 29) {
            if(
                !(tipoE1.equals("real") && tipoE2.equals("real")) &&
                !(tipoE1.equals("integer") && tipoE2.equals("integer"))
            )
                throw new Error ("Operandos inválidos para a operação binária ", Operador);
            
            return "boolean";
        }
               
        if( Operador.TK.kind == 21 || Operador.TK.kind == 24) {
            if(
                (tipoE1.equals("boolean") && tipoE2.equals("boolean"))
            )
                return "boolean";
            
            throw new Error ("Operandos inválidos para a operação lógica ", Operador);
        }
        
        return "ok";
    }
}
