package csc207phase2.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Choose Complexity for Memory Game.
 */
public class MemoryChooseComplexity extends GameComponent{

    /**
     * The Memory SlidingTilesBoard manager.
     */
    private MemoryBoardManager memoryBoardManager;

    /**
     *  The name of the game this is a part of
     */
    static final String NAME = "MemoryGame";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complexity_memory);
        add2ButtonListener();
        add4ButtonListener();
        add6ButtonListener();
    }

    /**
     * Activate the 2by2 button.
     */
    private void add2ButtonListener() {
        Button Button_2 = findViewById(R.id.twoButton);
        Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryBoardManager = new MemoryBoardManager(2, 2);
                switchToGame();
            }
        });
    }

    /**
     * Activate the 4by4 button.
     */
    private void add4ButtonListener() {
        Button Button_4 = findViewById(R.id.fourButton);
        Button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryBoardManager = new MemoryBoardManager(4, 4);
                switchToGame();
            }
        });
    }

    /**
     * Activate the 6by6 button.
     */
    private void add6ButtonListener() {
        Button Button_6 = findViewById(R.id.sixButton);
        Button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryBoardManager = new MemoryBoardManager(6,6);
                switchToGame();
            }
        });
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, MemoryGameActivity.class);
        saveToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Sets the current memoryBoardManager to be m.
     *
     * @param m current board manager
     */
    @Override
    void setBoardManager(BoardManager m) {
        memoryBoardManager = (MemoryBoardManager) m;
    }

    /**
     * Returns the current Memory Game board manager.
     *
     * @return current Memory Game board manager
     */
    @Override
    BoardManager getBoardManager() {
        return memoryBoardManager;
    }

    /**
     * Returns the name of the current game.
     *
     * @return current the name of the current game
     */
    @Override
    String getName() {
        return NAME;
    }
}
