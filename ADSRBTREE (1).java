/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adsrbtree;

/**
 *
 * @author gupta
 */
class Node
{
    int num;
    String color;
    Node left,right,parent;
    Node(int num){
        this.num=num;
        this.left=null;
        this.left=null;
        this.left=null;
        this.color="red";
    }
}
public class ADSRBTREE {
    Node root=null;
    Node temp=null;
    int flag;
    
    Node bstInsert(Node root,Node n)
    {
        if(root==null)
        {
            return n;
        }
        if(n.num < root.num)
        {
            root.left=bstInsert(root.left,n);
            root.left.parent=root;
        }
        if(n.num > root.num)
        {
            root.right=bstInsert(root.right,n);
            root.right.parent=root;
        }
        return root;
    }
    
    Node inorder_predecessor(Node n)
    {
        while(n.right!=null)
            n=n.right;
        return n;  
    }
    
    Node bstdelete(int num)
    {
        Node n=bstSearch(root,num);
        if(n.left==null && n.right==null)
            return n;
        else if(n.left!=null && n.right!=null)
        {
            Node inp;
            inp=inorder_predecessor(n.left);
            n.num=inp.num;
            return inp;    
        }
        else
        {
            if(n.left==null)
            {
                n.num=n.right.num;
                return n.right;
            }
            else
            {
                n.num=n.left.num;
                return n.left;
            }
        }   
    }
    
    Node bstSearch(Node n,int num)
    {
     while(n.num!=num)
     {
         n= num>n.num?n.right:n.left;
     }
     return n;
    }
    
    boolean isBlack(Node n)
    {
        if(n.color.equals("black"))
           return true;
        return false;
    }
    boolean isRed(Node n)
    {
        if(n.color.equals("red"))
           return true;
        return false;
    }
    void rotateLeft(Node n)
    {
        Node n_right = n.right;
        n.right  = n_right.left;
        if(n.right != null){
            n.right.parent = n;
        }
        if(n == root){
            root = n_right;
        }else if(n == n.parent.left){
            n.parent.left = n_right;
        }else{
            n.parent.right = n_right;
        }
        n_right.left = n;
        n_right.parent = n.parent;
        n.parent = n_right;
        
    }
    void rotateRight(Node n)
    {
        Node n_left = n.left;
        n.left = n_left.right;
        if(n.left != null){
            n.left.parent = n;
        }
        if(n == root){
            root = n_left;
        }else if(n == n.parent.left){
            n.parent.left = n_left;
        }else{
            n.parent.right = n_left;
        }
        n_left.right = n;
        n_left.parent = n.parent;
        n.parent = n_left;
        
    }
    void adjust(Node n){
        Node p=null;
        Node g=null;
        
            while(n != root && !isBlack(n) && !isBlack(n.parent)){
            p = n.parent;
            g = p.parent;
            if(p == g.left){
                Node s = g.right;
                if(s!=null && isRed(s)){
                    s.color = "black";
                    p.color = "black";
                    g.color = "red";
                    n = g;
                }
                else{
                    if(n == p.right){
                        rotateLeft(p);
                        n = p;
                        p = n.parent;
                    }
                    rotateRight(g);
                    String temp;
                    temp=p.color;
                    p.color=g.color;
                    g.color=temp;
                    n = p;
                }
            }else{
                Node s = g.left;
                if(s!=null && isRed(s)){
                    g.color = "red";
                    s.color = "black";
                    p.color = "black";
                    n = g;
                }
                else{
                    if(n == p.left){
                        rotateRight(p);
                        n = p;
                        p = n.parent;
                    }
                    rotateLeft(g);
                    String temp;
                    temp=p.color;
                    p.color=g.color;
                    g.color=temp;
                    n = p;
                }
 
            }   
        }
            root.color="black";
    }
    
    void insert(int num)
    {
        Node n=new Node(num);
        root=bstInsert(root,n);
        adjust(n);
    }
    
    void remove(Node n)
    {
        if(n.parent.left==n)
                n.parent.left=null;
        if(n.parent.right==n)
                n.parent.right=null;
        n.parent=null;
    }
    
    void delete(int num)
    {
        Node n=bstdelete(num);
        flag=0;
        Node x=n.left==null?n.right:n.left;
        if(isRed(n) && x==null)
        {
            remove(n);
        }
        else if(isRed(n) && x!=null && isBlack(x))
        {
            n.color="black";
            n.num=x.num;
            remove(x);
        }
        else if(isBlack(n) && x!=null && isRed(x))
        {
            remove(x);
        }
        else if(isBlack(n) && x==null && isRed(n.parent))
        {
            remove(n);
        }
        else
        {
            if(x!=null)
            {
                n.num=x.num;
                remove(x);
                flag=1;
            }
            
            Node s= n==n.parent.left?n.parent.right:n.parent.left;
            
            if(isRed(s))
            {
                s.color="black";
                s.parent.color="red";
                if(s==s.parent.right)
                    rotateLeft(s.parent);
                else
                    rotateRight(s.parent);
            }
            
            siblingBlack(n);
        }
    }
    
    void siblingBlack(Node n)
    {
        Node s= n==n.parent.left?n.parent.right:n.parent.left;
        
            if(s.right!=null&&(s.right.color.equals("red")) || (s.left!=null&&s.left.color.equals("red")))
            {
                System.out.println("here");
                if(s.parent.right==s)
                {
                    if(s.right==null)
                    {
                        rotateRight(s);
                        s.color="red";
                        System.out.println("...."+s.color);
                        s.parent.color="black";
                        s=s.parent;
                    }
                    s.right.color="black";
                    rotateLeft(s.parent);
                    if(flag==0)
                        remove(n);
                }
                else
                {
                    if(s.left==null)
                    {
                        rotateLeft(s);
                        s.color="red";
                        s.parent.color="black";
                        s=s.parent;
                    }
                    s.left.color="black";
                    rotateRight(s.parent);
                    if(flag==0)
                        remove(n);
                }
            }
            else
            {
                s.color="red";
                if(flag==0)
                  remove(n);
                if(isRed(s.parent))
                    s.parent.color="black";
                else
                {   
                    flag=1;
                    if(s.parent!=root)
                      siblingBlack(s.parent);
                }
            }
    }
    
    void printInorder(Node n)
{   
     if (n == null)
          return;
     printInorder(n.left);
     System.out.println(n.num+"->"+n.color); 
     printInorder(n.right);
}
    
    void display()
    {
        printInorder(root);
        System.out.println("root is:"+root.num);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ADSRBTREE o=new ADSRBTREE();
        o.insert(6);
        o.insert(4);
        o.insert(7);
        o.insert(2);
        o.insert(5);
        o.insert(20);
        o.insert(16);
        o.insert(17);
        o.display();
        o.delete(6);
        o.delete(4);
        o.delete(20);
        o.display();
    }
}
