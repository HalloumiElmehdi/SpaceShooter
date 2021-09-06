package com.mygdx.game.datastructure;

public class Node<T> {
    private T object;
    private Node<T> next;
    public void setObject(T object){
        this.object = object;
    }
    public void setNext(Node node){
        this.next = node;
    }
    public T getObject(){
        return  this.object;
    }
    public Node<T> getNext(){
        return next;
    }


}