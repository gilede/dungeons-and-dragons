package game.callbacks;

import game.callbacks.Visitor;

public interface Visited {
    void accept(Visitor v);
}
