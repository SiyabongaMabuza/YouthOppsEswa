package edu.skidmore.cs276.project.beans.tasklist;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a task.
 * 
 * @author David Read
 * @version 01.00.00
 *
 */
public class Task implements Serializable {
	/**
	 * The internal version id of this class
	 */
	private static final long serialVersionUID = 19620501;

	private String id;
	private Date dueDate;
	private String title;
	private String description;
	private Boolean completed;

	/**
	 * Create a task that has no due date
	 * 
	 * @param title
	 *          The task's title
	 * @param description
	 *          The task's description
	 */
	public Task(String title, String description) {
		this(null, title, description);
	}

	/**
	 * Create a task that includes a due date
	 * 
	 * @param dueDate
	 *          The task's due date
	 * @param title
	 *          The task's title
	 * @param description
	 *          The task's description
	 */
	public Task(Date dueDate, String title, String description) {
		this(null, dueDate, title, description);
	}

	/**
	 * Create a task with optional id and due date
	 * 
	 * @param id
	 *          The task's unique identifier (optional) - if null then one will be
	 *          generated
	 * @param dueDate
	 *          The task's due date (optional)
	 * @param title
	 *          The task's title
	 * @param description
	 *          The task's description
	 */
	public Task(String id, Date dueDate, String title, String description) {
		if (id == null) {
			setId(UUID.randomUUID().toString());
		} else {
			setId(id);
		}

		setDueDate(dueDate);
		setTitle(title);
		setDescription(description);
	}

	/**
	 * Get the task's unique id
	 * 
	 * @return The task's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the task's unique id
	 * 
	 * @param id
	 *          The task's id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the task's due date. If there is no due date this will be null.
	 * 
	 * @return The task's due date
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Set the tasks' due date. This may be null if there is no due date.
	 * 
	 * @param dueDate
	 *          The task's due date
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Get the task's title
	 * 
	 * @return The task's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the task's title
	 * 
	 * @param title
	 *          The task's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the task's description
	 * 
	 * @return The task's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the task's description
	 * 
	 * @param description
	 *          The task's description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Determine whether the task has been completed
	 * 
	 * @return True if the task has been completed
	 */
	public Boolean isCompleted() {
		return completed;
	}

	/**
	 * Set whether the task has been completed
	 * 
	 * @param completed
	 *          True if the task has been completed
	 */
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	/**
	 * Get a String representation of the task. This returns the internal id of
	 * the task and its title.
	 * 
	 * @return The task's id and title
	 */
	public String toString() {
		return id + ": " + title;
	}
}
