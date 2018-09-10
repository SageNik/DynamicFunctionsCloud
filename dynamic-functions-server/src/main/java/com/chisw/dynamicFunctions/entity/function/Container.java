package com.chisw.dynamicFunctions.entity.function;

import com.chisw.dynamicFunctions.entity.Calculation;
import com.chisw.dynamicFunctions.entity.Function;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistent Container entity with JPA markup.
 */
@Entity
@Data
public class Container extends Function {

    public Container(){super();}
    public Container(String name) {
        super(name);
        this.asContainer = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Calculation> evaluate(Float x, String userName) {
        List<Calculation> calculations = new ArrayList<>();
        for(Function function : functions){
            calculations.addAll(function.evaluate(x, userName));
        }
        return calculations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchOn(Float a, Float b) {
        this.switchedOn = true;
        this.a = a;
        this.b = b;
        for(Function function : functions){
             function.switchOn(a,b);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchOff() {
        this.switchedOn = false;
        this.a = null;
        this.b = null;
        for(Function function : functions){
            function.switchOff();
        }
    }

}
