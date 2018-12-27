package com.example.pessi.rhw3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

public class GridGameAdapter extends RecyclerView.Adapter<GridGameAdapter.ViewHolder> {

    private boolean [] mSquares;
    private static int DEFAULT=16;
    private int mElements, mWinningNumber;
    private Random mGenerator;

    public GridGameAdapter(){
       this(DEFAULT);
    }

    public GridGameAdapter(int elements){
        if (elements% Math.sqrt (elements)==0){
            mSquares= new boolean[elements];
            mElements=elements;
            mGenerator = new Random();
            startGame();
        }
        else{
            throw new IllegalArgumentException("Number of Squares must allow for a perfect " +
                    "square board");
        }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
       View itemLayoutView = LayoutInflater.from(parent.getContext ()).inflate(
               R.layout.rv_item, parent, false
       );
       return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mButton.setText(Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return mSquares.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.button);
        }
    }

    private void startGame() {
        startGameWith(mGenerator.nextInt(mElements));
    }

    public int getWinningNumber(){
        return mWinningNumber;
    }

    public boolean isWinner (int elementNumber){
        return mSquares[elementNumber];
    }

    public void startNewGame(){
        endCurrentGame();
        startGame();
    }

    public void overwriteWinningNumber(int newWinningNumber){
        if (newWinningNumber >=0 && newWinningNumber<mSquares.length){
            endCurrentGame();
            startGameWith(newWinningNumber);
        }
        else{
            throw new IllegalArgumentException("This number is not a valid winning number");
        }
    }

    private void startGameWith(int winningNumber) {
        mWinningNumber = winningNumber;
        mSquares[mWinningNumber]=true;
    }

    private void endCurrentGame() {
        mSquares[mWinningNumber]=false;
    }
}
