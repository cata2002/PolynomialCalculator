package data_model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static business_logi.PolynomialMethods.*;
import static junit.framework.Assert.assertEquals;
//import static org.junit.jupiter.api.AssertEquals.assertEquals;

public class TestOp {
    Polynomial p1;
    Polynomial p2;

    @BeforeEach
    void initPoly(){
        p1=new Polynomial();
        p2=new Polynomial();
        Map<Integer,Double> map1=new HashMap<>();
        map1.put(5,6.0);
        map1.put(2,6.0);
        Map<Integer,Double> map2=new HashMap<>();
        map2.put(2,6.0);
        map2.put(5,1.0);
        p1.setPolynomial(map1);
        p2.setPolynomial(map2);
    }

    @Test
    void testAdd() {
        String expectedResult="12x^2+7x^5";
        assertEquals(expectedResult,addPoly(p1,p2).toString());
    }

    @Test
    void testSubtract(){
        String expectedResult="5x^5";
        assertEquals(expectedResult,subtractPoly(p1,p2).toString());
    }

    @Test
    void testMultiply(){
        String expectedResult="36x^4+42x^7+6x^10";
        assertEquals(expectedResult,multiplyPoly(p1,p2).toString());
    }

    @Test
    void testDiff(){
        String expectedResult="12x+30x^4";
        assertEquals(expectedResult,differentiate(p1).toString());
    }

    @Test
    void testIntegrate(){
        String expectedResult="2x^3+x^6";
        assertEquals(expectedResult,integrate(p1).toString());
    }

    @Test
    void testDivide(){
        String expectedResult="Quotient= 3x+x^2 rest =2";
        Map<Integer,Double> dividend=new HashMap<>();
        dividend.put(3, 1.0);
        dividend.put(2, 2.0);
        dividend.put(1, -3.0);
        dividend.put(0, 2.0);
        Map<Integer,Double> divisor=new HashMap<>();
        divisor.put(1, 1.0);
        divisor.put(0, -1.0);
        p1.setPolynomial(dividend);
        p2.setPolynomial(divisor);
        assertEquals(expectedResult,polyDivision(p1,p2));
    }
}
