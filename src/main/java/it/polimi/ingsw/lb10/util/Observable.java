package it.polimi.ingsw.lb10.util;
import java.util.ArrayList;
import java.util.List;

public abstract class Observable <T>{

    private List<Observer<T>> observers = new ArrayList<Observer<T>>();

    public void notify(T request){
        for(Observer observer : observers){
            observer.update(request);
        }
    }

    public void addObserver(Observer<T> observer){
        observers.add(observer);
    }


}
