package zhrfrd.adventure2.main;

import java.awt.Rectangle;

public class EventRect extends Rectangle {
	int eventRectDefaultX, eventRectDefaultY;
	boolean eventDone = false;   // Check if the event has been already called (One time only event)
}
