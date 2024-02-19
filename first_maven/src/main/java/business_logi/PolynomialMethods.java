package business_logi;

import data_model.Polynomial;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolynomialMethods {
    public static Polynomial addPoly(Polynomial p1, Polynomial p2){
        Polynomial result = new Polynomial();
        Map<Integer, Double> aux = new HashMap<Integer,Double>();
        for(Map.Entry<Integer, Double> entry1 : p1.getPolynomial().entrySet()){
            for(Map.Entry<Integer, Double> entrry2 : p2.getPolynomial().entrySet()){
                if(entry1.getKey() == entrry2.getKey()){
                    aux.put(entry1.getKey(), entry1.getValue() + entrry2.getValue());
                }
                else {
                    if(!aux.containsKey(entry1.getKey()))
                        aux.put(entry1.getKey(), entry1.getValue());
                    if(!aux.containsKey(entrry2.getKey()))
                        aux.put(entrry2.getKey(), entrry2.getValue());
                }
            }
        }
        result.setPolynomial(aux);
        return result;
    }
    public static Polynomial subtractPoly(Polynomial p1, Polynomial p2){
        Polynomial result = new Polynomial();
        Map<Integer, Double> aux = new HashMap<Integer,Double>();
        for(Map.Entry<Integer, Double> entry1 : p1.getPolynomial().entrySet()){
            for(Map.Entry<Integer,Double> entrry2 : p2.getPolynomial().entrySet()){
                if(entry1.getKey() == entrry2.getKey()){
                    aux.put(entry1.getKey(), entry1.getValue() - entrry2.getValue());
                }
                else {
                    if(!aux.containsKey(entry1.getKey()))
                        aux.put(entry1.getKey(), entry1.getValue());
                    if(!aux.containsKey(entrry2.getKey()))
                        aux.put(entrry2.getKey(), -entrry2.getValue());
                }
            }
        }
        result.setPolynomial(aux);
        return result;
    }
    public static Polynomial differentiate(Polynomial p1){
        Polynomial result=new Polynomial();
        Map<Integer,Double>aux=new HashMap<Integer,Double>();
        for(Map.Entry<Integer,Double>entry:p1.getPolynomial().entrySet()){
            if(entry.getKey()==0)
                aux.put(entry.getKey(), 0.0);
            else
                aux.put(entry.getKey()-1, entry.getValue()* entry.getKey() );
        }
        result.setPolynomial(aux);
        return result;
    }
    public static Polynomial multiplyPoly(Polynomial p1, Polynomial p2){
        Polynomial result=new Polynomial();
        Map<Integer,Double>aux=new HashMap<Integer, Double>();
        for(Map.Entry<Integer,Double> entry1:p1.getPolynomial().entrySet()){
            for(Map.Entry<Integer,Double> entry2:p2.getPolynomial().entrySet()){
                if(aux.containsKey(entry1.getKey()+ entry2.getKey()))
                    aux.put(entry1.getKey()+entry2.getKey(), entry1.getValue()* entry2.getValue()+aux.get(entry1.getKey()+ entry2.getKey()));
                else aux.put(entry1.getKey()+entry2.getKey(), entry1.getValue()* entry2.getValue());
            }
        }
        result.setPolynomial(aux);
        return result;
    }

    public static Polynomial integrate(Polynomial p){
        Polynomial result=new Polynomial();
        Map<Integer,Double>aux=new HashMap<Integer,Double>();
        for(Map.Entry<Integer,Double>entry:p.getPolynomial().entrySet()){
            aux.put(entry.getKey()+1, entry.getValue()/ (entry.getKey()+1) );
        }
        result.setPolynomial(aux);
        return result;
    }

    public static double[] parseMonomial(String monomial){
        double []retString={0.0,0.0};
        String []words=new String[2];
        words=monomial.split("(x\\^)");
        if(monomial.startsWith("x^")||monomial.startsWith("+x^")){
            retString[0]=1.0;
            retString[1]=Double.parseDouble(monomial.replace("x^",""));
        } else if(monomial.startsWith("-x^")){
            retString[0]=-1.0;
            retString[1]=Double.parseDouble(monomial.replace("x^",""));
        } else if(words.length==2 && words[0]!=null && words[1]!=null) {
            retString[0]=Double.parseDouble(words[0]);
            retString[1]=Double.parseDouble(words[1]);
       } else {
           if(monomial.equals("x") || monomial.equals("+x")) {
               retString[0] = 1.0;
               retString[1] = 1.0;
           } else if(monomial.equals("-x")) {
               retString[0] = -1.0;
               retString[1] = 1.0;
           } else if (monomial.contains("x")) {
                monomial=monomial.replace("x", "");
                retString[0]=Double.parseDouble(monomial);
                retString[1]=1.0;
            } else {
                retString[0]=Double.parseDouble(monomial);
                retString[1]=0.0;
            }
        }
        return retString;
    }

    public static Polynomial getPolyMap1(String poly){ //no longer used, allowed for only ax^b type terms
        String[] numbers = poly.replace("^", "").split("((?=\\+)|(?=\\-)|x)");
        Map<Integer,Double> aux=new HashMap<Integer, Double>();
        if(numbers.length==0) return null;
        for (int i = 0; i < numbers.length; i += 2){
            aux.put(Integer.parseInt(numbers[i+1]),Double.parseDouble(numbers[i]));
        }
        Polynomial p=new Polynomial();
        p.setPolynomial(aux);
        //for(Map.Entry<Integer,Double>entry:p.getPolynomial().entrySet()){
        //    System.out.println(entry.getValue()+" "+ entry.getKey());
        //}
        return p;
    }
    public static Polynomial getPolyMap(String poly){
        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(poly);
        Map<Integer,Double> aux=new HashMap<Integer, Double>();
        double []retMono=new double[2];
        int x=0;
        while (matcher.find()) {
            //x=x+1;
            //System.out.println("Group "+x+": " + matcher.group(1));
            String monomial= matcher.group(1);
            retMono=parseMonomial(monomial);
            aux.put((int)retMono[1],retMono[0]);
        }
        Polynomial p=new Polynomial();
        p.setPolynomial(aux);
        return p;
    }

    public static int getOpCode(String op){
        return switch (op) {
            case "Add" -> 1;
            case "Subtract" -> 2;
            case "Multiply" -> 3;
            case "Differentiate" -> 4;
            case "Integrate" -> 5;
            case "Divide" ->6;
            default -> -1;
        };
    }

    public static String returnResult(String p1, String p2, int op) {
        Polynomial aux1 = new Polynomial();
        Polynomial aux2 = new Polynomial();
        aux1 = getPolyMap(p1);
        aux2 = getPolyMap(p2);
        if (op == 1) return addPoly(aux1, aux2).toString();
        else if (op == 2) return subtractPoly(aux1, aux2).toString();
        else if (op == 3) return multiplyPoly(aux1, aux2).toString();
        else if (op == 4) return differentiate(aux1).toString();
        else if (op == 5) return integrate(aux1).toString();
        else if (op == 6) return polyDivision(aux1,aux2);
        return null;
    }

    public static String polyDivision(Polynomial p1, Polynomial p2) {
        Map<Integer, Double> numarator = p1.getPolynomial();
        Polynomial numitor = new Polynomial();
        Map<Integer, Double> catAux = new HashMap<>();
        numitor = p2;
        Polynomial cat = new Polynomial();
        Polynomial rest = new Polynomial();
        rest.setPolynomial(numarator);
        while (rest.getHighestDegree(rest) >= numitor.getHighestDegree(numitor) ){
            int grad = Collections.max(rest.getPolynomial().keySet()) - Collections.max(numitor.getPolynomial().keySet());
            double coeff = rest.getPolynomial().get(Collections.max(rest.getPolynomial().keySet()))/numitor.getPolynomial().get(Collections.max(numitor.getPolynomial().keySet()));

            Polynomial term = new Polynomial();
            term.getPolynomial().put(grad, coeff);
            cat.getPolynomial().put(grad, coeff);

            Polynomial prod = multiplyPoly(term, numitor);
            rest = subtractPoly(rest, prod);
            rest.cleanPoly(rest);
            if(rest.getHighestDegree(rest) == 0) break;
        }
        String result = "Quotient= " + cat.toString() + " rest =" + rest.toString();
        return result;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static boolean checkValidPoly(String poly){
        int ok=0;
        Pattern p = Pattern.compile("^([+-]?\\d*\\.?\\d*x(\\^\\d+)?)(([+-]\\d*\\.?\\d*x(\\^\\d+)?)+)?([+-]\\d*\\.?\\d+)?$");
        Matcher m = p.matcher(poly);
        if(m.matches() || isNumeric(poly)) return true;
        return  false;
    }

}
