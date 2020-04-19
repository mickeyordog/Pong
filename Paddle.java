package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Paddle {

	private int x, y;
	private int vel = 0;
	private int speed = 10;
	private int width = 22, height = 85;
	private int score = 0;
	private int skill = 1;
	Random rand;
	private Color color;
	private boolean left;
	
	public Paddle(Color c, boolean left) {
		color = c;
		this.left = left;
		
		if (left)
			x = 0;
		else
			x = Game.WIDTH - width;
		y = Game.HEIGHT/2 - height/2;
		rand = new Random();
	}
	
	public void addPoint() {
		score++;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		int sx;
		String scoreText = Integer.toString(score);
		Font font = new Font("Roboto", Font.PLAIN, 50);
		int strWidth = g.getFontMetrics(font).stringWidth(scoreText) + 1;
		int padding = 25;
		
		if (left)
			sx = Game.WIDTH / 2 - padding - strWidth;
		else 
			sx = Game.WIDTH / 2 + padding;
		
		g.setFont(font);
		g.drawString(scoreText, sx, 50);
	}

	public void update(Ball b) {
		y = Game.ensureRange(y += vel, 0, Game.HEIGHT - height);
		
		int ballX = b.getX();
		int ballY = b.getY();
		
		if (left) {
			
			if (ballX <= width && ballY + Ball.SIZE >= y && ballY <= y + height) {
				b.changeXDir();
			}
			
		} else {
			
			if (ballX + Ball.SIZE >= Game.WIDTH - width && ballY + Ball.SIZE >= y && ballY <= y + height) {
				b.changeXDir();
				changeSkill();

			}
			
		}
	}

	//randomizes AI's skill to make game winnable
	private void changeSkill() {
		if (rand.nextFloat() < 0.25) {
			skill = skill == 1 ? 0 : 1;

			//toggles between 0 (no skill) and 1 (perfect skill)
		}
	}

	public void switchDirection(int direction) {
		vel = speed * direction;
	}
	
	public void stop() {
		vel = 0;
	}

	public void AI(Ball ball) {
		if (skill == 1) {
			y = Game.ensureRange(ball.getY() - height/2, 0, Game.HEIGHT - height);
		} else {
			y = Game.ensureRange(ball.getY() + height/2, 0, Game.HEIGHT - height);
		}
//		if (rand.nextFloat() < 0.0001) {
//			skill = 1;
//		}
		//System.out.println(skill);
	}
	
}
