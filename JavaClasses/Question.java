


/*
 * 
 * "Level shows the difficulty level of the question 
Every question is assigned a level"

 */
enum Level
{
	EASY,MEDIUM,HARD;
}

/*
 * Tags depicts the Tag a question is assigned to.

 */

enum Tag
{
	Tag1,Tag2,Tag3,Tag4,Tag5,Tag6;
}

/*
 * "To check whehter a question has been selected
 in any of the quizes or not yet."

 */

enum Status
{
	SELECTED,NOT_YET_SELECTED;
}

// Question representing every individual question from the pool

public class Question 
{
	private String id; // ID a question is assigned to ..unique for each question(assumption)
	private Level qlevel; // level a question is assigned to 
	private Tag qtag; // Tag for the question
	private Status qStatus; // Showing whether a question has been selected or not in any of the quizes yet.
	
	/*
	 * below are the getters and setters of the every instance variable
	 * and an overridden toString() method to print Question
	 */
	
	
	/*
	 * Default Constructor
	 */
	
	public Question()
	{
		qStatus=Status.NOT_YET_SELECTED;
	}
	
	/*
	 * Overloaded Construtor 
	 * 
	 */
	
	public Question(String id,Level level,Tag tag,Status status)
	{
		this.id=id;
		this.qlevel=level;
		this.qtag=tag;
		qStatus=status;
	}
	
	/*
	 * Overridden toString method 
	 */
	
	public String toString()
	{
		return this.id+" "+this.qlevel+" "+this.qtag+" "+this.qStatus;
	}

	
	/*
	 * Getters and setters
	 */
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Level getQlevel() {
		return qlevel;
	}

	public void setQlevel(Level qlevel) {
		this.qlevel = qlevel;
	}

	public Tag getQtag() {
		return qtag;
	}

	public void setQtag(Tag qtag) {
		this.qtag = qtag;
	}

	public Status getqStatus() {
		return qStatus;
	}

	public void setqStatus(Status qStatus) {
		this.qStatus = qStatus;
	}	
}


