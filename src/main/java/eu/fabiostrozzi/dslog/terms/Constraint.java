// Constraint.java, created on Apr 20, 2012
package eu.fabiostrozzi.dslog.terms;

/**
 * @author fabio
 * 
 */
public class Constraint implements Term {
    private String name;
    private boolean passed;
    private String passedHow;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the passedHow
     */
    public String getPassedHow() {
        return passedHow;
    }

    /**
     * @param passedHow
     *            the passedHow to set
     */
    public void setPassedHow(String passedHow) {
        this.passedHow = passedHow;
    }

    /**
     * @return the passed
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * @param passed
     *            the passed to set
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
