/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */

public class Coder implements Visitor {
    BufferedWriter buffWrite;
    Map<String, Byte> tamanhoTipos;
    int countIds;
    int countLabels;
    int contador;
    
    public Coder() throws IOException {
        this.buffWrite = new BufferedWriter(new FileWriter("assembly.asm"));
        this.countLabels = 0;
         this.tamanhoTipos = new HashMap<>()
        {{
            put("boolean",(byte) 1);
            put("integer",(byte) 4);
            put("real", (byte) 8);
        }};
         this.countIds = 0;
    }
    
    public void code(Programa programa){
        this.visitorPrograma(programa);
    }
    
    public void escrever(String texto) throws IOException{     
        buffWrite.append(texto + "\n");
    }
    
    public void fecharArquivo() throws IOException{ 
        try {
        buffWrite.close();
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void visitorToken(Token t) {
    }

    @Override
    public void visitorPrograma(Programa p) {
        p.I.visit(this);
        p.C.visit(this);
        
        try {
            escrever("POP " + this.contador);
            escrever("HALT");
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            this.fecharArquivo();
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void visitorDeclaracaoDeVariavel(declaracaoDeVariavel arg0) {
        if(arg0 != null){
           tipoSimples tp;
           this.countIds = 0;
           
           arg0.I.visit(this);
            System.out.println(this.countIds);
            
            if(arg0.T instanceof tipoSimples){
                for (int i = 0; i < this.countIds; i++) {
                    tp = (tipoSimples) arg0.T;
                    try {
                        this.contador += this.tamanhoTipos.get(tp.TK.spelling);
                        escrever("PUSH "+ this.tamanhoTipos.get(tp.TK.spelling));
                    } catch (IOException ex) {
                        Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
            }else{
                int tamanho = 0;
                tipoAgregado t;
                Tipo aux = arg0.T;

                while(aux instanceof tipoAgregado){
                    t = (tipoAgregado) aux;
                    tamanho += (Integer.parseInt(t.L2.TK.spelling) - Integer.parseInt(t.L1.TK.spelling) + 1);
                    aux = t.T;
                }
                
                tp = (tipoSimples) aux;
                tamanho *= this.tamanhoTipos.get(tp.TK.spelling);
                
                for (int i = 0; i < this.countIds; i++) {
                    try {
                        this.contador += tamanho;
                        escrever("PUSH "+ tamanho);
                    } catch (IOException ex) {
                        Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
       
                
            }
          
        }
    }

    @Override
    public void visitorDeclaracaoSequencial(declaracaoSequencial d) {
        if(d != null){
            d.D1.visit(this);
            d.D2.visit(this);
        }
    }

    @Override
    public void visitorExpressaoBinaria(expressaoBinaria e) {
         if(e != null){
            e.E1.visit(this);
            e.E2.visit(this);
            e.O.visit(this);
         }
    }

    @Override
    public void visitorExpressaoSequencial(expressaoSequencial e) {
        if(e != null){
            e.E1.visit(this);
            e.E2.visit(this);
        }
    }

    @Override
    public void visitorIdentificadorSequencial(identificadorSequencial i) {
        if(i != null){
            i.I1.visit(this);
            i.I2.visit(this);
        }
    }

    @Override
    public void visitorIdentificadorSimples(identificadorSimples i) {
        if(i != null){
            this.countIds++;
            i.TK.visit(this);
        }
    }

    @Override
    public void visitorTipoAgregado(tipoAgregado t) {
        t.L1.visit(this);
        t.L2.visit(this);
        t.T.visit(this);
    }

    @Override
    public void visitorTipoSimples(tipoSimples t) {
        if (t != null) {
            t.TK.visit(this);      
        }
    }

    @Override
    public void visitorComandoIterativo(comandoIterativo c) {
         if (c != null) { 
             try {
                 this.countLabels++;
                 escrever("h" + this.countLabels + ":");
             } catch (IOException ex) {
                 Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
             }
            c.E.visit(this);
             try {
                 escrever("JUMPIF(0) g" + this.countLabels);
             } catch (IOException ex) {
                 Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
             }
            c.C.visit(this);
             try {
                 escrever("JUMP h" + this.countLabels  + "\n" + "g" + this.countLabels + ":");
             } catch (IOException ex) {
                 Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }

    @Override
    public void visitorComandoCondicional(comandoCondicional arg0) {
        if (arg0 != null) {
            arg0.E.visit(this);
            this.countLabels++;
            try {
                escrever("JUMPIF (0) g" + this.countLabels);
            } catch (IOException ex) {
                Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
            }
            arg0.C1.visit(this);
            try {
                escrever("JUMP h" + this.countLabels);
                escrever("g" + this.countLabels + ":");
            } catch (IOException ex) {
                Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(arg0.C2 !=  null)
                arg0.C2.visit(this);
            try {
                escrever("h" + this.countLabels + ":");
            } catch (IOException ex) {
                Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            arg0.E.visit(this);
            if(arg0.V.E != null) {
                    arg0.V.E.visit(this);
                    tipoAgregado t;
                    t =  (tipoAgregado) arg0.V.ponteiro.tipo;
                    if(!(arg0.V.E instanceof expressaoSequencial)){
                        try {
                            escrever("LOADL " + Integer.parseInt(t.L1.TK.spelling));
                            escrever("CALL sub");
                            escrever("LOADL " + this.tamanhoTipos.get(arg0.V.tipo));
                            escrever("CALL mult");
                            escrever("LOADA " + arg0.V.endereco + "[SB]");
                            escrever("CALL add");
                            escrever("STOREI " + this.tamanhoTipos.get(arg0.V.tipo));
                        } catch (IOException ex) {
                            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }else{
                        Tipo aux = arg0.V.ponteiro.tipo;
                        try {
                            int tamanhos[] = new int[30]; 
                            int minimos[] = new int[30]; 
                            int count = 0;
                            
                            while(aux instanceof tipoAgregado ){
                                t = (tipoAgregado) aux;  
                                tamanhos[count] = (Integer.parseInt(t.L2.TK.spelling) -Integer.parseInt(t.L1.TK.spelling) + 1);
                                minimos[count] = Integer.parseInt(t.L1.TK.spelling);
                                aux = t.T;
                                count++;
                            }
                            count --;
                            
                            escrever("LOADL " + Integer.parseInt(t.L1.TK.spelling));
                            escrever("CALL sub");
                            escrever("LOADL " + this.tamanhoTipos.get(arg0.V.tipo));
                            escrever("CALL mult");
                            
                            for(int x = count; x >0; x--){
                                escrever("STORE " + "(" + this.tamanhoTipos.get(arg0.V.tipo) + ") " + "0[RA]");
                                escrever("LOADL " + minimos[x-1]);
                                escrever("CALL sub");
                                escrever("LOADL " + this.tamanhoTipos.get(arg0.V.tipo));
                                escrever("CALL mult");
                                
                                int tam = tamanhos[x];
                                
                                if(x > 0)
                                    tamanhos[count -1] *= tamanhos[count];
                                
                                escrever("LOADL " + tam);
                                escrever("CALL mult");

                                escrever("LOAD " + "(" + this.tamanhoTipos.get(arg0.V.tipo) + ") "+ "0[RA]");
                                escrever("CALL add");
                             
                            }
                            escrever("LOADA " + arg0.V.endereco + "[SB]");
                            escrever("CALL add");
                            escrever("STOREI " + this.tamanhoTipos.get(arg0.V.tipo) + "\n");
                            } catch (IOException ex) {
                                Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                            }   
                    }
            } else {
                  try {
                      escrever("STORE " + "(" + this.tamanhoTipos.get(arg0.V.tipo) + ") " + arg0.V.endereco + "[SB]");
                  } catch (IOException ex) {
                      Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }
        }
    }

    @Override
    public void visitorVariavel(Variavel arg0) {
        if (arg0 != null) {
            identificadorSimples i = (identificadorSimples) arg0.I;
            if(arg0.ponteiro.tipo instanceof tipoSimples){
                try {
                    escrever("LOAD " + "(" + this.tamanhoTipos.get(arg0.tipo) + ") " + arg0.endereco + "[SB]");
                } catch (IOException ex) {
                    Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                }
                arg0.I.visit(this);
            }else{
                if (arg0.E != null){
                    arg0.E.visit(this);
                    tipoAgregado t;
                    t =  (tipoAgregado) arg0.ponteiro.tipo;
                    if(!(arg0.E instanceof expressaoSequencial)){
                        try {
                            escrever("LOADL " + Integer.parseInt(t.L1.TK.spelling));
                            escrever("CALL sub");
                            escrever("LOADL " + this.tamanhoTipos.get(arg0.tipo));
                            escrever("CALL mult");
                            escrever("LOADA " + arg0.endereco + "[SB]");
                            escrever("CALL add");
                            escrever("LOADI " + this.tamanhoTipos.get(arg0.tipo));
                        } catch (IOException ex) {
                            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }else{
                        Tipo aux = arg0.ponteiro.tipo;
                        try {
                            int tamanhos[] = new int[30]; 
                            int minimos[] = new int[30]; 
                            int count = 0;
                            
                            while(aux instanceof tipoAgregado ){
                                t = (tipoAgregado) aux;  
                                tamanhos[count] = (Integer.parseInt(t.L2.TK.spelling) -Integer.parseInt(t.L1.TK.spelling) + 1);
                                minimos[count] = Integer.parseInt(t.L1.TK.spelling);
                                aux = t.T;
                                count++;
                            }
                            count --;
                            
                            escrever("LOADL " + Integer.parseInt(t.L1.TK.spelling));
                            escrever("CALL sub");
                            escrever("LOADL " + this.tamanhoTipos.get(arg0.tipo));
                            escrever("CALL mult");

                            for(int x = count; x >0; x--){
                                escrever("STORE " + "(" + this.tamanhoTipos.get(arg0.tipo) + ") " + "0[RA]");
                                escrever("LOADL " + minimos[x-1]);
                                escrever("CALL sub");
                                escrever("LOADL " + this.tamanhoTipos.get(arg0.tipo));
                                escrever("CALL mult");
                                
                                int tam = tamanhos[x];
                                
                                if(x > 0)
                                    tamanhos[count -1] *= tamanhos[count];
                                
                                escrever("LOADL " + tam);
                                escrever("CALL mult");

                                escrever("LOAD " + "(" + this.tamanhoTipos.get(arg0.tipo) + ") "+ "0[RA]");
                                escrever("CALL add");
                             
                            }
                            escrever("LOADA " + arg0.endereco + "[SB]");
                            escrever("CALL add");
                            escrever("LOADI " + this.tamanhoTipos.get(arg0.tipo) + "\n");
                            
                        } catch (IOException ex) {
                            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
                        }
                          
                                  
//                        tipoAgregado t;
//                        Tipo aux = arg0.ponteiro.tipo;
//                        int count = 0;
//                        int enderecoVirtual;
//                        int tamanhos[] = new int[30]; 
//                        
//                        while(aux instanceof tipoAgregado ){
//                            t = (tipoAgregado) aux;  
//                            tamanhos[count] = (Integer.parseInt(t.L2.TK.spelling) -Integer.parseInt(t.L1.TK.spelling) + 1) * this.tamanhoTipos.get(arg0.tipo)*Integer.parseInt(t.L1.TK.spelling);
//                            aux = t.T;
//                            count++;
//                        }
//                        
//                        for (int x = (count -1); x >= 0; x--) {
//                            System.out.println(i.TK.spelling + " virtual = " + tamanhos[x]);
//                        }
                    
                    }
                   
            }
        }
    }
    }

    @Override
    public void visitorLiteral(Literal arg0) {
        
        if (arg0 != null) {
            try {
                System.out.println("\n eaiii" +  arg0.TK.spelling);
                escrever("LOADL "+ arg0.TK.spelling);
            } catch (IOException ex) {
                Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
            }
            arg0.TK.visit(this);
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
    public void visitorOperador(Operador O) {
        String aux = null;
        
        switch(O.TK.spelling){
            case "+":
               aux = "add";break;
            case "-":
               aux = "sub";break;
            case "/":
               aux = "div";break;
            case "*":
               aux = "mult";break;
            case ">=":
               aux = "gte";break;
            case "<=":
               aux = "lte";break;
            case ">":
               aux = "gt";break;
            case "<":
               aux = "lt";break;
            case "and":
               aux = "and";break;
            case "or":
               aux = "or";break;
            case "=":
               aux = "equal";break;
            case "<>":
               aux = "diff";break;
        }
        
        try {
            escrever("CALL " + aux);
        } catch (IOException ex) {
            Logger.getLogger(Coder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        O.TK.visit(this);
    }
    
}
