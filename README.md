[![Maven Central](https://img.shields.io/maven-central/v/io.github.nikunj1312/AppiumPinchZoom.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.nikunj1312%22%20AND%20a:%22AppiumPinchZoom%22)

# AppiumPinchZoom
This will help to perform ZoomIn/ZoomOut and Scroll operations on elements for automation testing mobile applications using Appium

<p>Since Appium version 1.7 the MultiTouchAction doesn't work for Zoom In and Zoom Out operations. Hence, this library was created.
</p>

<p>Also Scroll operation using touch actions is not possible if the <b>ELEMENT</b> on which you are doing press and move to action has any action asssigned to it on long press. For eg: in Google Photos app, if I long press on a photo then the photo gets selected hence we cannot use image element for scroll.
  </p>

In your appium tests you need to write your code as below:</br>
eg: Zooming an Image</br>
Point p = img.getcenter(); //img is the mobile element for image</br>
driver.perform(PinchZoom.zoomIn(p));</br>
OR</br>
driver.perform(PinchZoom.zoomOut(p));</br>
