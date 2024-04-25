package it.polimi.ingsw.lb10.util;
import java.util.ArrayList;
import java.util.List;

public abstract class Observable <T>{

    private final List<Observer<T>> observers = new ArrayList<Observer<T>>();

    public void notifyAll(T response){
        for(Observer observer : observers){
            observer.update(response);
        }
    }

    public void notify(T response, int userHash){
        observers.forEach(o -> o.updateConditional(response, userHash));
    }

    public void addObserver(Observer<T> observer){
        observers.add(observer);
    }


}
