package br.com.antoniogabriel.lirelab.test_utilities;

public class TestPaths {

    public static final String TEST_ROOT =
            TestPaths.class.getClassLoader().getResource(".").getPath();

    public static final String TEST_IMAGES = TEST_ROOT + "images/";

}