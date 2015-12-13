

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/* TopLevelDemo.java requires no other files. */
public class TopLevelDemo {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * EXTRA COMMENT ADDED TO TEST GIT IN SOURCTREE
     */
	
	
	
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TopLevelDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));
       
        
        frame.add(new MyPanel());
        frame.setJMenuBar(greenMenuBar);
        

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

class MyPanel extends JPanel  {
	
	Key	pianokey;
	Keyboard keyboard;
	Queue<NoteDuration> notesqueue = new LinkedList<NoteDuration>();
	JButton playButton = new JButton("Play Music");
	NoteDuration	currentnote;

	
    public MyPanel() {
      setBorder(BorderFactory.createLineBorder(Color.black));
      //this.pianokey = new Key(100, 100, 100, 300);
      
      this.setLayout(null);
      //playButton.setLayout(null);
      playButton.setBounds(10,10,150,50);
      this.add(playButton);
         
           
     //Display Keyboard 
      this.keyboard = new Keyboard();
      System.out.println("Keyboard Created");
      
      //Register Mouse Listener and check if key pressed
      addMouseListener(new MouseAdapter(){
          public void mousePressed(MouseEvent e){
              playMusic(e.getX(),e.getY());
        	  clickNote(e.getX(),e.getY());
          }
      });
    }
    
    private void clickNote(int mousex, int mousey){
    	int i; 
    	i = keyboard.whichKey(mousex, mousey);
    	if ( i != Const.nonote){
    		System.out.println(Integer.toString(i));
    		notesqueue.add(new NoteDuration(i,1000));
    	}
    	
    	
    	
    		
    	}
    	
    private void playMusic(int mousex, int mousey) {
    	if (playButton.contains(mousex, mousey) == true){
    		System.out.println("Play Button Pressed");
    		while (!notesqueue.isEmpty()){
    			//Use copy constructor to copy class popped from queue
    			currentnote = new NoteDuration (notesqueue.remove());
    			System.out.println(currentnote.note);
    		}
    	}
    }
    

    public Dimension getPreferredSize() {
        return new Dimension(1070,500);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        keyboard.paintKeyboard(g);
        
    }  
}

class Key extends Rectangle {
	
	Color	keycolor;
	
	Key(int x, int y, int w, int h, Color c){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.keycolor  = c;
	}
	
	public void paintKey (Graphics g){
		g.setColor(Color.BLACK);
        g.drawRect(this.x,this.y,this.width, this.height); 
        g.setColor(this.keycolor);
        g.fillRect(this.x,this.y,this.width, this.height);
		
	}
		
}



class Keyboard {
	
	int i = 0;
	int gap = 0;

	Key[] pianokeyboard = new Key[Const.octave];
	Color [] keycolors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN,
			Color.MAGENTA, Color.PINK
	};
	
	Keyboard(){
	while (i < Const.octave){
		pianokeyboard[i] = new Key( (100*(i+1))+gap, 100, 100, 300, keycolors[i]);
				i++;
				gap = 10*i;
		}
	}
	public void paintKeyboard(Graphics g){
		i = 0;
		while (i < Const.octave){
			pianokeyboard[i].paintKey(g);
			i++;
			}
		
		
	}
	
	public int whichKey(int x, int y){
		
		for (int i = 0; i < Const.octave; i++){
			if (pianokeyboard[i].contains(x,y) == true){
				return i;
			}
		}
		System.out.println("Mouse Clicked Outside Keyboard");
		return Const.nonote;
	}
	
	 
	
	
}

class Const {
	
	public static int octave = 8;
	public static int nonote = -1;
	
}

class NoteDuration  {
	int note;
	int time ; //milliseconds
	
	NoteDuration(int n, int t){
		this.note = n;
		this.time = t;
	}
	
	NoteDuration(NoteDuration obj){
		this.note = obj.note;
		this.time = obj.time;
	}
		
}


