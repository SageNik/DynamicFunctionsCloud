package com.chisw.dynamicFunctions.entity.function;

import com.chisw.dynamicFunctions.entity.Calculation;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for calculation Cos(a,b,x) function
 */
@Entity
@Data
public class Cos extends PrimitiveFunction {

    /**
     * Constructor with all parameters
     */
    public Cos(String name) {
        super(name);
    }
    public Cos(){super();}

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Calculation> evaluate(Float x, String userName) {

        List<Calculation> calculations = new ArrayList<>();
        Float result = (float)(a * Math.cos(x + b));
        calculations.add(new Calculation(this, userName, result, x));
        return calculations;
    }
}
