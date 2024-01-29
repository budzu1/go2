package com.tp.goserver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ReplayService {

    private HashMap<Long, Replay> replays;

    public synchronized void createReplay(Long id, int size, ArrayList<Move> moves) {
        replays.put(id, new Replay(size, moves));
    }

    public synchronized void removeReplay(Long id) {
        replays.remove(id);
    }

    public synchronized ArrayList<ArrayList<Integer>> getNext(Long id) {
        return replays.get(id).getNext();
    }

    public synchronized ArrayList<ArrayList<Integer>> getPrev(Long id) {
        return replays.get(id).getNext();
    }
}
