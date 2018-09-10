package com.chisw.dynamicFunctions.entity.function;

import com.chisw.dynamicFunctions.entity.Calculation;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for calculation Sin(a,b,x) function
 */
@Entity
@Data
public class Sin extends PrimitiveFunction {

    /**
     * Constructor with all parameters
     */
    public Sin(String name) {
        super(name);
    }
    public Sin(){ super();}
    /**
     *{@inheritDoc}
     */
    @Override
    public List<Calculation> evaluate(Float x, String userName) {

        List<Calculation> calculations = new ArrayList<>();
        Float result = (float)(a*Math.sin(x+b));
        calculations.add(new Calculation(this, userName, result, x));
        return calculations;
    }
}
