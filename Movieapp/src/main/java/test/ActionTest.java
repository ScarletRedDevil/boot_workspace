package test;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ActionTest extends JFrame{
	JButton bt;
	
	public ActionTest() {
		bt= new JButton("버튼");
		setLayout(new FlowLayout());
		
		add(bt);
		bt.addActionListener((ActionEvent e)->{
			System.out.println("버튼눌");
		});
		
		setSize(200,250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new ActionTest();
	}

}
