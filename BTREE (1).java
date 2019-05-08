/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adsbtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Node implements Comparable<Node>{
    ArrayList<Character> elements;
    ArrayList<Node> children;
    Node Parent;
    
    Node()
    {
        elements=new ArrayList<>();
        children=new ArrayList<>();
    }

    @Override
    public int compareTo(Node n) {
        return (int)this.elements.get(0)-(int)n.elements.get(0);
    }
}

/**
 *
 * @author gupta
 */
public class ADSBTREE {
    Node root=null;
    int t=0;
    
    void insert(char a){
        Node n;
        if(root==null)
        {
            n=new Node();
            root=n;
        }
        n=findNode(root,a);
        n.elements.add(a);
        n.elements.sort(null);
        
        while(n.elements.size()==2*t)
        {
            n.elements.sort(null);
            
            if(n==root)
            {
                Node p=new Node();
                root=p;
                n.Parent=p;
            }
            Node l=new Node();
            Node r=new Node();
            split(n,l,r);
            
            n.Parent.children.add(l);
            n.Parent.children.add(r);
            n.Parent.elements.add(n.elements.get(t-1));
            n.Parent.elements.sort(null);
            n.elements.clear();
            
            n.Parent.children.remove(n);
            Collections.sort(n.Parent.children);
            
            if(n.Parent.elements.size()==2*t)
                n=n.Parent; 
        }
    }
    
    void split(Node n,Node l,Node r)
    {
        for (int i = 0; i < t-1; i++) {
            l.elements.add(n.elements.get(i));
        }
        l.Parent=n.Parent;
        for (int i = t; i < 2*t; i++) {
            r.elements.add(n.elements.get(i));
        }
        r.Parent=n.Parent;
        
        int i=0;
        if(!n.children.isEmpty())
        {
        while((int)n.children.get(i).elements.get(0)<(int)n.elements.get(t-1))
        {
           l.children.add(n.children.get(i));
           n.children.get(i).Parent=l;
           i++;
        }
        while(i<n.children.size())
        {
            r.children.add(n.children.get(i));
            n.children.get(i).Parent=r;
            i++;
        }
        }
    }
    
    Node findNode(Node n,char a)
    {
       int i=0;
       if(n.children.isEmpty())
       {
           return n;
       }
       for(char c:n.elements)
       {
           if((int)a<(int)c)
               break;
            i++;
       }
       n=findNode(n.children.get(i),a);
       
       return n; 
    }
    
    void display(Node n,int i)
    {
        if(n.children.isEmpty())
            return;
        i++;
        for(Node c: n.children)
        {
            display(c,i);
            System.out.println(i+"->"+c.elements);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        ADSBTREE o=new ADSBTREE();
        System.out.println("provide minimum degree:");
        o.t=sc.nextInt();
        
        char c=' ';
        System.out.println("enter elements ; 'q' to exit");
        while(c!='q')
        {
            c=sc.next().charAt(0);
            if(c!='q')
                o.insert(c);
        }
        o.display(o.root,0);
        System.out.println("0->"+o.root.elements);
    }
}
