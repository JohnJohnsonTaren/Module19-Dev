package com.example;

/**
 * A simple test class to verify Checkstyle.
 */
public class Checkstyle {
    private String testField;
    
    public Checkstyle(String testField) {
        this.testField = testField;
    }
    
    /**
     * Test method for Checkstyle.
     * @return test string
     */
    public String getTestField() {
        return testField;
    }
}
