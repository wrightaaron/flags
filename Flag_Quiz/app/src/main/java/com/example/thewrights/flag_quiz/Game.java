package com.example.thewrights.flag_quiz;

import android.content.Intent;
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
    int numRounds;
    int correctIndex;
    int numRight;
    //String[] choices = {"Australia", "Barbados", "Brazil", "Canada", "Cuba", "Germany", "France", "Great Britain", "Italy", "Sweden"};
    //int[] images = {R.drawable.au, R.drawable.bb, R.drawable.br, R.drawable.ca, R.drawable.cu, R.drawable.de, R.drawable.fr, R.drawable.gb, R.drawable.it, R.drawable.se};
    ArrayList<String> choiceList;
    ArrayList<Integer> imgList;
    ArrayList<String> opts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        choiceList = new ArrayList<String>();
        choiceList = fillChoices(choiceList);
        imgList = new ArrayList<Integer>();
        imgList = fillImages(imgList);
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

    public void playRound()
    {
        round++;
        opts.clear();
        if(round <= numRounds)
        {
            int imgIndex = getRand(imgList.size()-1);
            image.setImageResource(imgList.get(imgIndex));

            opts.add(choiceList.get(getAnswer(imgList.get(imgIndex))));
            opts = fillOptions(opts);
            shuffleList(opts);
            correctIndex = opts.indexOf(choiceList.get(getAnswer(imgList.get(imgIndex))));
            ArrayAdapter<String> choices = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, opts);
            listOptions.setAdapter(choices);
            imgList.remove(imgIndex);
            listOptions.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if(position == correctIndex)
                    {
                        numRight++;
                    }
                    playRound();
                }
            });
        }
        else
        {
            Intent finalIntent = new Intent(this, FinalScreen.class);
            startActivity(finalIntent);
        }
    }

    public int getRand(int highNum)
    {
        int min = 0;
        int max = highNum;
        Random rand = new Random();
        int randInt = rand.nextInt(max - min + 1) + min;
        return randInt;
    }

    public ArrayList<String> fillChoices(ArrayList<String> list)
    {
        list.add("Australia");
        list.add("Barbados");
        list.add("Brazil");
        list.add("Canada");
        list.add("Cuba");
        list.add("Germany");
        list.add("France");
        list.add("Great Britain");
        list.add("Israel");
        list.add("Italy");
        list.add("Jamaica");
        list.add("Japan");
        list.add("Korea");
        list.add("Mexico");
        list.add("Sweden");
        list.add("Turkey");
        return list;
    }

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

    public ArrayList<Integer> fillImages(ArrayList<Integer> list)
    {
        list.add(R.drawable.au);
        list.add(R.drawable.bb);
        list.add(R.drawable.br);
        list.add(R.drawable.ca);
        list.add(R.drawable.cu);
        list.add(R.drawable.de);
        list.add(R.drawable.fr);
        list.add(R.drawable.gb);
        list.add(R.drawable.il);
        list.add(R.drawable.it);
        list.add(R.drawable.jm);
        list.add(R.drawable.jp);
        list.add(R.drawable.kr);
        list.add(R.drawable.mx);
        list.add(R.drawable.se);
        list.add(R.drawable.tr);
        return list;
    }

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

    public int getAnswer(int imgIndex)
    {
        switch(imgIndex)
        {
            case R.drawable.au:
                return choiceList.indexOf("Australia");
            case R.drawable.bb:
                return choiceList.indexOf("Barbados");
            case R.drawable.br:
                return choiceList.indexOf("Brazil");
            case R.drawable.ca:
                return choiceList.indexOf("Canada");
            case R.drawable.cu:
                return choiceList.indexOf("Cuba");
            case R.drawable.de:
                return choiceList.indexOf("Germany");
            case R.drawable.fr:
                return choiceList.indexOf("France");
            case R.drawable.gb:
                return choiceList.indexOf("Great Britain");
            case R.drawable.il:
                return choiceList.indexOf("Israel");
            case R.drawable.it:
                return choiceList.indexOf("Italy");
            case R.drawable.jm:
                return choiceList.indexOf("Jamaica");
            case R.drawable.jp:
                return choiceList.indexOf("Japan");
            case R.drawable.kr:
                return choiceList.indexOf("Korea");
            case R.drawable.mx:
                return choiceList.indexOf("Mexico");
            case R.drawable.se:
                return choiceList.indexOf("Sweden");
            case R.drawable.tr:
                return choiceList.indexOf("Turkey");
            default:
                return -1;
        }
    }
}
