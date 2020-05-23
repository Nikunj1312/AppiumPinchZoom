package appium.zoom;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

/**
 * This PinchZoom class helps to perform ZoomIn and ZoomOut operations on elements like images/maps/etc while 
 * doing automation testing on Mobile applications using Appium.
 * <p>Appium has MultiTouchActions API which used to perform the same operations. 
 * However, since Appium version 1.7 it has stopped working and there is no other alternative provided by Appium as yet. 
 * Hence, there was a need to create a library which can perform these operations.
 * </p>
 * <p>This class uses Collections, Selenium Point and Selenium Interactions.
 * </p>
 * 
 * @author Nikunj Shah
 * @version 1.0
 * @since 07-05-2020
 */
public class PinchZoom {
	
	/**
	 * This method will help you to zoom in the image/maps/etc upto 50% approximately.
	 * You can call this method multiple times if you want to zoom in more.
	 * This method will call common zoom method to perform the operation.
	 * @param locus - This should be the point on which you want to zoom in. It can be center point.
	 * @return - Collection of Sequence of the finger(both) actions.
	 */
	public static Collection<Sequence> zoomIn(Point locus) {
		return zoom(locus, 200, 200 + 400, 45, Duration.ofMillis(100));
	}
	
	/**
	 * This method is not completely implemented as yet.
	 * @param locus
	 * @param distance
	 * @return
	 */
	private static Collection<Sequence> zoomIn(Point locus, int distance) {
		return zoom(locus, 200, 200 + distance, 45, Duration.ofMillis(100));
	}
	
	/**
	 * This method will help you to zoom out the image/maps/etc upto 50% approximately.
	 * You can call this method multiple times if you want to zoom out more.
	 * This method will call common zoom method to perform the operation.
	 * @param locus - This should be the point on which you want to zoom out. It can be center point.
	 * @return - Collection of Sequence of the finger(both) actions.
	 */
	public static Collection<Sequence> zoomOut(Point locus) {
		return zoom(locus, 200 + 400, 200, 45, Duration.ofMillis(100));
	}
	
	/**
	 * This method is not completely implemented as yet.
	 * @param locus
	 * @param distance
	 * @return
	 */
	private static Collection<Sequence> zoomOut(Point locus, int distance) {
		return zoom(locus, 200 + distance, 200, 45, Duration.ofMillis(100));
	}
	
	/**
	 * This is the common method for Zoom in or Zoom out.
	 * The difference is in the values of startRadius and endRadius 
	 * which are accepted as parameters from ZoomIn/ZoomOut methods.
	 * This will call singlefingerAction twice to create Finger actions for two fingers.
	 * @param locus - This the point on which Zoom in or Zoom out operation needs to be performed
	 * @param startRadius - This is the starting point. 
	 * For Zoom In it will be the point on which you want to zoom.
	 * For Zoom Out it will be some distance diagonally away from the point on which you want to zoom out.
	 * @param endRadius - This will be the end point.
	 * For Zoom In it will be some distance diagonally away from the point on which you want to zoom out.
	 * For Zoom Out it will be the point on which you want to zoom.
	 * @param pinchAngle - This the angle of direction in which you want to move your finger.
	 * @param duration - This is the amount of time it should take to move the finger from start point to end point.
	 * @return - This method returns Arrays object which contains two sequences for two finger actions.
	 */
	private static Collection<Sequence> zoom(Point locus, int startRadius, int endRadius, int pinchAngle, Duration duration) {
		
		// convert degree angle into radians. 0/360 is top (12 O'clock).
		double angle = Math.PI / 2 - (2 * Math.PI / 360 * pinchAngle);

		// create the gesture for one finger
		Sequence fingerAPath = singlefingerAction("fingerA", locus, startRadius, endRadius, angle, duration);

		// flip the angle around to the other side of the locus and get the gesture for the second finger
		angle = angle + Math.PI;
		Sequence fingerBPath = singlefingerAction("fingerB", locus, startRadius, endRadius, angle, duration);

		return Arrays.asList(fingerAPath, fingerBPath);
	}
	
	/**
	 * This method will create action for one finger and add it to a Sequence.
	 * @param fingerName - This is to identify multiple Fingers like unique ID.
	 * @param locus - This the point on which Zoom in or Zoom out operation needs to be performed
	 * @param startRadius - This is the starting point. 
	 * For Zoom In it will be the point on which you want to zoom.
	 * For Zoom Out it will be some distance diagonally away from the point on which you want to zoom out.
	 * @param endRadius - This will be the end point.
	 * For Zoom In it will be some distance diagonally away from the point on which you want to zoom out.
	 * For Zoom Out it will be the point on which you want to zoom.
	 * @param angle - This the angle of direction in which you want to move your finger.
	 * @param duration - This is the amount of time it should take to move the finger from start point to end point.
	 * @return - This method returns the sequence of actions for one finger.
	 */
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
