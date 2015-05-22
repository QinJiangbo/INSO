/**  
 * @description Queue data structure of links queue
 * @author QinJiangbo 2012302580314
 * @version 1.0
 * @date Apr 10, 2015 10:43:56 PM
 */

package Crawler;

import java.util.LinkedList;

public class LinkQueue<T> {
	
	
	//use the doubly linked list to implement a queue
	private LinkedList<T> queue = new LinkedList<T>();
	
	/**
	 * enable--> add last element to the queue
	 * @param t
	 */
	public void enQueue(T t) 
	{
		queue.addLast(t);
	}
	
	/**
	 * destroy--> remove first element from the queue
	 * @return the element deleted
	 */
	public T deQueue()
	{
		return queue.removeFirst();
	}
	
	/**
	 * judge whether the element exists
	 * @param t
	 * @return true or false
	 */
	public boolean contains(T t)
	{
		return queue.contains(t);
	}
	
	/**
	 * to know whether the queue is empty
	 * @return true or false
	 */
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	
}
