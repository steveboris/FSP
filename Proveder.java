package com.company;

import java.util.Random;

public class Proveder {

    private String name;
    private int v;
    private Random random = new Random();

    public Proveder(String name, int v) {
        this.name = name;
        this.v = v;
    }

    /**
     * register - registration of the user to the Verifier
     * @param verifier
     */
    public void register(Verifier verifier) {
        verifier.register(name, v);
    }

    /**
     * login - Method zu einlogen eines users.
     * @param verifier - Der muss die authentication durchfueren. Alle Berechne Wege zu
     * eine Erfolgreiche Authentification der Users Testen
     * @param s - Private Key des Users
     * @return das Ergebniss des login process.
     */
    public boolean login(Verifier verifier, int s) {
        int r = random.nextInt(Verifier.N);
        int x = (r * r) % Verifier.N;
        Verifier.Session session = verifier.newSession(name, x);

        if (session == null)
            return false;

        for (int i = 0; i < Verifier.RequiredPasses; ++i) {
            Verifier.ChallengeMode mode = session.getChallengeMode();
            if (mode == Verifier.ChallengeMode.Head)
                session.check(r);
            else if (mode == Verifier.ChallengeMode.Tail)
                session.check((r * s) % Verifier.N);
        }

        return session.passed();
    }
}
