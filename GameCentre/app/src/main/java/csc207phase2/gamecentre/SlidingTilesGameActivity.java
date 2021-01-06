package csc207phase2.gamecentre;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends GameActivity {

    /**
     * The board manager.
     */
    private SlidingTilesManager boardManager;

    /**
     * The name of this game.
     */
    static final String NAME = "SlidingTiles";

    /**
     * This activity's content view.
     */
    static final int CONTENT_VIEW = R.layout.activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addUndoButtonListener();
    }

    @Override
    public void updateTileButtons() {
        super.updateTileButtons();
        if (boardManager.puzzleSolved()) {
            updateScoreBoard();
            updateUserScoreBoard();
        }
    }

    /**
     * Put an updated scoreboard into the shared preferences to be accessed by score view.
     */
    public void updateScoreBoard() {
        Score newScore = new Score(boardManager.getAccountName(),
                "Sliding Tiles " + boardManager.getBoard().getNumRows());
        newScore.setScorePoint(boardManager.getNumMoves() + 1);
        SlidingTilesManager.getScoreBoard().addScore(newScore);
    }

    /**
     * Put an updated user scoreboard into the shared preferences to be accessed by user score view.
     */
    public void updateUserScoreBoard() {
        SharedPreferences prefs = this.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String user = boardManager.getAccountName();
        Score userHighScore = SlidingTilesManager.getScoreBoard().getUserHighscore(user);
        String userScoreBoard = prefs.getString(user, null);
        if(userScoreBoard != null) {
            String[] scoreText = userScoreBoard.split(",");
            for (int i = 0; i < scoreText.length; i++) {
                if (scoreText[i].matches("(.*)Sliding(.*)") || !scoreText[i].matches("(.*)Score:(.*)")) {
                    scoreText[i] = null;
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int index = 0; index < scoreText.length; index++) {
                if (scoreText[index] != null) {
                    sb.append(scoreText[index]).append(index).append(",");
                }
            }
            if (sb.toString().equals("")) {
                String appendedNewScore = userHighScore.toString();
                editor.putString(user, appendedNewScore);
            }
            else {
                userScoreBoard = sb.toString();
                String appendedNewScore = userScoreBoard + ", " + userHighScore.toString();
                editor.putString(user, appendedNewScore);
            }
        }
        else {
            editor.putString(user, userHighScore.toString());
        }
        editor.commit();
    }

    /**
     * Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.Undo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boardManager.stepSaver.empty()) {
                    boardManager.undo(boardManager.stepSaver.undo());
                } else
                    Toast.makeText(getApplicationContext(), "Invalid Undo!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets the current BoardManager for this game.
     *
     * @param m The BoardManager for this game.
     */
    @Override
    void setBoardManager(BoardManager m) {
        this.boardManager = (SlidingTilesManager) m;
    }

    /**
     * Returns the current BoardManager for this game.
     *
     * @return the current BoardManager.
     */
    @Override
    SlidingTilesManager getBoardManager() {
        return boardManager;
    }

    /**
     * Returns name of this game.
     *
     * @return this game's name.
     */
    @Override
    String getName() {
        return NAME;
    }

    @Override
    public int getContentView() {
        return CONTENT_VIEW;
    }

}
