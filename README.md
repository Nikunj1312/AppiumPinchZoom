# AppiumPinchZoom
This will help to perform Zoom In and Zoom Out operations on elements for automation testing mobile applications using Appium

<p>Since Appium version 1.7 the MultiTouchAction doesn't work for Zoom In and Zoom Out operations. Hence, this library was created.
</p>

In your appium tests you need to write your code as below:</br>
eg: Zooming an Image</br>
Point p = img.getcenter(); //img is the mobile element for image</br>
driver.perform(PinchZoom.zoomIn(p);</br>
OR</br>
driver.perform(PinchZoom.zoomOut(p);</br>
