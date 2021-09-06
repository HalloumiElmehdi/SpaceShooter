package com.mygdx.game.datastructure;

import java.util.NoSuchElementException;

public class CircularSinglyLinkedList <T>{

    private Node<T> first;
    private Node<T> last;
    private int size ;
    public CircularSinglyLinkedList(){
        first = last = null;
    }
    public void setFirst(Node<T> node){
        first = node;
    }
    public void setLast(Node<T> node){
        last = node;
    }
    public Node<T> getFirst(){
        return first;
    }
    public Node<T> getLast(){
        return last;
    }

    //addLast
    public void add(T item){
        Node<T> node = new Node<T>();
        node.setObject(item);
        if(isEmpty()){
            first = last = node;
        }else{
            last.setNext(node);
            last = node;
            last.setNext(first);
        }
        size++;
    }
    //deleteFirst
    public void remove(Node<T> node){
        if(isEmpty())
            throw new NoSuchElementException();
        Node<T> previous = this.getPrevious(node);
        Node<T> next = node.getNext();
        previous.setNext(next);
        size--;

    }

    public Node<T> getPrevious(Node<T> node){
        Node<T> current = first;
        while(current.getNext() != last){
            if(current.getNext() == node){
                return current;
            }
            current = current.getNext();
        }
        return current;
    }
    //contains
    public boolean contains(T item){
        return indexOf(item) != -1;
    }
    //indexOf
    public int indexOf(T item){
        Node<T> head = first;
        int i = 0;
        while(head != null){
            if(head.getObject() == item)
                return i;
            head = head.getNext();
            i++;
        }
        return -1;
    }
    //isEmpty
    private boolean isEmpty(){
        return first == null;
    }
    //size
    public int size(){
        return size;
    }

}
