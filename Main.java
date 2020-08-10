package BrickBreaker.JAVA;

import javax.swing.*;
import java.lang.*;

public class Main {

    public static void main(String[] args) {

    	//Set Game Frame
		JFrame obj = new JFrame();

		//Initialize Game
		Gameplay gamePlay = new Gameplay();

		//Set Title Tag for the Window
		obj.setTitle("Breakout Ball");

		//Set Properties of the Graphics
		obj.setBounds(10, 10, 700, 600);
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
    }
}
