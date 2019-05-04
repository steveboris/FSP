package com.company;

import java.util.HashMap;
import java.util.Random;

public class Verifier {

    public static final int N = 101;
    public static final int RequiredPasses = 5;
    private HashMap<String, Integer> registered = new HashMap<String, Integer>();
    private Random random = new Random();
    public static enum ChallengeMode {
        Tail,
        Head,
        None
    }

    public class Session {

        private ChallengeMode mode = ChallengeMode.None;
        private int x, v, numPasses = 0;
        private boolean failed = false;

        public Session (int v, int x) {
            this.x = x;
            this.v = v;
        }

        /**
         * getChallengeMode - Hilf dabei eine Bestimmmte mode zur Berechnung auszufuehren
         * @return mode
         */
        public ChallengeMode getChallengeMode() {
            if(mode == ChallengeMode.None)
                mode = (random.nextInt(2) == 0) ? ChallengeMode.Head : ChallengeMode.Tail;
            return mode;
        }

        /**
         * check - Diese Mehode hilf dabei fuer die Pruefung der Authentification zu einer Session.
         * @param y -
         */
        public void check(int y) {

            boolean pass = false;
            int ySquared = (y * y) % N;

            if (mode == ChallengeMode.Head)
                pass = (ySquared == x);
            else if (mode == ChallengeMode.Tail)
                pass = (ySquared == (x * v % N));
            mode = ChallengeMode.None;
            if (!pass)
                failed = true;
            else
                ++numPasses;
        }

        /**
         * passed - to check the authentication 5 time according to the challenge mode.
         * This will return true if the giving information are ok.
         * @return the state of the Authentification process
         */
        public boolean passed() {
            return !failed && numPasses >= RequiredPasses;
        }

    }

    /**
     * register - Methode zur Regiatration von Benutzer
     * @param name - Name des Nutzers
     * @param v - public key des Nutzers
     */
    public void register(String name, int v) {
        if (!registered.containsKey(name))
            registered.put(name, v);
    }

    /**
     * newSession - Hilf dabei eine Session anzufangen.
     * @param name - Name des Nutzers
     * @param x - computer the computer to the authentification process.
     * @return users session
     */
    public Session newSession(String name, int x) {
        return registered.containsKey(name) ? new Session(registered.get(name).intValue(), x) : null;
    }

}
