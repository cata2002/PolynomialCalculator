package data_model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.floor;

public class Polynomial {
    private Map<Integer, Double> polynomial = new HashMap<Integer, Double>();

    public Map<Integer, Double> getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(Map<Integer, Double> polynomial) {
        this.polynomial = polynomial;
    }

    public Polynomial(Map<Integer, Double> polynomial) {
        this.polynomial = polynomial;
    }

    public Polynomial() {
    }

    public int getHighestDegree(Polynomial p) {
        int max = -9;
        for (int deg : p.polynomial.keySet()) {
            if (deg > max)
                max = deg;
        }
        return max;
    }

    public int getLowestDegree(Polynomial p) {
        int max = 999;
        for (int deg : p.polynomial.keySet()) {
            if (deg < max) max = deg;
        }
        return max;
    }

    public Polynomial cleanPoly(Polynomial p) {
        double toRemove = 0.0;
        int key = -1;
        for (Map.Entry<Integer, Double> deg : p.polynomial.entrySet()) {
            if (deg.getValue().equals(0.0)) {
                toRemove = deg.getValue();
                key = deg.getKey();
            }
        }
        p.polynomial.remove(key, toRemove);
        return p;
    }

    @Override
    public String toString() {
        StringBuilder poly = new StringBuilder();
        boolean ok = true;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        for (int exponent : polynomial.keySet()) {
            double coeff = polynomial.get(exponent);
            if (coeff == 0) continue;
            if (!ok && coeff > 0) poly.append("+");
            if (coeff == 1 && exponent != 0) {
                poly.append("");
            } else if (coeff == -1 && exponent != 0) {
                poly.append("-");
            } else {
                if (coeff != (int) coeff)
                    poly.append(numberFormat.format(coeff)) ;
                else poly.append((int) coeff);
            }
            if (exponent != 0) {
                poly.append("x");
                if (exponent != 1) {
                    poly.append("^");
                    poly.append(exponent);
                }
            }
            ok = false;
        }
        if (ok) return "0";
        return poly.toString();
    }
}
