/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Harvy
 */
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.*;

public class Sampling {
   
 static class Node{
 long degree,index,label;
 
 public Node(){
 this.label=0;
 this.index=0;
 this.degree=0;
 }
}
 
static class Edge{
long start,end,label;

public Edge(){
this.start=0;
this.end=0;
this.label=0;
}
} 
 
static class Temp{
ArrayList<Node> nodeList;
HashSet<Long> forbidden;

public Temp(){
 nodeList=new ArrayList<>();
 forbidden=new HashSet<>();
}
}

    private static final File F= new File("C:\\Users\\Harvy\\Downloads\\GraMi-master\\Datasets\\Sampling.txt");
    
    static Temp sampling(ArrayList<Node> nodeList,HashMap<Long,Long> degreeList,
            double samplingRate)
    {
        
     ArrayList<Node> result=new ArrayList<>();
     HashSet<Long> result1=new HashSet<>();
     HashMap<Long,ArrayList<Node>> sameDegree=new HashMap<>();
     
     for(int i=0;i<nodeList.size();i++){
      Long degree=degreeList.get(nodeList.get(i).index);
      
      if(sameDegree.containsKey(degree)){
       ArrayList<Node> temp=sameDegree.get(degree);
       temp.add(nodeList.get(i));
       sameDegree.put(degree,temp);
      }
      else{
       ArrayList<Node> temp=new ArrayList<>();
       temp.add(nodeList.get(i));
       sameDegree.put(degree,temp);
      }
     }
     
     for(Map.Entry m: sameDegree.entrySet()){
      ArrayList<Node> temp=(ArrayList<Node>)m.getValue();
      int maxIndex=(int)(temp.size()*samplingRate);
       for(int i=0;i<temp.size();i++){
        if(i<=maxIndex)result.add(temp.get(i));
        else result1.add(temp.get(i).index);
       }
     }
     Temp t=new Temp();
     t.forbidden
             =result1;
     t.nodeList=result;
     
     return t;
    }
    public static void main(String[] args) throws Exception {
      FileReader fr=new FileReader("C:\\Users\\Harvy\\Downloads\\GraMi-master\\Datasets\\mico.txt");
      
      LineNumberReader lnr=new LineNumberReader(fr);
      FileWriter fw=new FileWriter(F);
      
      ArrayList<Node> nodeList=new ArrayList<>();
      ArrayList<Edge> edgeList=new ArrayList<>();
      HashMap<Long,Long> degreeList=new HashMap<>();
      
      String x=lnr.readLine();
      x=lnr.readLine();
      
      while(x!=null){
       String[] s=x.split(" ");
       if(s[0].equals("v")){
         Node n=new Node();
         n.label=Integer.parseInt(s[2]);
         n.index=Long.parseLong(s[1]);
         nodeList.add(n);
       }
       else if(s[0].equals("e")){
        Edge e=new Edge();
        e.start=Long.parseLong(s[1]);
        e.end=Long.parseLong(s[2]);
        if(e.start==e.end){
         if(degreeList.containsKey(e.start))
           degreeList.put(e.start,degreeList.get(e.start)+2);
         else degreeList.put(e.start,(long)2);
         edgeList.add(e);
        }
        else{
        if(degreeList.containsKey(e.start))
         degreeList.put(e.start,degreeList.get(e.start)+1);
         else degreeList.put(e.start,(long)1);
            
         if(degreeList.containsKey(e.end))
             degreeList.put(e.end, degreeList.get(e.end)+1);
         else degreeList.put(e.end,(long)1);
        e.label=Long.parseLong(s[3]);
        edgeList.add(e);
       }
       }
       x=lnr.readLine();
      }
      
      Temp t=sampling(nodeList,degreeList,0.5);
      nodeList=t.nodeList;
      HashSet<Long> forbidden=t.forbidden;
      
      for(int i=0;i<nodeList.size();i++){
      fw.write("v"+" "+(nodeList.get(i).index)+" "+(nodeList.get(i).label));
      fw.write("\n");
      fw.flush();
      }
      for(int i=0;i<edgeList.size();i++){
      Edge e=edgeList.get(i);
      if(forbidden.contains(e.start) || forbidden.contains(e.end) || e.start>=nodeList.size() || e.end>=nodeList.size())
       continue;
      else{
      fw.write("e"+" "+e.start+" "+e.end+" "+e.label);
      fw.write("\n");
      fw.flush();
      }
      }
    }
    
}
