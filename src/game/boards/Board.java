package game.boards;

import game.tiles.tile.Empty;
import game.tiles.Tile;
import game.tiles.units.enemies.Enemy;
import game.tiles.units.players.Player;
import game.utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private List<Tile> tiles;
    private int row_length;
    public Board(Tile[][] board){
        this.row_length = board[0].length;
        tiles = new ArrayList<>();
        for(Tile[] line:board){
            tiles.addAll(Arrays.asList(line));
        }
    }
    public Tile get(int x, int y) throws Exception {
        for (Tile t : tiles) {
            if (t.getPosition().equals(Position.at(x, y))) {
                return t;
            }
        }
        throw new Exception("No such tile");
    }
    public void remove(Enemy e) {
        tiles.remove(e);
        Position p = e.getPosition();
        tiles.add(new Empty(p));
    }
    public void remove(Player player) {
        tiles.remove(player);
        Position p = player.getPosition();
        Empty emp = new Empty(p,'X');
        tiles.add(emp); //need to put X
    }
    @Override
    public String toString() {
        int i=0;
        List<Tile> sortedTiles = tiles.stream().sorted().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (Tile tile : sortedTiles) {
            if(i==row_length){
                i=0;
                sb.append("\n");
            }
            i++;
            sb.append(tile);
        }
        return sb.toString();

    }

}
