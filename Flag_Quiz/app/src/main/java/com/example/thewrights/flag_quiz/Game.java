package com.example.thewrights.flag_quiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;


public class Game extends ActionBarActivity {

    ImageView image;
    ListView listOptions;
    int round;
    public static int numRounds;
    int correctIndex;
    public static int numRight;
    //The only place that new flags need to be added after adding the image to the drawable folder
    //Each choice is at the same index as it's respective image reference
    //Once added, they will be included in the game
    String[] choices = {"Australia", "Barbados", "Brazil", "Canada",
            "Cuba", "Germany", "France", "Great Britain", "Israel",
            "Italy", "Jamaica", "Japan", "Korea", "Mexico", "Sweden", "Turkey"};
    int[] images = {R.drawable.au, R.drawable.bb, R.drawable.br, R.drawable.ca,
            R.drawable.cu, R.drawable.de, R.drawable.fr, R.drawable.gb, R.drawable.il,
            R.drawable.it, R.drawable.jm, R.drawable.jp, R.drawable.kr, R.drawable.mx,
            R.drawable.se, R.drawable.tr };
    ArrayList<String> choiceList;
    ArrayList<Integer> imgList;
    ArrayList<String> opts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        choiceList = new ArrayList<String>();
        imgList = new ArrayList<Integer>();
        fillLists();
        opts = new ArrayList<String>();
        image = (ImageView)findViewById(R.id.imgFlag);
        listOptions = (ListView)findViewById(R.id.lstOptions);
        round = 0;
        numRounds = 10;
        correctIndex = -1;
        numRight = 0;
        playRound();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Each round is played in this method
    public void playRound()
    {
        round++;
        opts.clear();
        //Checking the number of rounds
        if(round <= numRounds)
        {
            //Get the random index for the image to be displayed and set the image to the imageView
            int imgIndex = getRand(imgList.size()-1);
            image.setImageResource(imgList.get(imgIndex));
            //Filling the options for the listView with the correct answer and three other random choices
            opts.add(choiceList.get(getAnswer(imgList.get(imgIndex))));
            opts = fillOptions(opts);
            // Shuffling the list of options so the correct answer will not always be located at the top of the list
            shuffleList(opts);
            //Getting the index of the correct answer from the shuffled arrayList
            correctIndex = opts.indexOf(choiceList.get(getAnswer(imgList.get(imgIndex))));
            //Creating and setting the adapter
            ArrayAdapter<String> choices = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, opts);
            listOptions.setAdapter(choices);
            //Removing the image that was used so that images do not get duplicated
            imgList.remove(imgIndex);
            //Setting the OnItemClickListener to handle the list item clicks
            //Checks the position of the list item and compares it to the correct answer's index
            listOptions.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //creating the media player object
                    MediaPlayer mp;
                    //if the answer is correct
                    if(position == correctIndex)
                    {
                        //set the sound to play
                        mp = MediaPlayer.create(Game.this, R.raw.right);
                        //increment the number of correct answers
                        numRight++;
                    }
                    else //if the answer is not correct
                    {
                        //set the sound to play
                        mp = MediaPlayer.create(Game.this, R.raw.wrong);
                    }
                    //play the sound
                    mp.start();
                    //call the method again to see if another round needs to be played
                    playRound();
                }
            });
        }
        else // if the last round has been played go to the final screen to display the score
        {
            Intent finalIntent = new Intent(this, FinalScreen.class);
            startActivity(finalIntent);
        }
    }

    //Random number generator for getting a random number to be used as an index
    public int getRand(int highNum)
    {
        int min = 0;
        int max = highNum;
        Random rand = new Random();
        int randInt = rand.nextInt(max - min + 1) + min;
        return randInt;
    }

    //Filling the listView with three random options
    public ArrayList<String> fillOptions(ArrayList<String> options)
    {
        ArrayList<String> opts = options;
        int randIndex;
        for(int i=0; i<3; i++)
        {
            do
            {
                randIndex = getRand(choiceList.size()-1);
            }while(opts.contains(choiceList.get(randIndex)));
            opts.add(choiceList.get(randIndex));
        }
        return opts;
    }

    //Filling the image and option lists from the respective arrays
    //This allows me to use ArrayList and its methods for working with the data
    public void fillLists()
    {
        for(int i=0; i<choices.length; i++)
        {
            choiceList.add(choices[i]);
            imgList.add(images[i]);
        }
    }


    //Borrowed and modified from a solution found on stack overflow
    public void shuffleList(ArrayList<String> lst)
    {
        Random rand = new Random();
        for(int i=lst.size()-1; i>0; i--)
        {
            int index = rand.nextInt(i+1);
            String a = lst.get(index);
            lst.set(index, lst.get(i));
            lst.set(i, a);
        }
    }

    //Getting the index of the correct answer in the list to add to the listView
    public int getAnswer(int imgIndex)
    {
        int index = -1;
        for(int i=0; i<choices.length; i++)
        {
            if(imgIndex == images[i])
            {
                index = choiceList.indexOf(choices[i]);
            }
        }
        return index;
    }
}
