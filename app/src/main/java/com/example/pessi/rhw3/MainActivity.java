package com.example.pessi.rhw3;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridGameAdapter mObjGridGameAdapter;
    private int mTurnsTaken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpBoard();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mObjGridGameAdapter.overwriteWinningNumber(savedInstanceState.getInt("Winning_Number"));
        mTurnsTaken = savedInstanceState.getInt("Current_Guesses");
        incrementGuessesCounterAndUpdateStatusbar();

    }

    public void setUpBoard(){
        int squares =16;
        int rows = (int)(squares /Math.sqrt(squares));

        RecyclerView objRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager objLayoutManager = new GridLayoutManager(this, rows);

        objRecyclerView.setHasFixedSize(true);
       // objLayoutManager.setAutoMeasureEnabled(true);
        mObjGridGameAdapter = new GridGameAdapter(squares);

        objRecyclerView.setLayoutManager(objLayoutManager);
        objRecyclerView.setAdapter(mObjGridGameAdapter);
    }

    public void buttonHandler(View view) {
        showGuessResults((Button)view);
        incrementGuessesCounterAndUpdateStatusbar();

    }

    private void incrementGuessesCounterAndUpdateStatusbar() {
        TextView tvStatusBar = (TextView)findViewById(R.id.status_bar);
        tvStatusBar.setText("Guesses Taken: "+mTurnsTaken);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("Winning_Number", mObjGridGameAdapter.getWinningNumber());
        outState.putInt("Current_Guesses", mTurnsTaken);
    }

    private void showGuessResults(Button view){
        View sbContainer = findViewById(R.id.activity_main);
        Button currentButton = (Button) view;
        int currentElement = Integer.parseInt(currentButton.getText().toString());

        String msg = "You clicked on "+currentButton.getText().toString()+"\n";
        mTurnsTaken++;
        msg+=mObjGridGameAdapter.isWinner(currentElement) ? "This is the winning number!" :
                "Please try a different number.";
        Snackbar.make(sbContainer, msg, Snackbar.LENGTH_SHORT).show();
    }

    public void newGame(MenuItem item) {
        mObjGridGameAdapter.startNewGame();
        mTurnsTaken = 0;
        incrementGuessesCounterAndUpdateStatusbar();
        View sbContainer = findViewById(R.id.activity_main);
        Snackbar.make(sbContainer, "Welcome to a new Game!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}
