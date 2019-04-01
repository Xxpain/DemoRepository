package test;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import org.junit.Test;


public class Testasd {

    @Test
    public void sendMessage() throws Exception {
    	for (int i = 0; i < 1000; i++) {
    		PointerInfo pinfo = MouseInfo.getPointerInfo();
        	Point p = pinfo.getLocation();
        	int mx = (int) p.getX();
        	int my = (int) p.getY();
        	System.out.println(mx+"---"+my);
			Thread.sleep(3);
		}
    }

}