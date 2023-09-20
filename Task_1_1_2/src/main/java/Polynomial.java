/**
 * Task_1_1_2.
 */

public class Polynomial {
    private int[] coeff;
    private int deg;

    /**
     * Creating a polynomial with specified coefficients.
     */
    public Polynomial(int[] args) {
        int n = args.length;
        if (n == 0) {
            System.out.println("The array is empty!");
        }
        coeff = new int[n];
        deg = n - 1;
        for (int i = 0; i < n; i++) {
            coeff[i] = args[i];
        }
    }

    /**
     * Addition of polynomials.
     */
    public Polynomial plus(Polynomial a) {
        int n = Math.max(a.deg, this.deg);
        Polynomial res = new Polynomial(new int[n + 1]);
        for (int i = 0; i <= a.deg; i++) {
            res.coeff[i] += a.coeff[i];
        }
        for (int i = 0; i <= this.deg; i++) {
            res.coeff[i] += this.coeff[i];
        }
        return res;
    }

    /**
     * Subtraction of polynomials.
     */
    public Polynomial minus(Polynomial a) {
        Polynomial res = new Polynomial(new int[Math.max(this.deg, a.deg) + 1]);
        for (int i = 0; i <= this.deg; i++) {
            res.coeff[i] += this.coeff[i];
        }
        for (int i = 0; i <= a.deg; i++) {
            res.coeff[i] -= a.coeff[i];
        }
        return res;
    }

    /**
     * Multiplication of polynomials.
     */
    public Polynomial times(Polynomial a) {
        Polynomial res = new Polynomial(new int[this.deg + a.deg + 1]);
        for (int i = 0; i <= this.deg; i++) {
            for (int j = 0; j <= a.deg; j++) {
                res.coeff[i + j] += (this.coeff[i] * a.coeff[j]);
            }
        }
        return res;
    }

    /**
     * Calculating the value at a point.
     */
    public int evaluate(int a) {
        int res = 0;
        for (int i = 0; i <= this.deg; i++) {
            res += (this.coeff[i] * Math.pow(a, i));
        }
        return res;
    }

    /**
     * Taking the i`th derivative.
     */
    public Polynomial differentiate(int a) {
        Polynomial pol = this;
        Polynomial res = this;
        for (int i = 0; i < a; i++) {
            res = pol.derivative();
            pol = res;
        }
        return res;
    }

    public Polynomial derivative() {
        Polynomial res;
        if (this.deg == 0) {
            res = new Polynomial(new int[1]);
        }
        else {
            res = new Polynomial(new int[this.deg]);
            for (int j = 1; j <= this.deg; j++) {
                res.coeff[j - 1] = this.coeff[j] * j;
            }
        }
        return res;
    }

    /**
     * Checking for equality with another polynomial.
     */
    public boolean equality(Polynomial a) {
        if (this.deg != a.deg) {
            return false;
        } else {
            boolean res = true;
            for (int i = 0; i <= a.deg; i++) {
                if (this.coeff[i] != a.coeff[i]) {
                    res = false;
                    break;
                }
            }
            return res;
        }
    }

    /**
     * Getting a string representation.
     */
    public String toString() {
        if (deg == 0) {
            return "" + coeff[deg];
        }
        String s;
        if (deg == 1) {
            s = coeff[deg] + "x";
        } else {
            s = coeff[deg] + "x^" + deg;
        }
        for (int i = deg - 1; i >= 0; i--) {
            if (coeff[i] == 0) {
                continue;
            } else if (i == 0) {
                if (coeff[0] > 0) {
                    s = s + " + " + coeff[0];
                } else {
                    s = s + " - " + (-coeff[0]);
                }
            } else if (i == 1) {
                if (coeff[1] > 0) {
                    s = s + " + " + coeff[1] + "x";
                } else {
                    s = s + " - " + (-coeff[1]) + "x";
                }
            } else {
                if (coeff[i] > 0) {
                    s = s + " + " + coeff[i] + "x^" + i;
                } else {
                    s = s + " - " + (-coeff[i]) + "x^" + i;
                }
            }
        }
        return s;
    }
}
