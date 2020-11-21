package snakepkg;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import javax.swing.Timer;


import javax.swing.JFrame;
import java.awt.Color;


public class Snake_gui extends JFrame implements ActionListener{
	public final int dots = 25;
	public final int MAX_BODY = 250000;
	public final int window_width = 1000;
	public final int window_height = 800;
    private final int x[] = new int[MAX_BODY];
    private final int y[] = new int[MAX_BODY];
    private final int x2[] = new int[MAX_BODY];
    private final int y2[] = new int[MAX_BODY];
    private final int TIME_SET = 70;
    private final int bnum = 10;
    Font fm = new Font("Serif" , Font.PLAIN , 40);
    
    //�{�^���������ꂽ����
    private boolean leftDirection = false;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private int apple_x;
    private int apple_y;
    private int square;
    private boolean game_over; //true�Ȃ�Q�[����
    private Timer timer;
    private int score = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Snake_gui frame = new Snake_gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Snake_gui() {
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setTitle("Snake GAME");
		setSize(window_width + 200, window_height +200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�L�[���͂�L����
		MyKeyAdapter myKeyAdapter = new MyKeyAdapter();
		addKeyListener(myKeyAdapter);
		initGame();
	}
	
	//�Q�[���̏����ݒ�
	public void initGame() {
		rightDirection = true;
		leftDirection = false;
		upDirection = false;
		downDirection = false;
		game_over = true;
		score = 0;
		
		make_block();
		make_apple();
		square = 3;
		for(int i=0;i<square;i++) {
			x[i] = 100 - i*dots;
			y[i] = 100; 
		}
		
		timer = new Timer(TIME_SET, this);
		timer.start(); //ActionPerFormer���^�C�}�[���Ԃ��ƂɋN��
	}
	
    @Override
    public void paint(Graphics g) {
    	//�Q�[����ʂ�\��
    	g.drawImage(getScreen(), 100, 100, this);
    	//�X�R�A��\��
    	g.drawImage(getScreen2(), 80, 40, this);
    	
    }
    //�_�u���o�b�t�@�p�̃X�N���[���̏�ɕ`�悵�Ă���
    private Image getScreen() {
		Image screen = createImage(window_width,window_height);
	    Graphics2D g = (Graphics2D)screen.getGraphics();
	    
	    //�w�i�ݒ�
	    g.setColor(Color.black);
    	g.fillRect(0, 0, window_width, window_height);

    	if(game_over) {
		    // �^�[�Q�b�g�`��
    		g.setColor(Color.gray);
    		for(int i=0;i<bnum;i++) {
    			g.fillRect(x2[i], y2[i], dots, dots);
    		}
		    g.setColor(Color.RED);
		    g.fillRect(apple_x, apple_y, dots, dots);
		    for(int i=0;i<square;i++) {
		    	if(i==0) {
		    		g.setColor(Color.YELLOW);
		    		g.fillOval(x[0], y[0], dots, dots);
		    	}else {
		    		g.setColor(Color.BLUE);
		    		g.fillRect(x[i], y[i], dots, dots);
		    	}
		    }
    	}else {
    		gameOver(g);
    	}

		return screen;
	}
    
    //�X�R�A�\��
    private Image getScreen2() {
    	Image screen = createImage(window_width, 50);
	    Graphics2D g = (Graphics2D)screen.getGraphics();
	  //�w�i�ݒ�
	    g.setColor(Color.yellow);
    	g.fillRect(0, 0, window_width, 50);
    	
    	String score_msg = "SCORE : " + String.valueOf(score);
    	g.setColor(Color.BLACK);
    	g.setFont(fm);
    	g.drawString(score_msg, 0, 40);
    	
    	return screen;
    }
    
    //�Q�[���I�[�o�[�\��
    private void gameOver(Graphics2D g) {
		timer.stop();
        String msg = "Game Over";


        g.setColor(Color.YELLOW);
        g.setFont(fm);
        g.drawString(msg, window_width / 2, window_height /2);
    }

    
    //�ւ̓����𐧌䂷��֐�
    public void move() {
    	//�����ۂ̕�����O�̈ʒu�Ɉړ�����(���͕�)
    	for(int i=square;i>0;i--) {
    		x[i] = x[i-1];
    		y[i]= y[i-1]; 
    	}
    	//�������瓪�̓���
    	if(leftDirection) {
    		x[0] -= dots;
    	}else if(rightDirection) {
    		x[0] += dots;
    	}else if(upDirection) {
    		y[0] -= dots;
    	}else if(downDirection) {
    		y[0] += dots;
    	}
    }
    
    //�L�[���͂��󂯕t����
	private class MyKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(!rightDirection) {
				    leftDirection = true;
				    upDirection = false;
				    downDirection = false;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(!leftDirection) {
				    rightDirection = true;
				    upDirection = false;
				    downDirection = false;
				}
				break;
			case KeyEvent.VK_UP:
				if(!downDirection) {
					leftDirection = false;
				    rightDirection = false;
				    upDirection = true;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(!upDirection) {
					leftDirection = false;
				    rightDirection = false;
				    downDirection = true;
				}
				break;
			case KeyEvent.VK_ENTER:
				if(!game_over) {
					initGame();
				}
			}

		}

	}
	
	//�����S�̏ꏊ�������_���Ɍ��߂�
	public void make_apple() {
		Random rand = new Random();
		int num;
		while(true) {
			boolean flag = false;
			num = rand.nextInt((window_width-dots*2)/dots) * dots +dots;
			for(int i=0;i<bnum;i++) {
				if(x2[i] == num)   flag = true;
			}
			if(!flag) break;
		}
		apple_x = num;
		apple_y = rand.nextInt((window_height-dots*2)/dots) * dots + dots;
	}
	
	public void make_block() {
		Random rand = new Random();
		for(int i=0;i<bnum; i++) {
			x2[i] = rand.nextInt(window_width/dots) * dots;
			y2[i]= rand.nextInt(window_height/dots) * dots; 
		}
	}
	
	//�����S�ƐڐG�������`�F�b�N���A�ڐG������g�̂�L�΂��ă����S���ĕ`��
	public void check_apple() {
		if(x[0] == apple_x && y[0] == apple_y) {
			square++;
			score += square * 50;
			make_apple();
		}
	}
	
	//�Q�[���I�[�o�[�̔���
	public void check_gameOver() {
		if(square > 4) {
			for(int i=square; i>0; i--) {
				if(x[i] == x[0] && y[i] == y[0]) {
					game_over = false;
				}
			}
		}
		if(x[0] < 0 || x[0] >= window_width || y[0] < 0 || y[0] >= window_height) {
			game_over = false;
		}
		for(int i=0;i<bnum;i++) {
			if(x[0] == x2[i] && y[0] == y2[i]) {
				game_over = false;
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// �^�C�}�[���Ԃ��ƂɌĂяo�����
		if(game_over) {
			move();
			check_apple();
			check_gameOver();
			score++;
		}
	    repaint();
	}

}
