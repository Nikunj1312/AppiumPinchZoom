package io.github.nikunj1312;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;


/**
 * This class will help in performing Scrolling gestures on the device using Appium and Selenium.
 * <p>TouchAction can be used to perform scrolling but it doesn't work under certain conditions. 
 * Hence the need for this library.</p>
 * 
 * <p>This class uses Collections, Selenium Point and Selenium Interactions.
 * </p>
 * 
 * @author Nikunj Shah
 * @version 1.0.5
 * @since 24-05-2020
 */
public class ScrollActions {
	
	/**
	 * This method will help you to scroll down
	 * @param locus - The code will press on this point and then move some distance to imitate scrolling action
	 * @return - Collection of Sequence of finger movement
	 */
	public static Collection<Sequence> verticalScrollDown(Point locus) {
		return scroll(locus, 10, 10+2000, 0, Duration.ofMillis(100));
	}
	
	/**
	 * This method will help you to scroll up
	 * @param locus - The code will press on this point and then move some distance to imitate scrolling action
	 * @return - Collection of Sequence of finger movement
	 */
	public static Collection<Sequence> verticalScrollUp(Point locus) {
		return scroll(locus, 10, 10+2000, 180, Duration.ofMillis(100));
	}
	
	/**
	 * This method will help you to scroll right
	 * @param locus - The code will press on this point and then move some distance to imitate scrolling action
	 * @return - Collection of Sequence of finger movement
	 */
	public static Collection<Sequence> horizontalScrollRight(Point locus) {
		return scroll(locus, 10, 10+1000, 270, Duration.ofMillis(100));
	}
	
	/**
	 * This method will help you to scroll left
	 * @param locus - The code will press on this point and then move some distance to imitate scrolling action
	 * @return - Collection of Sequence of finger movement
	 */
	public static Collection<Sequence> horizontalScrollLeft(Point locus) {
		return scroll(locus, 10, 10+1000, 90, Duration.ofMillis(100));
	}
	
	private static Collection<Sequence> scroll
		(Point locus, int startRadius, int endRadius, int pinchAngle, Duration duration) {
		
		// convert degree angle into radians. 0/360 is top (12 O'clock).
				double angle = Math.PI / 2 - (2 * Math.PI / 360 * pinchAngle);

				// create the gesture for one finger
				Sequence fingerAPath = singlefingerAction("fingerA", locus, startRadius, endRadius, angle, duration);
				
				return Arrays.asList(fingerAPath);
	}
	
	private static Sequence singlefingerAction
		(String fingerName, Point locus, int startRadius, int endRadius, double angle, Duration duration) {
		//create fingerPointer and Sequence to create action for the fingerPointer
				PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, fingerName);
				Sequence fingerPath = new Sequence(finger, 0);

				// find coordinates for starting point of action (converting from polar coordinates to cartesian)
				int fingerStartx = (int)Math.floor(locus.x + startRadius * Math.cos(angle));
				int fingerStarty = (int)Math.floor(locus.y - startRadius * Math.sin(angle));

				// find coordinates for ending point of action (converting from polar coordinates to cartesian)
				int fingerEndx = (int)Math.floor(locus.x + endRadius * Math.cos(angle));
				int fingerEndy = (int)Math.floor(locus.y - endRadius * Math.sin(angle));

				// move finger into start position
				fingerPath.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), fingerStartx, fingerStarty));
				
				// finger comes down into contact with screen
				fingerPath.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				
				// pause for a little bit
				fingerPath.addAction(new Pause(finger, Duration.ofMillis(10)));
				
				// finger moves to end position
				fingerPath.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), fingerEndx, fingerEndy));
				
				// finger lets up, off the screen
				fingerPath.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

				return fingerPath;
	}

}
