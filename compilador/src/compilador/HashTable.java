/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author italo
 */
public class HashTable {
    private int M = 100;
    private ArrayList<Item>[] table = new ArrayList[M];

    public HashTable() {
        for (int i = 0; i < M; i++) {
            table[i] = new ArrayList<Item>();
        }
    }
//    ArrayList<ArrayList> Table = new ArrayList();
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    
    void put(String key, int value) throws Exception {
        int hashCode = this.hash(key);
        
        if(this.containsKey(key)){
            throw new Exception("Chave duplicada");
        }
        
        Item item = new Item(key, value);
        this.table[hashCode].add(item);
    }
    
    int get(String key) {
        int hashCode = this.hash(key);
            
        for (Item item : this.table[hashCode]) {
            if(item.getKey().equals(key))
                return item.getValue();
        }
        
        return -1;
    }
    
    boolean containsKey(String key) {
        if(this.get(key) != -1){
            return true;
        }
        
        return false;
    }
    
//    public static void main(String[] args) throws IOException, Exception {
//        HashTable tt = new HashTable();
//        System.out.println("kkkkk");
//        tt.put("djeysi", 0);
//        tt.put("adolfo", 1);
//        tt.put("adolfo", 1);
//        
//        
//        System.out.println(tt.get("itim"));
//        System.out.println(tt.get("adolfo"));
//        System.out.println(tt.get("djeysi"));
//        
//        System.out.println(tt.hash("itim"));
//        System.out.println(tt.hash("adolfo"));
//        System.out.println(tt.hash("djeysi"));
//    }
        
}


