package com.example.calculator;

import android.content.Context;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Calculator {
    private final Context context;
    public  String lastExpression;
    private  int openParentheses = 0;
    private boolean is_dot_allowed = true;
    private  final ArrayDeque<Double> ResultStack = new ArrayDeque<>();
    private  final ArrayDeque<Character> STACK = new ArrayDeque<>();
    public Calculator(Context context) {
        this.context = context;
    }
    private  final char[] operators = {'+', '-', '\u00F7', '\u00D7', '%', '.'};

    public String calculate(String operator, String input) {
        switch (operator) {
            case "backspace":
                return backspace(input);
            case "module":
                return add_operator(input, '\u0025');
            case "multiply":
                return add_operator(input, '\u00D7');
            case "divide":
                return add_operator(input, '\u00F7');
            case "subtract":
                return add_operator(input, '-');
            case "sum":
                return add_operator(input, '+');
            case "result":
                is_dot_allowed = true;
                openParentheses = 0;
                return result(input);
            case "opposite":
                return opposite(input);
            case "point":
                return add_point(input);
            case "one":
                return add_symbol(input, '1');
            case "two":
                return add_symbol(input, '2');
            case "three":
                return add_symbol(input, '3');
            case "four":
                return add_symbol(input, '4');
            case "five":
                return add_symbol(input, '5');
            case "six":
                return add_symbol(input, '6');
            case "seven":
                return add_symbol(input, '7');
            case "eight":
                return add_symbol(input, '8');
            case "nine":
                return add_symbol(input, '9');
            case "zero":
                return add_symbol(input, '0');
            case "parentheses":
                return add_parentheses(input);
            default:
                openParentheses = 0;
                is_dot_allowed = true;
                return "";
        }
    }

    private String add_symbol(String input, char symbol) {
        int len = input.length();
        if(len > 0) {
            char lastCharacter = input.charAt(input.length() - 1);
            if (len == 1 && isDigit(lastCharacter) && lastCharacter == '0') {
                return String.valueOf(symbol);
            } else if(lastCharacter == '(') {
                is_dot_allowed = true;
                return input + symbol;
            } else if(lastCharacter == ')') {
                is_dot_allowed = true;
                return input + "\u00D7" + symbol;
            } else if(lastCharacter == '.' || isDigit(lastCharacter) || isOperand(lastCharacter)) {
                if (lastCharacter == '.') {
                    is_dot_allowed = false;
                }
                return input + symbol;
            }
        } else {
            return String.valueOf(symbol);
        }
        return input;
    }

    private  String add_operator(String input, char operator) {
        int len = input.length();
        if(len == 0) {
            Toast.makeText(context, "Operator without number?", Toast.LENGTH_SHORT).show();
            return input;
        }
        char last = input.charAt(input.length() - 1);
        if (isOperand(last)) {
            input = input.substring(0, input.length() - 1);
            input += operator;
        } else if(isDigit(last)) {
            if (last == '.') {
                input += "0" + operator;
            } else {
                input += operator;
            }
        }
        else if(last == ')') {
            input += operator;
        }
        is_dot_allowed = true;
        return input;
    }

    private  String add_point(String input) {
        if(input.length() == 0) {
            if (is_dot_allowed) {
                input += "0.";
                is_dot_allowed = false;
            }
        }
        else {
            char lastElement = input.charAt(input.length() - 1);
            if(isDigit(lastElement) && lastElement != '.') {
                if (is_dot_allowed) {
                    input += ".";
                    is_dot_allowed = false;
                }
            } else if (isOperand(lastElement)) {
                if (is_dot_allowed) {
                    input += "0.";
                    is_dot_allowed = false;
                }
            }
        }
        return input;
    }

    private  String add_parentheses(String input) {
        StringBuilder stringBuilder = new StringBuilder(input);
        if(input.length() == 0) {
            stringBuilder.append("(");
            openParentheses++;
        } else if (openParentheses > 0 && stringBuilder.length() > 0) {
            char lastInput = stringBuilder.charAt(stringBuilder.length() - 1);
            if(isDigit(lastInput)) {
                int is_there_open_parentheses = stringBuilder.indexOf("(");
                if(is_there_open_parentheses == -1) {
                    stringBuilder.append("\u00D7").append("(");
                } else {
                    stringBuilder.append(")");
                    openParentheses--;
                }
            } else if(isOperand(lastInput)) {
                stringBuilder.append("(");
                openParentheses++;
            } else if(lastInput == '(') {
                stringBuilder.append("(");
                openParentheses++;
            } else if(lastInput == ')') {
                stringBuilder.append(")");
                openParentheses--;
            }
        } else if(openParentheses == 0 && stringBuilder.length() > 0) {
            char lastInput = stringBuilder.charAt(stringBuilder.length() - 1);
            if(isOperand(lastInput)) {
                stringBuilder.append("(");
            } else {
                stringBuilder.append("\u00D7").append("(");
            }
            openParentheses++;
        }
        return stringBuilder.toString();
    }

    private String opposite(String input) {
        return input;
    }

    private  String result(String input) {
        try {
            if(!isInputProper(input)) {
                Toast.makeText(context, "Wrong format used", Toast.LENGTH_SHORT).show();
                return input;
            }
            StringTokenizer tokenizer = new StringTokenizer(input, "");
            StringBuilder builder = new StringBuilder();
            while(tokenizer.hasMoreTokens()) {
                builder.append(tokenizer.nextToken()).append(" ");
            }
            lastExpression = builder.toString() + " ";
            String result = String.valueOf(solve(infixToPostfix(input)));
            double res = Double.parseDouble(result);
            if(res % 1 != 0) {
                BigDecimal decimal  = new BigDecimal(res);
                result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString();
                int index = result.indexOf(".") + 1;
                for (int i = result.length() - 1; i >= 0; i--) {
                    if (result.charAt(i) != '0') {
                        index = i;
                        break;
                    }
                }
                return result.substring(0, index + 1);
            }
            int response = (int) res;
            return String.valueOf(response);
        } catch (Exception e) {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        openParentheses = 0;
        return input;
    }






    private  String backspace(String input) {
        if (input.length() == 0) {
            return "";
        }
        char last_element = input.charAt(input.length() - 1);
        if (last_element == '.') is_dot_allowed = true;
        else if (isOperand(last_element)) is_dot_allowed = false;
        return input.substring(0, input.length() - 1);
    }

    private  boolean is_last_element_operator(String input) {
        if (input.length() == 0) return false;
        for (char operator : operators) {
            if (input.charAt(input.length() - 1) == operator) {
                return true;
            }
        }
        return false;
    }

    // The Algorithm to find infix and postfix begin

    private boolean isOperand(char c) {
        return c == '\u00F7' || c == '\u00D7' || c == '+' || c == '-' || c == '%';
    }
    private boolean isDigit(char c) {
        return Character.isDigit(c) || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '.';
    }
    private int prec(char c) {
        if (c == '%') return 4;
        if(c == '^') return 3;
        if(c == '\u00F7' || c == '\u00D7') return 2;
        if(c == '+' || c == '-') return 1;
        return -1;
    }

    private String infixToPostfix(String infix) {
        StringBuilder postix = new StringBuilder();
        for(int i = 0; i < infix.length(); i++) {
            if(isDigit(infix.charAt(i))) {
                if(i != infix.length() - 1 && isDigit(infix.charAt(i + 1))) {
                    postix.append(infix.charAt(i));
                } else {
                    postix.append(infix.charAt(i)).append(" ");
                }
            } else if(infix.charAt(i) == '(') {
                STACK.push(infix.charAt(i));
            } else if(infix.charAt(i) == ')') {
                while((STACK.element() != '(')) {
                    char temp = STACK.element();
                    postix.append(temp);
                    STACK.pop();
                }
                STACK.pop();
                postix.append(" ");
            } else if(isOperand(infix.charAt(i))) {
                if(STACK.isEmpty()) STACK.push(infix.charAt(i));
                else {
                    if(prec(infix.charAt(i)) > prec(STACK.element())) {
                        STACK.push(infix.charAt(i));
                    } else if((prec(infix.charAt(i)) == prec(STACK.element())) && infix.charAt(i) == '^') {
                        STACK.push(infix.charAt(i));
                    } else {
                        while((!STACK.isEmpty()) && (prec(infix.charAt(i)) <= prec(STACK.element()))) {
                            char temp = STACK.element();
                            postix.append(temp);
                            STACK.pop();
                        }
                        postix.append(" ");
                        STACK.push(infix.charAt(i));
                    }
                }
            }
        }
        while(!STACK.isEmpty()) {
            postix.append(STACK.peek());
            postix.append(" ");
            STACK.pop();
        }
        return postix.toString();
    }

    private double calc(double a, double b, char operation) {
        double res;
        switch(operation) {
            case '+':
                res = a + b;
                break;
            case '-':
                res = a - b;
                break;
            case '\u00D7':
                res = a * b;
                break;
            case '\u00F7':
                res = a / b;
                break;
            case '%':
                res = (a * b) / 100;
                break;
            default:
                res = 0;
                break;
        }
        return res;
    }

    private double solve(String postfix) {
        StringTokenizer items = new StringTokenizer(postfix, " ");
        while(items.hasMoreTokens()) {
            String nextToken = items.nextToken();
            if(isInteger(nextToken)) {
                ResultStack.push(Double.valueOf(nextToken));
            } else {
                if(isOperand(nextToken.charAt(0))) {
                    double b = ResultStack.element();
                    ResultStack.pop();
                    double a = ResultStack.element();
                    ResultStack.pop();
                    ResultStack.push(calc(a, b, nextToken.charAt(0)));
                }
            }
        }
        return ResultStack.element();
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if(c == '.') continue;
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    // The Algorithm to find infix and postfix end
    private boolean isInputProper(String input) {
        boolean flag = true;
        if (!containsOperator(input)) flag = false;
        if (is_last_element_operator(input)) flag = false;
        return flag;
    }

    public boolean containsOperator(String input) {
        return input.contains("+") || input.contains("-") || input.contains("\u00D7") || input.contains("\u00F7") || input.contains("%");
    }

}
