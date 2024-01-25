package com.tp.goserver;

import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class SessionService {

    private ArrayList<Integer> sessions = new ArrayList<>();

    public boolean addCheck(int session) {
        if (sessions.contains(session)) {
            return false;
        }

        sessions.add(session);
        return true;
    }

    public boolean ifContain(int session) {
        return sessions.contains(session);
    }
}
