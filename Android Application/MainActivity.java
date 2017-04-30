package com.example.root.directev3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //variable initialization
    private Socket socket2ev3 = null;
    private Button forwardButton;
    private Button backwardButton;
    private Button leftwardButton;
    private Button rightwardButton;
    private EditText passwordField;
    private EditText timeField;

    private String directionPlusTime = null;
    private String fullMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //functions call
        conn2EV3();
        firing();
    }

    //function to connect to the ev3
    public void conn2EV3(){

        new Thread(){ //threading is used to make the connection concurrently
            @Override
            public void run() {

                try{
                    //make a socket connection to the ev3
                    socket2ev3 = new Socket("192.168.10.100", 3000);

                }catch(Exception e){
                    System.out.println(e);
                }

            }
        }.start();
    }


    public void firing(){

        //declaring the variables
        forwardButton       = (Button) findViewById(R.id.fwdBtn);
        backwardButton      = (Button) findViewById(R.id.backBtn);
        leftwardButton      = (Button) findViewById(R.id.leftBtn);
        rightwardButton     = (Button) findViewById(R.id.btnRight);
        passwordField       = (EditText) findViewById(R.id.passwordHolder);
        timeField           = (EditText) findViewById(R.id.timeHolder);

        //set a click listener on the forward button
        forwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //variable initialization
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        //get the password from the password input field
                        password = passwordField.getText().toString();

                        //convert the time(integer) to a string
                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        //declare the direction
                        direction = "F";
                        //function call with 3 paramters
                        fireData(direction, password, time);
                    }
                }
        );

        //set a click listener on the backward button
        backwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {

                        //variable initialization
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        //get the password from the password input field
                        password = passwordField.getText().toString();

                        //convert the time(integer) to a strin
                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        //direction declaration
                        direction = "B";
                        //function call
                        fireData(direction, password, time);
                    }
                }
        );

        leftwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        password = passwordField.getText().toString();

                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        direction = "L";
                        fireData(direction, password, time);
                    }
                }
        );

        rightwardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = null;
                        int time = 3000;
                        String direction = null;

                        password = passwordField.getText().toString();

                        try{
                            time  = Integer.parseInt(timeField.getText().toString());
                        }catch(Exception e){
                            System.out.println(e);
                        }
                        direction = "R";
                        fireData(direction, password, time);
                    }
                }
        );

    }


    //function to send the data to ev3
    public void fireData(String x, String y, int z){

        //variable declaration
        BufferedWriter bw = null;
        String movement = x;
        String password  = y;
        int time = z * 1000; //convert the time (second) to milisecond unit
        String Stringtime = Integer.toString(time); //convert the integer type time to string type time

        directionPlusTime = movement.concat(Stringtime); //concatenate the direction with the time (e.g. F3000)
        fullMessage = password.concat(directionPlusTime);  //concatenate the password with the direction and the time

        try{
            //sends the data through the socket connection to ev3
            bw = new BufferedWriter(new OutputStreamWriter(socket2ev3.getOutputStream()));
            bw.write(fullMessage);
            bw.newLine();
            bw.flush(); //ends the data transfer

        }catch (Exception e){
            System.out.println(e);
        }


    }


}
