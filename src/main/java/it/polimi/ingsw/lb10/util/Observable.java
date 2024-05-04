package it.polimi.ingsw.lb10.util;
import it.polimi.ingsw.lb10.network.response.Response;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private final List<Observer> observers = new ArrayList<Observer>();

    public void notifyAll(Response response){
        for(Observer observer : observers){
            observer.update(response);
        }
    }

    public void notify(Response response, int userHash){
        observers.forEach(o -> o.updateConditional(response, userHash));
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){observers.remove(observer);}


}
