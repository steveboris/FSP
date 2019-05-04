package com.company;

public class Main {

    public static void main(String[] args) {
        Verifier ver = new Verifier();
        Proveder p = new Proveder("Titus", 10);
        p.register(ver);
        boolean state = p.login(ver, 7);
        if(state == true)
            System.out.println("Erfolgreiche Anmeldung!");
        else System.out.println("Anmeldung fehlgeschlagen!");
    }
}
